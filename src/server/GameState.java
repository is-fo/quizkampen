package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import static server.Categories.*;

public class GameState implements Serializable {

    private List<Score> playerScores = new ArrayList<>();
    private int currentRound = 0;
    private int category = SPORT;



    public GameState(int numRounds, int numQuestions) {

        for (int i = 0; i < 2; i++) {
            playerScores.add(new Score());
        }
    }

    public void updatePlayerScores(int currentRound, int score) {
        playerScores.get(currentRound).setScores(currentRound, score);
    }

    public void incrementRound() {
        currentRound++;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Integer getScore(int player) {
        return playerScores.get(player).getScoreForRound(currentRound);
    }

    public GameState getResults() {
        return this;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public List<Score> getPlayerScores() {
        return playerScores;
    }

    public int getCategory() {
        return category;
    }
}
