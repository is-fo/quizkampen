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
    private ObjectOutputStream oos;


    public QuestionPanel(Question question, ObjectOutputStream oos) {
        this.question = question;
        this.oos = oos;
        this.panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    }

    public void drawQuestion() {
        //TODO en label med frågar
        JLabel questionLabel = new JLabel(question.getQuestion());
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
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
            JOptionPane.showMessageDialog(panel, "Fel vid kommunikation med servern.");
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        List<String> answers = List.of("Blå", "Gul", "Vit", "Svart");
        Question question = new Question("Vilken färg har himlen?", answers, "Blå");

        try {
            Socket socket = new Socket("localhost", 55555);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            QuestionPanel questionPanel = new QuestionPanel(question, oos);
            questionPanel.drawQuestion();

            JFrame frame = new JFrame("QuizKampen");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLayout(new BorderLayout());
            frame.add(questionPanel.getPanel(), BorderLayout.CENTER);
            frame.setResizable(true);
            frame.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("QuestionPanel kunde inte asnluta till servern");
        }
    }
}
