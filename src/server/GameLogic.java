package server;

import pojos.GameState;
import pojos.Intro;

import java.io.*;
import java.net.Socket;
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
                out[i].writeObject(intro);
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
                    Object processed;
                    processed = protocol.processInput(o, currentClient, gameState, intro);
                    out[currentClient].writeObject(processed);
                    System.out.println("Sent:  " + processed + " to client: " + (currentClient));
                    if (processed instanceof GameState) {
                        currentClient = (currentClient + 1) % MAX_CLIENTS; //nextClient()
                    }
                }

            } catch (ClassNotFoundException e) {
                System.err.println("Error reading object: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Error reading from client, closing connections." + e.getMessage());
                for (int i = 0; i < MAX_CLIENTS; i++) {
                    try {
                        clientSockets[i].close();
                        in[i].close();
                        out[i].close();
                    } catch (IOException ex) {
                        System.err.println("Error closing socket connections: " + ex.getMessage());
                    }
                }
                break;
            }
        }
    }
}
