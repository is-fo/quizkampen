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
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ObjectOutputStream oos;
    private JFrame categoryFrame;


    public CategoryPanel(List<String> categories, ObjectOutputStream oos) {
        this.categories = categories;
        this.oos = oos;
        this.panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    }

    public void drawCategories() {
        createCategoryFrame();

        for (String category : categories) {
            JButton categoryButton = new JButton(category);
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

            panel.add(categoryButton);
            categoryFrame.add(panel);
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    private void createCategoryFrame() {
        categoryFrame = new JFrame("QuizKampen");
        //categoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        categoryFrame.setSize(400, 300);
        categoryFrame.setLocationRelativeTo(null);
        categoryFrame.setVisible(true);
    }

    private void closeCategoryFrame() {
        categoryFrame.dispose();
    }
}

