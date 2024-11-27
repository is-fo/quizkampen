package pojos;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Score implements Serializable {

    @Serial
    private static final long serialVersionUID = 8597013214444225635L;

    List<Integer> scores = new ArrayList<>();

    public List<Integer> getScores() {
        return scores;
    }

    public void addScore(int score) {
        scores.add(score);
    }

    public int getScoreForRound(int round) {
        return scores.get(round);
    }

    @Override
    public String toString() {
        return scores.toString();
    }
}
