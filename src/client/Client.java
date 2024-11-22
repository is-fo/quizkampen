package client;

import pojos.Intro;
import pojos.Question;
import server.GameState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;


public class Client {

    private int roundsPerGame;

    Client() {
        String hostName = "localhost";
        int portNumber = 55555;

        try (
            Socket addressSocket = new Socket(hostName, portNumber);
            ObjectOutputStream oos = new ObjectOutputStream(addressSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(addressSocket.getInputStream())
        ) {
            while (true) {
                Object fromServer = ois.readObject();

                if (fromServer instanceof Intro) {
                    oos.writeObject(fromServer);
                } else if (fromServer instanceof String) { //TODO fromserver List<String> categories
                    System.out.println(fromServer + "<- category received");
                    oos.writeObject("Sport");
                } else if (fromServer instanceof List<?>) {
                    List<?> receivedList = (List<?>) fromServer;
                    if (!receivedList.isEmpty() && receivedList.getFirst() instanceof Question q) {
                        System.out.println(q);
                        oos.writeObject(q.getCorrectAnswer());
                    }
                } else if (fromServer instanceof GameState gameState) {
                    System.out.println(gameState.getPlayerScores());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error reading object from server: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new Client();
    }
}


