package Panels;

import javax.swing.*;

public class AnswerButton extends JButton {

    private boolean isCorrect;

    public void setButton(AnswerAlternative answerAlternative){
        isCorrect = answerAlternative.isCorrectAnswer();
    }
    public boolean isCorrect(){
        return isCorrect;
    }
}
