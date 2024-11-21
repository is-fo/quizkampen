package panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class GamePanel extends MasterPanel {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);

    private final JPanel categoryPanel = new JPanel();
    private final JPanel questionPanel = new JPanel();

    private final JButton category1Button = new JButton("Kategori 1");
    private final JButton category2Button = new JButton("Kategori 2");
    private final JButton category3Button = new JButton("Kategori 3");

    private final JLabel questionLabel = new JLabel();
    private final JPanel answerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
    private final AnswerButton[] answerButtons = new AnswerButton[4];
    private final JButton nextQuestionButton = new JButton("Nästa fråga");

    // Data för frågor och svar (vår data/logik)?
    private int currentQuestionIndex = 0;

    public GamePanel() {
        setPanel();
    }

    @Override
    public void setPanel() {
        setLayout(new BorderLayout());
        mainPanel.setBackground(backgroundColor);
        setupCategoryPanel();
        setupQuestionPanel();
        mainPanel.add(categoryPanel, "CategoryPanel");
        mainPanel.add(questionPanel, "QuestionPanel");
        cardLayout.show(mainPanel, "CategoryPanel");
        add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void setActionListener(ActionListener actionListener) {
    }

    private void setupCategoryPanel() {
        categoryPanel.setLayout(new GridLayout(3, 1, 10, 10));
        categoryPanel.setBackground(backgroundColor);
        categoryPanel.add(category1Button);
        categoryPanel.add(category2Button);
        categoryPanel.add(category3Button);
    }

    private void setupQuestionPanel() {
        questionPanel.setLayout(new BorderLayout());
        questionPanel.setBackground(backgroundColor);
        questionLabel.setFont(buttonFont);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionPanel.add(questionLabel, BorderLayout.NORTH);
        answerPanel.setBackground(backgroundColor);

        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i] = new AnswerButton();
            answerButtons[i].setFont(buttonFont);
            answerPanel.add(answerButtons[i]);
        }
        questionPanel.add(answerPanel, BorderLayout.CENTER);
        questionPanel.add(nextQuestionButton, BorderLayout.SOUTH);
    }



    public void showCategoryPanel() {
        cardLayout.show(mainPanel, "CategoryPanel");
    }
    public void showQuestionPanel() {
        cardLayout.show(mainPanel, "QuestionPanel");
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("QuizKampen");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            GamePanel gamePanel = new GamePanel();
            frame.add(gamePanel);
            frame.setVisible(true);
        });
    }
}





