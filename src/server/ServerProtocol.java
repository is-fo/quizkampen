package server;

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
    List<String> categoriesString = new ArrayList<>(Arrays.asList(categories.getCategoryString(categories.SPORT), categories.getCategoryString(categories.GEOGRAPHY)));

    private Socket socket;

    public ServerProtocol(Socket socket) {
        this.socket = socket;
    }

    @SuppressWarnings("unchecked")
    public Object processInput(Object input, int player, GameState gameState) {
        Object output = null;
        int currentCategory = -1;
        int scoreCurrentRound = 0;
        
        if (state == WAITING) {
            output = new Waiting();
            if (player == 0 && gameState.getCurrentRound() == 0) {
                state = CHOOSE_CATEGORY;
            } else {
                state = ANSWER_QUESTION;
            }
        } else if (state == CHOOSE_CATEGORY) {
            output = categoriesString;
            state = CATEGORY_CHOSEN;
        } else if (state == CATEGORY_CHOSEN) {
            currentCategory = categories.getCategoryInt((String)input);
            output = categories.getCategory(currentCategory);
            if (output != null) {
                categories.setCurrentCategory((List<Question>) output);
            } else System.exit(30);
            gameState.incrementRound();
            state = PLAY_ROUND;
        } else if (state == PLAY_ROUND) {
            currentQuestions = categories.getNQuestions(2, currentCategory);
            output = currentQuestions;
            state = SHOW_RESULTS;
        } else if (state == SHOW_RESULTS) {
            List<?> answersCurrentCategory = (List<?>) input;
            for (int i = 0 ; i < answersCurrentCategory.size(); i++) {
                if (answersCurrentCategory.equals(categories.getNQuestions(2, currentCategory).get(i).getCorrectAnswer())){
                    scoreCurrentRound++;
                }
            }
            gameState.updatePlayerScores(currentCategory, scoreCurrentRound, player);
            output = gameState.getResults();
            state = WAITING;
        } else if (state == ANSWER_QUESTION) {
            output = currentQuestions;
            
//            if (player == 0 && gameState.getCurrentRound() % 2 == 0) {
//                gameState.incrementRound();
//            } else if (player == 1 && gameState.getCurrentRound() % 2 == 1) {
//                gameState.incrementRound();
//            }

            state = CHOOSE_CATEGORY;
        }

        return output;
    }

    @Override
    public void run() {

    }

}
