package Panels;

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
    private List<Question> questions = new ArrayList<>();

    public GamePanel() {
        setPanel();
        setCategoryButtonActions();
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

    public void setQuestionActionListener(ActionListener actionListener) {
        nextQuestionButton.addActionListener(e -> {
            showNextQuestion();

            for (JButton button : answerButtons) {
                button.setBackground(null);
                button.setEnabled(true);
            }
            nextQuestionButton.setEnabled(false);
        });

        for (AnswerButton answerButton : answerButtons) {
            answerButton.addActionListener(e -> {
                AnswerButton clickedButton = (AnswerButton) e.getSource();
                boolean isCorrect = clickedButton.isCorrect();

                clickedButton.setBackground(isCorrect ? Color.GREEN : Color.RED);
                JOptionPane.showMessageDialog(this, isCorrect ? "Rätt svar!" : "Fel svar!");
                for (JButton button : answerButtons) {
                    button.setEnabled(false);
                }
                nextQuestionButton.setEnabled(true);
            });
        }
    }

    public void showCategoryPanel() {
        cardLayout.show(mainPanel, "CategoryPanel");
    }
    public void showQuestionPanel() {
        cardLayout.show(mainPanel, "QuestionPanel");
    }
    //Tillfällig
    public void loadQuestions(List<Question> questions) {
        this.questions = questions;
        currentQuestionIndex = 0;
        showNextQuestion();
    }
    //Tillfällig
    public void showNextQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex++);
            questionLabel.setText(question.getQuestionQ());

            List<AnswerAlternative> answers = question.getAnswerAlternatives();
            for (int i = 0; i < answers.size(); i++) {
                answerButtons[i].setButton(answers.get(i));
            }
        } else {
            JOptionPane.showMessageDialog(this, "Återvänder till kategorier.");
            showCategoryPanel();
        }
    }
    private void setCategoryButtonActions() {
        category1Button.addActionListener(e -> {
            loadSampleQuestionsForCategory("Kategori 1"); // Ladda frågor för kategori 1
            showQuestionPanel(); // Växla till frågepanelen
        });
        category2Button.addActionListener(e -> {
            loadSampleQuestionsForCategory("Kategori 2");
            showQuestionPanel();
        });
        category3Button.addActionListener(e -> {
            loadSampleQuestionsForCategory("Kategori 3");
            showQuestionPanel();
        });
    }
    // Tillfällig funktion för att ladda exempeldata beroende på kategori
    private void loadSampleQuestionsForCategory(String category) {
        List<Question> sampleQuestions = new ArrayList<>();
        if (category.equals("Kategori 1")) {
            sampleQuestions.add(new Question("Vad är 2 + 2?",
                    List.of(
                            new AnswerAlternative("3", false),
                            new AnswerAlternative("4", true),
                            new AnswerAlternative("5", false),
                            new AnswerAlternative("6", false)
                    )
            ));
        } else if (category.equals("Kategori 2")) {
            sampleQuestions.add(new Question("Vilken färg har himlen?",
                    List.of(
                            new AnswerAlternative("Grön", false),
                            new AnswerAlternative("Blå", true),
                            new AnswerAlternative("Röd", false),
                            new AnswerAlternative("Gul", false)
                    )
            ));
        } else if (category.equals("Kategori 3")) {
            sampleQuestions.add(new Question("Vilket år blev Sverige självständigt?",
                    List.of(
                            new AnswerAlternative("1523", true),
                            new AnswerAlternative("1809", false),
                            new AnswerAlternative("1905", false),
                            new AnswerAlternative("1945", false)
                    )
            ));
        }
        loadQuestions(sampleQuestions); // Ladda de valda frågorna i panelen
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("QuizKampen");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            GamePanel gamePanel = new GamePanel();
            gamePanel.setQuestionActionListener(null);
            frame.add(gamePanel);
            frame.setVisible(true);
        });
    }
}





