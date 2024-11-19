package server;



import java.util.List;

import static server.Categories.*;

public class ServerProtocol {
    private static final int WAITING = 0;
    private static final int CHOOSE_CATEGORY = 1;
    private static final int PLAY_ROUND = 2;
    private static final int SHOW_RESULTS = 3;

    private int state = WAITING;
    private Categories categories;
    private GameState gameState;

    @SuppressWarnings("unchecked")
    public Object processInput(String input) {
        Object output = null;
        int category = 0;

        if (state == WAITING) {
            output = gameState.getResults();
            state = CHOOSE_CATEGORY;
        } else if (state == CHOOSE_CATEGORY) {
            output = categories.getCategory(Integer.parseInt(input));
            if (output != null) {
                categories.setCurrentCategory((List<Question>) output);
            } else System.exit(30);
            state = PLAY_ROUND;
        } else if (state == PLAY_ROUND) {
            output = categories.getNQuestions(2, Integer.parseInt(input));
            state = SHOW_RESULTS;
        } else if (state == SHOW_RESULTS) {
            output = gameState.getResults();
            state = WAITING;
        }

        return output;
    }

}
