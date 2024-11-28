package server;

import pojos.Connected;
import pojos.EndGame;
import pojos.GameState;
import pojos.Intro;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

import static server.Server.MAX_CLIENTS;

public class GameLogic implements Runnable {
    private final Socket[] clientSockets;
    private final ObjectInputStream[] in;
    private final ObjectOutputStream[] out;
    private final ServerProtocol protocol;

    public GameLogic(Socket[] clientSockets, ObjectInputStream[] in, ObjectOutputStream[] out, ServerProtocol protocol) {
        this.clientSockets = clientSockets;
        this.in = in;
        this.out = out;
        this.protocol = protocol;
    }

    @Override
    public void run() {

        Properties p = new Properties();
        try {
            p.load(new FileInputStream("src/server/Settings.properties"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        int roundsPerGame = Integer.parseInt(p.getProperty("roundsPerGame", "2"));
        int questionsPerRound = Integer.parseInt(p.getProperty("questionsPerRound", "2"));


        GameState gameState = new GameState();
        Intro intro = new Intro(questionsPerRound, roundsPerGame, gameState);

        int currentClient = 0;

        for (int i = 0; i < MAX_CLIENTS; i++) {
            try {
                out[i].writeObject(new Intro(intro, i));
            } catch (IOException e) {
                System.err.println("Error intro: " + e.getMessage());
            }
        }


        while (true) {
            try {
                System.out.println("Run started in GameLogic");
                Object o;
                while ((o = in[currentClient].readObject()) != null) {
                    System.out.println("Received: " + o + " client: " + (currentClient));
                    if (o instanceof String s) {
                        out[nextClient(currentClient)].writeObject(s);
                    }
                    Object processed;
                    processed = protocol.processInput(o, currentClient, intro);
                    out[currentClient].writeObject(processed);
                    System.out.println("Sent:  " + processed + " to client: " + (currentClient));
                    out[currentClient].reset();
                    if (processed instanceof GameState) {
                        currentClient = nextClient(currentClient);
                    } else if (processed instanceof EndGame) {
                        currentClient = nextClient(currentClient);
                        out[currentClient].writeObject(processed);
                        System.out.println("Game ended. Closing connections...");
                        closeConnections();
                    } else if (processed instanceof List<?>) {
                        if (!((List<?>) processed).isEmpty() && ((List<?>) processed).get(0) instanceof String) {
                            System.out.println("Sent notification to nextclient=)");
                            out[nextClient(currentClient)].writeObject(new Connected());
                        }
                    }
                }

            } catch (ClassNotFoundException e) {
                System.err.println("Error reading object: " + e.getMessage());
            } catch (IOException | RuntimeException e) {
                System.err.println("Error reading from client, closing connections." + e.getMessage());
                closeConnections();
                break;
            }
        }
    }

    private int nextClient(int i) {
        return (i + 1) % MAX_CLIENTS;
    }

    private void closeConnections() {
        for (int i = 0; i < MAX_CLIENTS; i++) {
            try {
                clientSockets[i].close();
                in[i].close();
                out[i].close();
            } catch (IOException ex) {
                System.err.println("Error closing socket connections: " + ex.getMessage());
            }
        }
    }
}
