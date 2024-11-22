package client;

import panels.ResultPanel;
import pojos.Connected;
import pojos.Question;
import pojos.Waiting;
import server.GameState;
import pojos.Intro;
import panels.GamePanel;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Client {

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
                System.out.println("hej");

                if (fromServer instanceof Intro) {
                    //TODO: skapa X antal labels i Resultatpaneler beroende på antal roundsPerGame från intro Object
                    Intro intro = (Intro) fromServer;
                    int roundsPerGame = intro.getRoundsPerGame();
                    System.out.println("Antal rundor per spel: " + roundsPerGame);

                    SwingUtilities.invokeLater(() -> {
                        ResultPanel resultPanel = new ResultPanel("Spelare 1", "Spelare 2", roundsPerGame);
                        JFrame frame = new JFrame("Resultat");
                        frame.add(resultPanel);
                        frame.setSize(800, 600);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setVisible(true);
                    });

                    oos.writeObject(new Connected());
                    oos.writeObject(fromServer);

                    //System.out.println("Anslutning upprättad ");
                    //oos.writeObject(new Connected());
                    //oos.writeObject(fromServer);
                } else if (fromServer instanceof Waiting) {
                    System.out.println("Tjena");
                    oos.writeObject(fromServer);
                } else if (fromServer instanceof List<?>) {
                    List<?> receivedList = (List<?>) fromServer;
                    if (!receivedList.isEmpty() && receivedList.get(0) instanceof Question) {
                        List<Question> questions = (List<Question>) receivedList;
                        System.out.println("Han är lugn");
                        for (Question q : questions) {
                            System.out.println(q.getQuestion());
                        }
                    } else if (!receivedList.isEmpty() && receivedList.get(0) instanceof String) {
                        //TODO logik för att välja kategori
                        //lär iterera över listan man får in, skapa en knapp för varje
                        //skicka till servern en String med texten på tryckt knapp
                        List<String> categories = (List<String>) receivedList;
                        GamePanel gamePanel;
                        SwingUtilities.invokeLater(() -> gamePanel.showCategorySelection(categories, selectedCategory -> {
                            try {
                                oos.writeObject(selectedCategory);
                                oos.flush();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }));
                    }
                } else if (fromServer instanceof GameState) {
                    System.out.println("Spelstatus");
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


