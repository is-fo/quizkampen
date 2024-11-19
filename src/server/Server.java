package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    public final int PORT = 55555;

    Socket[] clientSockets = new Socket[2];

    public Server() {

    }

    private void startServer() {
        try {
            int clientIndex = 0;
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                while (clientSockets[0] == null && clientSockets[1] == null) {
                    Socket clientSocket;
                    clientSocket = serverSocket.accept();

                    clientSockets[clientIndex] = clientSocket;
                    System.out.println("Client " + clientIndex + " connected");

                    clientIndex++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error starting server: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        startServer();
    }
}
