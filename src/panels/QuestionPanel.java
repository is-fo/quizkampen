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
        question.getQuestion();
        JLabel questionLabel = new JLabel(question.getQuestion());
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(questionLabel);

        List<String> answers = question.getAnswers();
        for (String ans : answers) { //TODO svarsalternativ -> returnera en String
            JButton answerButton = new JButton(ans);
            answerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            answerButton.addActionListener(e -> {
                String selectedAnswer = ((JButton) e.getSource()).getText();
                if (selectedAnswer.equals(question.getCorrectAnswer())) {
                    JOptionPane.showMessageDialog(panel, "Rätt svar!");
                } else {
                    JOptionPane.showMessageDialog(panel, "Fel svar. Rätt svar är: " + question.getCorrectAnswer());
                }
                try {
                    oos.writeObject(selectedAnswer);
                    oos.flush();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Fel vid kommunikation med servern.");
                }
            });
            panel.add(answerButton);
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        List<String> answeres = new ArrayList<String>();
        answeres.add("Blå");
        answeres.add("Gul");
        answeres.add("Vit");
        answeres.add("Svart");
        Question q = new Question("Vilken färg har himlen?", answeres, "Blå");
        try {
            Socket socket = new Socket("localhost", 5000);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            QuestionPanel qp = new QuestionPanel(q, oos);
            qp.drawQuestion();
            JFrame frame = new JFrame("QuizKampen");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLayout(new BorderLayout());
            frame.add(qp.getPanel(), BorderLayout.CENTER);
            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("QuestionPanel kunde inte asnluta till servern");
        }
    }
}
