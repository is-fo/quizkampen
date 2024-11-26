package panels;

import pojos.Question;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class QuestionPanel {

    private JFrame questionFrame;
    private List<Question> questions;
    private JPanel panel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private List<String> selectedAnswers = new ArrayList<>();
    private ObjectOutputStream oos;

    int index = 0;
    public boolean done = false;

    public QuestionPanel(List<Question> questions, ObjectOutputStream oos) {
        this.questions = questions;
        this.cardPanel = new JPanel();
        this.cardLayout = new CardLayout();
        this.oos = oos;
        cardPanel.setLayout(cardLayout);
    }

    public void drawAll() {

        for (int index = 0; index < questions.size(); index++) {
            panel = new JPanel(new BorderLayout(10, 10));
            JPanel questionPanel = new JPanel();
            questionPanel.setBackground(Color.LIGHT_GRAY);
            questionPanel.setLayout(new BorderLayout());

            JLabel questionLabel = new JLabel(questions.get(index).getQuestion());
            questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
            questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            questionPanel.add(questionLabel, BorderLayout.CENTER);
            panel.add(questionPanel, BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
            for (String answer : questions.get(index).getAnswers()) {
                JButton answerButton = new JButton(answer);
                answerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

                int finalIndex = index;
                answerButton.addActionListener(e -> {

                    handleAnswerSelection(answerButton, answerButton.getText(), finalIndex);
                    System.out.println(getAnswers());

                    Timer timer = new Timer(1000, e2 -> {
                        if (finalIndex < questions.size() - 1) {
                            cardLayout.next(cardPanel);
                        } else {
                            hideQuestionFrame();
                            try {
                                oos.writeObject(getAnswers());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                System.err.println("Error sending answer list to server: " + ex.getMessage());
                            }
                        }
                        ((Timer) e2.getSource()).stop();
                    });
                    timer.setRepeats(false);
                    timer.start();

                });
                buttonPanel.add(answerButton);
            }
            panel.add(buttonPanel, BorderLayout.CENTER);
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            cardPanel.add(panel, "Question " + (index + 1));

        }
        showQuestionFrame();
    }

    private void handleAnswerSelection(JButton button, String selectedAnswer, int index) {

        boolean isCorrect = selectedAnswer.equals(questions.get(index).getCorrectAnswer());

        if (isCorrect) {
            button.setBackground(Color.GREEN);
        } else {
            button.setBackground(Color.RED);
        }

        JPanel buttonPanel = (JPanel) button.getParent().getParent().getComponent(1);
        for (Component component : buttonPanel.getComponents()) {
            if (component instanceof JButton) {
                component.setEnabled(false);
            }
        }

        selectedAnswers.add(selectedAnswer);
    }

    public JPanel getCardPanel() {
    return cardPanel;
}

    public void showQuestionFrame() {
        questionFrame = new JFrame("QuizKampen");
        questionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        questionFrame.setSize(801, 601);
        questionFrame.add(cardPanel);
        questionFrame.setLocationRelativeTo(null);
        questionFrame.setVisible(true);
    }

    public void hideQuestionFrame() {
        questionFrame.dispose();
    }

    public List<String> getAnswers() {
        return selectedAnswers;
    }
}

