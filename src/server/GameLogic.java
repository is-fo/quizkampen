package server;

import client.Connected;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
        GameState gameState = new GameState(1, 2);
        int currentClient = 0;

        while (true) {
            try {
                Object o;
                while ((o = in[currentClient].readObject()) != null) {
                    if (o instanceof Connected) {
                        System.out.println("Client connection OK");
                    }
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
                System.err.println("Error reading from client: " + e.getMessage());
                break;
            }
        }
    }
}
