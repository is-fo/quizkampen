package pojos;

import java.io.Serializable;

public class Intro implements Serializable {

    private int questionsPerRound;
    private int roundsPerGame;
    private int player;
    private GameState gameState;

    public Intro(int questionsPerRound, int roundsPerGame, GameState gameState) {
        this.questionsPerRound = questionsPerRound;
        this.roundsPerGame = roundsPerGame;
        this.gameState = gameState;
    }

    public Intro(Intro intro, int player) {
        this(intro.questionsPerRound, intro.roundsPerGame, intro.gameState);
        this.player = player;
    }

    public int getRoundsPerGame() {
        return roundsPerGame;
    }

    public int getQuestionsPerRound() {
        return questionsPerRound;
    }

    public int getPlayer() {
        return player;
    }

    public GameState getGameState() {
        return gameState;
    }
}
