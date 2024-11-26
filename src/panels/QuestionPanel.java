package panels;

import pojos.Question;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

//TODO Fixa kanpparna --> Röd/Grön + grafik + Lista med alla rätta svar.
    public class QuestionPanel {

    private Question question;
    private JPanel panel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ObjectOutputStream oos;
    private List<String> correctAnswers = new ArrayList<>();

    public QuestionPanel(Question question, ObjectOutputStream oos) {
        this.question = question;
        this.oos = oos;
        this.cardPanel = new JPanel();
        this.cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
    }

    public void drawQuestion() {
        //TODO en label med frågar
        panel = new JPanel(new BorderLayout(10, 10));
        JPanel questionPanel = new JPanel();
        questionPanel.setBackground(Color.LIGHT_GRAY);
        questionPanel.setLayout(new BorderLayout());

        JLabel questionLabel = new JLabel(question.getQuestion());
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionPanel.add(questionLabel, BorderLayout.CENTER);

        panel.add(questionPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));


        //TODO svarsalternativ -> returnera en String
        for (String answer : question.getAnswers()) {
            JButton answerButton = new JButton(answer);
            answerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            answerButton.addActionListener(e -> handleAnswerSelection(answerButton, answer));
            buttonPanel.add(answerButton);

        }
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardPanel.add(panel, "QuestionPanel");
    }

    private void handleAnswerSelection(JButton button, String selectedAnswer) {

        boolean isCorrect = selectedAnswer.equals(question.getCorrectAnswer());

        if (isCorrect) {
            button.setBackground(Color.GREEN);
            correctAnswers.add(selectedAnswer);
        } else {
            button.setBackground(Color.RED);
        }

        for (Component component : panel.getComponents()) {
            if (component instanceof JPanel) {
                for (Component btn : ((JPanel) component).getComponents()) {
                    if (btn instanceof JButton) {
                        btn.setEnabled(false);
                    }
                }
            }
        }

        try {
            oos.writeObject(selectedAnswer);
            oos.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

        public JPanel getCardPanel() {
        return cardPanel;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

        public static void addQuestionViews(JPanel cardPanel, QuestionPanel questionPanel) {
            cardPanel.add(questionPanel.getCardPanel(), "QuestionPanel");
        }

    public void showQuestionFrame() {
        JFrame questionFrame = new JFrame("QuizKampen");
        questionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        questionFrame.setSize(800, 600);
        questionFrame.add(this.getCardPanel());
        questionFrame.setLocationRelativeTo(null);
        questionFrame.setVisible(true);
    }

        public static void main (String[]args){
            List<String> answers = List.of("Blå", "Gul", "Vit", "Svart");
            Question question = new Question("Vilken färg har himlen?", answers, "Blå");
            QuestionPanel questionPanel = new QuestionPanel(question, null);
            questionPanel.drawQuestion();
            questionPanel.showQuestionFrame();
        }
}

