package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

    public GamePanel(){
        setPanel();
    }

    @Override
    public void setPanel() {
        setLayout(new BorderLayout());
        mainPanel.setBackground(backgroundColor);

        // Ställ in kategori-panelen
        setupCategoryPanel();

        // Ställ in fråge-panelen
        setupQuestionPanel();

        // Lägg till båda panelerna i CardLayout
        mainPanel.add(categoryPanel, "CategoryPanel");
        mainPanel.add(questionPanel, "QuestionPanel");

        // Visa kategori-panelen först
        cardLayout.show(mainPanel, "CategoryPanel");

        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupCategoryPanel() {
        categoryPanel.setLayout(new GridLayout(3, 1, 10, 10));
        categoryPanel.setBackground(backgroundColor);

        category1Button.setFont(buttonFont);
        category2Button.setFont(buttonFont);
        category3Button.setFont(buttonFont);

        categoryPanel.add(category1Button);
        categoryPanel.add(category2Button);
        categoryPanel.add(category3Button);
    }

    private void setupQuestionPanel() {
        questionPanel.setLayout(new BorderLayout());
        questionPanel.setBackground(backgroundColor);

        // Frågetext
        questionLabel.setFont(buttonFont);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionPanel.add(questionLabel, BorderLayout.NORTH);

        // Svarsknappar
        answerPanel.setBackground(backgroundColor);
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i] = new AnswerButton();
            answerButtons[i].setFont(buttonFont);
            answerPanel.add(answerButtons[i]);
        }
        questionPanel.add(answerPanel, BorderLayout.CENTER);


        nextQuestionButton.setFont(buttonFont);
        questionPanel.add(nextQuestionButton, BorderLayout.SOUTH);
    }

    public void setCategoriesActionListener(ActionListener actionListener) {
        category1Button.addActionListener(actionListener);
        category2Button.addActionListener(actionListener);
        category3Button.addActionListener(actionListener);
    }

    public void setQuestionActionListener(ActionListener actionListener) {
        nextQuestionButton.addActionListener(actionListener);
        for (AnswerButton answerButton : answerButtons) {
            answerButton.addActionListener(actionListener);
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
                answerButtons[i].setBackground(null);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Spelet är slut!");
            showCategoryPanel();
        }
    }

    public void showCorrectAnswer() {
        for (AnswerButton button : answerButtons) {
            if (button.isCorrect()) {
                button.setBackground(Color.GREEN);
            }
        }
    }

    public void showWrongAnswer(AnswerButton clickedButton) {
        clickedButton.setBackground(Color.RED);
    }


public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        JFrame frame = new JFrame("QuizKampen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new GamePanel());
        frame.setVisible(true);
    });
  }
}


