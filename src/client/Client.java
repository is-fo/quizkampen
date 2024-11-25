package client;

import panels.CategoryPanel;
import pojos.EndGame;
import pojos.Intro;
import pojos.Question;
import pojos.Waiting;
import pojos.GameState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Client {

    private int roundsPerGame;

    Client() {
        String hostName = "192.168.0.35";
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
                } else if (fromServer instanceof String) {
                    System.out.println(fromServer + "<- category received");
                    oos.writeObject("Sport");
                } else if (fromServer instanceof List<?>) {
                    List<?> receivedList = (List<?>) fromServer;
                    if (!receivedList.isEmpty() && receivedList.getFirst() instanceof Question q) {
                        System.out.println(q.getCorrectAnswer());
                        List<String> answers = new ArrayList<>();
                        answers.add(q.getCorrectAnswer());
                        oos.writeObject(answers);
                    }
                    else if (!receivedList.isEmpty() && receivedList.getFirst() instanceof String) {
                        CategoryPanel cp = new CategoryPanel((List<String>)fromServer, oos);
                        cp.drawCategories();
                    }
                } else if (fromServer instanceof GameState gameState) {
                    System.out.println(gameState.getPlayerScores());
                    oos.writeObject(fromServer);
                } else if (fromServer instanceof Waiting) {
                    oos.writeObject(fromServer);
                } else if (fromServer instanceof EndGame) {
                    oos.close();
                    ois.close();
                    addressSocket.close();
                    break;
                }
                oos.flush();
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


