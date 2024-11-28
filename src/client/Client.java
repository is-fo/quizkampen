package client;

import panels.CategoryPanel;
import panels.QuestionPanel;
import panels.ResultPanel;
import pojos.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;


public class Client {

    private int roundsPerGame;

    @SuppressWarnings("unchecked")
    Client() {
        String hostName = "localhost";
        int portNumber = 55555;


        try (
            Socket addressSocket = new Socket(hostName, portNumber);
            ObjectOutputStream oos = new ObjectOutputStream(addressSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(addressSocket.getInputStream())
        ) {
            ResultPanel resultPanel = null;
            while (true) {
                Object fromServer = ois.readObject();

                if (fromServer instanceof Intro i) {
                    resultPanel = new ResultPanel(oos, i.getRoundsPerGame());
                    resultPanel.createWindow(i.getPlayer());
                } else if (fromServer instanceof List<?> receivedList) {
                    resultPanel.enablePlayButton();
                    if (!receivedList.isEmpty() && receivedList.getFirst() instanceof Question q) {
                        QuestionPanel qp = new QuestionPanel((List<Question>) receivedList, oos);
                        qp.drawAll();
                    } else if (!receivedList.isEmpty() && receivedList.getFirst() instanceof String) {
                        CategoryPanel cp = new CategoryPanel((List<String>) fromServer, oos);
                        cp.drawCategories();
                    }
                } else if (fromServer instanceof GameState g) {
                    assert resultPanel != null;
                    resultPanel.updateWindow(g.getPlayerScores(), g.getCategories());
                } else if (fromServer instanceof Waiting) {
                    resultPanel.disablePlayButton();
                    oos.writeObject(fromServer);
                } else if (fromServer instanceof EndGame eg) {
                    oos.close();
                    ois.close();
                    addressSocket.close();
                    resultPanel.updateWindow(eg.getGameState().getPlayerScores(), eg.getGameState().getCategories());
                    resultPanel.disablePlayButton();
                    break;
                } else if (fromServer instanceof Connected) {
                    if (resultPanel != null) {
                        resultPanel.notifyNextPlayer();
                    }
                } else if (fromServer instanceof String s) {
                    if (resultPanel != null) {
                        resultPanel.notifyNextPlayer(s);
                    }
                }
                oos.flush();
                }
            } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error reading object from server: " + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(12);
            }
    }

    public static void main(String[] args) {
        new Client();
    }
}


