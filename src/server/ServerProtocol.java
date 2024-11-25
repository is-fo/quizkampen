package server;

import pojos.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerProtocol implements Runnable {

    private static final int WAITING = 0;
    private static final int CHOOSE_CATEGORY = 1;
    private static final int CATEGORY_CHOSEN = 11;
    private static final int PLAY_ROUND = 2;
    private static final int SHOW_RESULTS = 3;
    private static final int ANSWER_QUESTION = 4;

    private static int state = WAITING;
    private static Categories categories = new Categories();
    private static List<Question> currentQuestions = new ArrayList<>(2);

    private Socket socket;

    public ServerProtocol(Socket socket) {
        this.socket = socket;
    }

    @SuppressWarnings("unchecked")
    public static Object processInput(Object input, int player, GameState gameState) {
        Object output = null;
        int currentCategory;

        if (state == WAITING) {
            System.out.println("STATE == WAITING: " + player);
            state = (gameState.getCurrentRound() != 0 || player == 1) ? ANSWER_QUESTION : CHOOSE_CATEGORY;
            output = (gameState.getCurrentRound() != 0 || player == 1) ? currentQuestions : new Waiting();
        } else if (state == CHOOSE_CATEGORY) {
            System.out.println("STATE == CHOOSE_CATEGORY: " + player);
            if (gameState.getCurrentRound() != 0) {
                int score = gameState.calculateScore((List<String>)input, currentQuestions);
                gameState.addPlayerScore(score, player);
            }
            output = categories.getCategoriesStringList(2);

            state = CATEGORY_CHOSEN;
        } else if (state == CATEGORY_CHOSEN) {
            System.out.println("STATE == CATEGORY_CHOSEN: " + player);
            currentCategory = categories.getCategoryInt((String)input);
            gameState.addCategory((String)input);
            currentQuestions = categories.getTempQuestion(); //TODO skicka random frågor
            System.out.println(currentQuestions + "<---------------");
            output = categories.getCategory(currentCategory);
            if (output != null) {
                categories.setCurrentCategory((List<Question>) output);
            } else System.exit(30);
            gameState.incrementRound();
            state = PLAY_ROUND;
        } else if (state == PLAY_ROUND) {
            System.out.println("STATE == PLAY_ROUND: " + player);
            if (input instanceof List l) {
                int score = gameState.calculateScore(l, currentQuestions);
                gameState.addPlayerScore(score, player);
            } else {
                System.err.println(input.getClass().getSimpleName());
                throw new RuntimeException("Not a list");
            }

            output = gameState;
            state = SHOW_RESULTS;
        } else if (state == SHOW_RESULTS) {
            System.out.println("STATE == SHOW_RESULTS: " + player);
            if (gameState.getCurrentRound() > 6) { //TODO maxrounds från Settings.properties
                return new EndGame();
            }
            output = new Waiting();
            state = WAITING;
        } else if (state == ANSWER_QUESTION) { //input == svar
            System.out.println("ANSWER_QUESTION: " + player);
            if (input instanceof List l) {
                int score = gameState.calculateScore(l, currentQuestions);
                gameState.addPlayerScore(score, player);
            } else {
                System.err.println(input.getClass().getSimpleName());
                throw new RuntimeException("Not a list");
            }
            output = categories.getCategoriesStringList(2);
            state = CATEGORY_CHOSEN;
        }

        return output;
    }

    @Override
    public void run() {

    }
}