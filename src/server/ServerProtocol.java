package server;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static server.Categories.*;

public class ServerProtocol implements Runnable {
    private static final int WAITING = 0;
    private static final int CHOOSE_CATEGORY = 1;
    private static final int PLAY_ROUND = 2;
    private static final int SHOW_RESULTS = 3;

    private int state = WAITING;
    private Categories categories = new Categories();
    private GameState gameState = new GameState(1, 2);
    private List<Question> currentQuestions = new ArrayList<>(2);

    private Socket socket;

    public ServerProtocol(Socket socket) {
        this.socket = socket;
    }

    @SuppressWarnings("unchecked")
    public Object processInput(Object input, int player) {
        Object output = null;
        int category = 0;

        if (state == WAITING) {
            output = gameState.getResults();
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
        }

        return output;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            Object o;
            while ((o = in.readObject()) != null) {
                out.writeObject(processInput(o));
                System.out.println("Input processed.");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage() + "fel i serverprotocol");
        } catch (ClassNotFoundException e) {
            System.err.println("Unexpected type: " + e.getMessage());
        }
    }

}
