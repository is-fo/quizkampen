package panels;

public class AnswerAlternative {

    private String answer;
    private boolean correctAnswer;

    public AnswerAlternative(String answer, boolean correctAnswer) {
        this.answer = answer;
        this.correctAnswer = correctAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }
}
