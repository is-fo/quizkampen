package server;

import pojos.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import static server.Categories.*;

public class GameState implements Serializable {

    private List<Score> playerScores = new ArrayList<>();
    private List<String> categories = new ArrayList<>();
    private int currentRound = 0;
    private int category = SPORT;



    public GameState(int numRounds, int numQuestions) {

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

    public void updatePlayerScores(int score, int player) {
        playerScores.get(player).setScores(currentRound, score);
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

    public GameState getResults() {
        return this;
    }

}
