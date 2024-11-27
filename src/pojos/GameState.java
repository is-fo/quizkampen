package pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {

    private final List<Score> playerScores = new ArrayList<>();
    private final List<String> categories = new ArrayList<>();

    private int currentRound = 0;

    public GameState() {

        for (int i = 0; i < 2; i++) {
            playerScores.add(new Score());
        }
    }

    public void addCategory(String category) {
        categories.add(category);
    }

    public String getCategory(int round) {
        return categories.get(round);
    }

    public String getLastCategory() {
        return categories.getLast();
    }

    public List<String> getCategories() {
        return categories;
    }

    public Integer calculateScore(List<String> answers, List<Question> questions) {
        Integer correctAnswers = 0;
        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).equals(questions.get(i).getCorrectAnswer())) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    public void addPlayerScore(int score, int player) {
        playerScores.get(player).addScore(score);
    }

    public void incrementRound() {
        currentRound++;
    }

    public Integer getScore(int player) {
        return playerScores.get(player).getScoreForRound(currentRound);
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public List<Score> getPlayerScores() {
        return playerScores;
    }

    public GameState getGamestate() {
        return this;
    }

}
