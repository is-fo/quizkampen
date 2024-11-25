package pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Score implements Serializable {
    List<Integer> scores = new ArrayList<>();

    public List<Integer> getScores() {
        return scores;
    }

    public void addScore(int player, int score) {
        scores.add(score);
    }

    public int getScoreForRound(int round) {
        return scores.get(round);
    }
}
