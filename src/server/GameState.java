package server;

import java.util.ArrayList;
import java.util.List;

import static server.Categories.*;

public class GameState {

    private List<List<Integer>> playerScores = new ArrayList<>();
    private int currentRound = 0;
    private int category = SPORT;

    public GameState(int numRounds, int numQuestions) {

        for (int i = 0; i < 2; i++) {
            playerScores.add(new ArrayList<>());
        }

    }

    public void updatePlayerScores(int player, int score) {
        playerScores.get(player).add(score);
    }

    public List<List<Integer>> getPlayerScores() {
        return playerScores;
    }

    public void incrementRound() {
        currentRound++;
    }

    public int getCurrentRound() {
        return currentRound;
    }
}
