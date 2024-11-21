package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

import static server.Server.MAX_CLIENTS;

public class GameLogic implements Runnable {
    private final Socket[] clientSockets;
    private final ObjectInputStream[] in;
    private final ObjectOutputStream[] out;
    private final ServerProtocol[] serverProtocols;

    public GameLogic(Socket[] clientSockets, ObjectInputStream[] in, ObjectOutputStream[] out, ServerProtocol[] serverProtocols) {
        this.clientSockets = clientSockets;
        this.in = in;
        this.out = out;
        this.serverProtocols = serverProtocols;
    }

    @Override
    public void run() {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream("src/PropertiesDemo/DemoProperties.properties"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        int roundsPerGame = Integer.parseInt(p.getProperty("roundsPerGame", "2"));
        int questionsPerRound = Integer.parseInt(p.getProperty("questionsPerRound", "2"));

        GameState gameState = new GameState(roundsPerGame, questionsPerRound);
        int currentClient = 0;
        for (int i = 0; i < MAX_CLIENTS; i++) {
            try {
                out[i].writeObject(p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        while (true) {
            try {
                Object o;
                while ((o = in[currentClient].readObject()) != null) {
                    Object processed;
                    processed = serverProtocols[currentClient].processInput(o, currentClient, gameState);
                    out[currentClient].writeObject(processed);
                    System.out.println("Sent:  " + o + " to client: " + currentClient);
                    if (processed instanceof GameState) {
                        currentClient = (currentClient + 1) % MAX_CLIENTS; //nextClient()
                    }
                }
            } catch (ClassNotFoundException e) {
                System.err.println("Error reading object: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Error writing to server: " + e.getMessage());
            }
        }
    }
}
