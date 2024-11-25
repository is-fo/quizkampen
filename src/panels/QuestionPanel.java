package panels;

import pojos.Question;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class QuestionPanel {

    private Question question;
    private JPanel panel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ObjectOutputStream oos;

    public QuestionPanel(Question question, ObjectOutputStream oos) {
        this.question = question;
        this.oos = oos;
        this.cardPanel = new JPanel();
        this.cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
    }

    public void drawQuestion() {
        //TODO en label med frågar
        JLabel questionLabel = new JLabel(question.getQuestion());
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(questionLabel);

        //TODO svarsalternativ -> returnera en String
        for (String answer : question.getAnswers()) {
            JButton answerButton = new JButton(answer);
            answerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            answerButton.addActionListener(e -> handleAnswerSelection(answer));
            panel.add(Box.createRigidArea(new Dimension(0, 5)));
            panel.add(answerButton);
        }
        cardPanel.add(panel, "QuestionPanel");
    }

    private void handleAnswerSelection(String selectedAnswer) {
        String message = selectedAnswer.equals(question.getCorrectAnswer())
                ? "Rätt svar!" : "Fel svar. Rätt svar är: " + question.getCorrectAnswer();
        JOptionPane.showMessageDialog(panel, message);

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

        public static void addQuestionViews(JPanel cardPanel, QuestionPanel questionPanel) {
            cardPanel.add(questionPanel.getCardPanel(), "QuestionPanel");
        }

        private static void createQuestionFrame(JPanel cardPanel) {
            JFrame questionFrame = new JFrame("QuizKampen");
            questionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            questionFrame.setSize(400, 300);
            questionFrame.setLayout(new BorderLayout());
            questionFrame.add(cardPanel, BorderLayout.CENTER);
            questionFrame.setResizable(true);
            questionFrame.setVisible(true);
        }

        public static void main (String[]args){
            List<String> answers = List.of("Blå", "Gul", "Vit", "Svart");
            Question question = new Question("Vilken färg har himlen?", answers, "Blå");
            QuestionPanel questionPanel = new QuestionPanel(question,null);
            questionPanel.drawQuestion();
            JPanel cardPanel = new JPanel(new CardLayout());
            addQuestionViews(cardPanel, questionPanel);
            createQuestionFrame(cardPanel);




            /*try {
                Socket socket = new Socket("localhost", 55555);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                QuestionPanel questionPanel = new QuestionPanel(question,null);
                questionPanel.drawQuestion();
                JPanel cardPanel = new JPanel(new CardLayout());
                addQuestionViews(cardPanel, questionPanel);
                createQuestionFrame(cardPanel);

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("QuestionPanel kunde inte ansluta till servern");
            }*/
        }
    }

