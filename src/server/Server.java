package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    public static final int PORT = 55555;
    public static final int MAX_CLIENTS = 2;

    private int clientCount = 0;

    Socket[] clientSockets = new Socket[MAX_CLIENTS];
    ServerProtocol[] serverProtocols = new ServerProtocol[MAX_CLIENTS];
    ObjectInputStream[] in = new ObjectInputStream[MAX_CLIENTS];
    ObjectOutputStream[] out = new ObjectOutputStream[MAX_CLIENTS];

    private void startServer(ServerSocket serverSocket) {
        try {
            int clientIndex = 0;
             while (clientIndex < MAX_CLIENTS) {
                 System.out.println("Listening for client connection...");
                Socket clientSocket;
                clientSocket = serverSocket.accept();
                clientSockets[clientIndex] = clientSocket;
                 System.out.println("Client " + (clientIndex + 1) + "/" + MAX_CLIENTS + " connected." );
                 System.out.println("Total amount of clients: " + ++clientCount);

                in[clientIndex] = new ObjectInputStream(clientSocket.getInputStream());
                out[clientIndex] = new ObjectOutputStream(clientSocket.getOutputStream());

                out[clientIndex].writeObject(new Intro());

                ServerProtocol protocol = new ServerProtocol(clientSocket);
                serverProtocols[clientIndex] = protocol;

                clientIndex++;

            }

            System.out.println("Two clients connected.");
             new Thread(new GameLogic(clientSockets, in, out, serverProtocols)).start();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error accepting client connections: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port: " + PORT);

            while (true) {
                startServer(serverSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error starting server: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        new Thread(server).start();
    }
}
