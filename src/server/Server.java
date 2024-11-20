package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    public final int PORT = 55555;

    Socket[] clientSockets = new Socket[2];
    ServerProtocol[] serverProtocols = new ServerProtocol[2];

    private void startServer() {
        try {
            int clientIndex = 0;
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                while ((clientSockets[0] == null && clientSockets[1] == null) || clientIndex < clientSockets.length) {
                    Socket clientSocket;
                    clientSocket = serverSocket.accept();

                    clientSockets[clientIndex] = clientSocket;
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

    }

    @Override
    public void run() {
        startServer();
        handleClients();
    }
}
