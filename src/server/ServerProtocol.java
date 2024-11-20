package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerProtocol implements Runnable {
    private static final int WAITING = 0;
    private static final int CHOOSE_CATEGORY = 1;
    private static final int PLAY_ROUND = 2;
    private static final int SHOW_RESULTS = 3;
    private static final int ANSWER_QUESTION = 4;

    private int state = WAITING;
    private Categories categories = new Categories();
    private List<Question> currentQuestions = new ArrayList<>(2);

    private Socket socket;

    public ServerProtocol(Socket socket) {
        this.socket = socket;
    }

    @SuppressWarnings("unchecked")
    public Object processInput(Object input, int player, GameState gameState) {
        Object output = null;

        if (state == WAITING) {
            output = null; //TODO ett intro objekt
            state = CHOOSE_CATEGORY;
        } else if (state == CHOOSE_CATEGORY) {
            output = categories.getCategory(Integer.parseInt((String)input));
            if (output != null) {
                categories.setCurrentCategory((List<Question>) output);
            } else System.exit(30);
            state = PLAY_ROUND;
        } else if (state == PLAY_ROUND) {
            currentQuestions = categories.getNQuestions(2, Integer.parseInt((String)input));
            output = currentQuestions;
            state = SHOW_RESULTS;
        } else if (state == SHOW_RESULTS) {
            gameState.updatePlayerScores(player, (Integer) input);

            output = gameState.getResults();
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
