package panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.util.List;


//TODO Katogori knappar storlek
public class CategoryPanel {

    private List<String> categories;
    private JPanel panel;
    private ObjectOutputStream oos;
    private JFrame categoryFrame;


    public CategoryPanel(List<String> categories, ObjectOutputStream oos) {
        this.categories = categories;
        this.oos = oos;
        this.panel = new JPanel(new BorderLayout());;
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    }

    public void drawCategories() {
        createCategoryFrame();

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 20, 20));

        for (String category : categories) {
            JButton categoryButton = new JButton(category);
            categoryButton.setFont(new Font("Arial", Font.BOLD, 18));
            categoryButton.setPreferredSize(new Dimension(200, 100));
            categoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Kategori vald: " + category);
                    try {
                        oos.writeObject(category);
                        oos.flush();
                        closeCategoryFrame();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(panel, "Fel vid kommunikation med servern.");
                    }
                }
            });
            buttonPanel.add(categoryButton);
        }
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(buttonPanel, BorderLayout.CENTER);
        categoryFrame.add(panel);
    }

    public JPanel getPanel() {
        return panel;
    }

    private void createCategoryFrame() {
        categoryFrame = new JFrame("QuizKampen");
        categoryFrame.setSize(800, 600);
        categoryFrame.setLocationRelativeTo(null);
        categoryFrame.setVisible(true);
    }

    private void closeCategoryFrame() {
        categoryFrame.dispose();
    }

}

