package pojos;

import java.io.Serializable;

public class Intro implements Serializable {

    private int questionsPerRound;
    private int roundsPerGame;

    public Intro(int questionsPerRound, int roundsPerGame) {
        this.questionsPerRound = questionsPerRound;
        this.roundsPerGame = roundsPerGame;
    }
}
