package Panels;

import javax.swing.*;

public class AnswerButton extends JButton {

    private boolean isCorrect;

    public void setButton(AnswerAlternative answerAlternative){
        isCorrect = answerAlternative.isCorrectAnswer();
        this.setText(answerAlternative.getAnswer());
        this.putClientProperty("korrekt", isCorrect);
    }
    public boolean isCorrect(){
        return isCorrect;
    }
}
