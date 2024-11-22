package server;

import java.util.ArrayList;
import java.util.List;

public class Score {
    List<Integer> scores = new ArrayList<>();


    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(int score) {
        scores.add(score);
    }
    public int getPlayerScore(int player) {
        return scores.get(player);
    }
    public int getScoreForRound(int round) {
        return scores.get(round);
    }


}
