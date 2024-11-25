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


    public CategoryPanel(List<String> categories, CardLayout cardLayout, JPanel cardPanel, ObjectOutputStream oos) {
        this.categories = categories;
        this.oos = oos;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.panel = new JPanel();  // Create a new JPanel for the category buttons
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

    private static JFrame createCategoryFrame() {
        JFrame categoryFrame = new JFrame("QuizKampen");
        categoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        categoryFrame.setSize(400, 300);
        categoryFrame.setLocationRelativeTo(null);
        return categoryFrame;
    }

    private static void addCategoryViews(CardLayout cardLayout, JPanel cardPanel, List<String> categories) {
        for (String category : categories) {
            JPanel categoryView = new JPanel();
            categoryView.setLayout(new BorderLayout());

            JLabel label = new JLabel("Du valde: " + category, SwingConstants.CENTER);
            categoryView.add(label, BorderLayout.CENTER);
            cardPanel.add(categoryView, category);
        }
    }

    public static void main(String[] args) {
        List<String> categories = List.of("Kategori 1", "Kategori 2", "Kategori 3");
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        ObjectOutputStream oos = null;

        CategoryPanel categoryPanel = new CategoryPanel(categories, cardLayout, cardPanel, oos);
        categoryPanel.drawCategories();
        cardPanel.add(categoryPanel.getPanel(), "CategoryView");
        addCategoryViews(cardLayout, cardPanel, categories);

        JFrame frame = createCategoryFrame();
        frame.add(cardPanel);
        frame.setVisible(true);
    }
}

