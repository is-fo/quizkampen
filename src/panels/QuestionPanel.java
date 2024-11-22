package panels;

import pojos.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionPanel {

    private Question question;

    public QuestionPanel(Question question) {
        this.question = question;
    }

    public void drawQuestion() {
        //TODO en label med frågar
        question.getQuestion();

        List<String> answers = question.getAnswers();
        for (String ans : answers) {
            //TODO svarsalternativ -> returnera en String
        }
    }

    public static void main(String[] args) {
        List<String> answeres = new ArrayList<String>();
        answeres.add("Blå");
        answeres.add("Gul");
        answeres.add("Vit");
        answeres.add("Svart");
        Question q = new Question("Vilken färg har himlen?", answeres, "Blå");
        QuestionPanel qp = new QuestionPanel(q);
        qp.drawQuestion();
    }
}
