package server;

import pojos.EndGame;
import pojos.Question;
import pojos.Waiting;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerProtocol implements Runnable {

    private static final int WAITING = 0;
    private static final int CHOOSE_CATEGORY = 1;
    private static final int CATEGORY_CHOSEN = 11;
    private static final int PLAY_ROUND = 2;
    private static final int SHOW_RESULTS = 3;
    private static final int ANSWER_QUESTION = 4;

    private int state = CHOOSE_CATEGORY;
    private Categories categories = new Categories();
    private List<Question> currentQuestions = new ArrayList<>(2);

    private Socket socket;

    public ServerProtocol(Socket socket) {
        this.socket = socket;
    }

    @SuppressWarnings("unchecked")
    public Object processInput(Object input, int player, GameState gameState) {
        Object output = null;
        int currentCategory;

        if (state == WAITING) {
            output = currentQuestions;
            System.out.println(gameState.getCurrentRound());
        } else if (state == CHOOSE_CATEGORY) {
            if (gameState.getCurrentRound() != 0) {
                int score = gameState.calculateScore((List<String>)input, currentQuestions);
                gameState.addPlayerScore(score, player);
            }
            output = categories.getCategoryString(categories.getCategoryInt("Sport")); //TODO List<String> category random

            state = CATEGORY_CHOSEN;
        } else if (state == CATEGORY_CHOSEN) {
            currentCategory = categories.getCategoryInt((String)input);

            currentQuestions = categories.getTempQuestion(); //TODO skicka random frågor
            output = categories.getCategory(currentCategory);
            if (output != null) {
                categories.setCurrentCategory((List<Question>) output);
            } else System.exit(30);
            gameState.incrementRound();
            state = PLAY_ROUND;
        } else if (state == PLAY_ROUND) {
            int score = gameState.calculateScore((List<String>)input, currentQuestions);
            gameState.addPlayerScore(score, player);
            output = gameState;
            state = SHOW_RESULTS;
        } else if (state == SHOW_RESULTS) {
            if (gameState.getCurrentRound() > 6) { //TODO maxrounds från Settings.properties
                return new EndGame();
            }
            output = new Waiting();
            state = WAITING;
        } else if (state == ANSWER_QUESTION) {
            output = currentQuestions;
            state = CHOOSE_CATEGORY;
        }

        return output;
    }

    @Override
    public void run() {

    }
}