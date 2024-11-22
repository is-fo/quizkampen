package panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.util.List;

public class CategoryPanel {

    private List<String> categories;
    private JPanel panel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ObjectOutputStream oos;

    public CategoryPanel(){
    }

    public CategoryPanel(List<String> categories, CardLayout cardLayout, JPanel cardPanel, ObjectOutputStream oos) {
        this.categories = categories;
        this.oos = oos;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.panel = new JPanel();  // Skapar en ny JPanel för att lägga till knappar
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    }

    public void drawCategories() {
        for (String category : categories) {
            JButton categoryButton = new JButton(category);
            categoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Kategori vald: " + category);
                    cardLayout.show(cardPanel,category);
                    try {
                        oos.writeObject(category);
                        oos.flush();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(panel, "Fel vid kommunikation med servern.");
                    }
                }
            });

            panel.add(categoryButton);
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        CategoryPanel categoryPanel = new CategoryPanel();
    }
}

