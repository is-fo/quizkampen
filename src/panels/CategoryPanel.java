package panels;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectOutputStream;
import java.util.List;

public class CategoryPanel {

    private List<String> categories;
    private JPanel panel;
    private ObjectOutputStream oos;
    private JFrame categoryFrame;


    public CategoryPanel(List<String> categories, ObjectOutputStream oos) {
        this.categories = categories;
        this.oos = oos;
        this.panel = new JPanel(new BorderLayout());
    }

    public void drawCategories() {
        createCategoryFrame();

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 20, 20));

        for (String category : categories) {
            JButton categoryButton = new JButton(category);
            categoryButton.setFont(new Font("Arial", Font.BOLD, 18));
            categoryButton.setPreferredSize(new Dimension(200, 100));
            categoryButton.addActionListener(e -> {
                System.out.println("Kategori vald: " + category);
                try {
                    oos.writeObject(category);
                    oos.flush();
                    closeCategoryFrame();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Fel vid kommunikation med servern.");
                }
            });
            buttonPanel.add(categoryButton);
        }
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(buttonPanel, BorderLayout.CENTER);
        categoryFrame.add(panel);
    }

    private void createCategoryFrame() {
        categoryFrame = new JFrame("QuizKampen");
        categoryFrame.setSize(800, 600);
        categoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        categoryFrame.setLocationRelativeTo(null);
        categoryFrame.setVisible(true);
    }

    private void closeCategoryFrame() {
        categoryFrame.dispose();
    }

}

