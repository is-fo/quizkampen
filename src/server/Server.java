package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    public final int PORT = 55555;

    Socket[] clientSockets = new Socket[2];
    ServerProtocol[] serverProtocols = new ServerProtocol[2];
    ObjectInputStream[] in;
    ObjectOutputStream[] out;

    private void startServer() {
        try {
            int clientIndex = 0;
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                while ((clientSockets[0] == null && clientSockets[1] == null) || clientIndex < clientSockets.length) {
                    Socket clientSocket;
                    clientSocket = serverSocket.accept();

                    clientSockets[clientIndex] = clientSocket;
                    in[clientIndex] = new ObjectInputStream(clientSocket.getInputStream());
                    out[clientIndex] = new ObjectOutputStream(clientSocket.getOutputStream());
                    System.out.println("Client " + clientIndex + " connected");
                    ServerProtocol protocol = new ServerProtocol(clientSocket);
                    serverProtocols[clientIndex] = protocol;
//                    if (clientIndex == 0) {
//                        new Thread(serverProtocols[0]).start();
//                    }

                    clientIndex++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error starting server: " + e.getMessage());
        }
    }

    private void handleClients() {
        GameState gameState = new GameState(1, 2);
        int currentClient = 0;

        while (true) {
            try {
                Object o;
                while ((o = in[currentClient].readObject()) != null) {
                    out[currentClient].writeObject(serverProtocols[currentClient].processInput(o, currentClient, gameState));
                    System.out.println("Sent:  " + o + " to client: " + currentClient);
                    if (o instanceof GameState) {
                        currentClient = (currentClient + 1) % 2; //nextClient()
                    }
                }
            } catch (ClassNotFoundException e) {
                System.err.println("Error reading object: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Error writing to server: " + e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        startServer();
        handleClients();
    }
}
