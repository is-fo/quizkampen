package panels;

import java.util.List;

public class Question {

    private String questionQ;
    private List<AnswerAlternative> answerAlternatives;

    public Question(String questionQ, List<AnswerAlternative> answerAlternatives) {
        this.questionQ = questionQ;
        this.answerAlternatives = answerAlternatives;
    }

    public String getQuestionQ() {
        return questionQ;
    }

    public List<AnswerAlternative> getAnswerAlternatives() {
        return answerAlternatives;
    }
}

