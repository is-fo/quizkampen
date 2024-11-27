package pojos;

import java.io.Serializable;

public class Intro implements Serializable {

    private int questionsPerRound;
    private int roundsPerGame;
    private GameState gameState;

    public Intro(int questionsPerRound, int roundsPerGame, GameState gameState) {
        this.questionsPerRound = questionsPerRound;
        this.roundsPerGame = roundsPerGame;
        this.gameState = gameState;
    }

    public int getRoundsPerGame() {
        return roundsPerGame;
    }

    public int getQuestionsPerRound() {
        return questionsPerRound;
    }

    public GameState getGameState() {
        System.out.println(gameState + "<-------------getGameState()");
        return gameState;
    }
}
