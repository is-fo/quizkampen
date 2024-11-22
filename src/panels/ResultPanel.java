package panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;




public class ResultPanel extends JPanel  {
    private JLabel[] player1Scores;
    private JLabel[] categories;
    private JLabel[] player2Scores;
    private JButton playAgainButton;
    private JButton geUppButton;
    private JPanel buttonPanel = new JPanel();

    public ResultPanel(String player1Name, String player2Name) {
        setLayout(new BorderLayout());
        JPanel resultPanel = new JPanel(new GridLayout(7, 3, 5, 5));

        resultPanel.add(new JLabel(player1Name, SwingConstants.CENTER));
        resultPanel.add(new JLabel("Kategori", SwingConstants.CENTER));
        resultPanel.add(new JLabel(player2Name, SwingConstants.CENTER));

        player1Scores = new JLabel[6];
        categories = new JLabel[6];
        player2Scores = new JLabel[6];

        for (int i = 0; i < 6; i++) {
            player1Scores[i] = new JLabel("", SwingConstants.CENTER);
            categories[i] = new JLabel("", SwingConstants.CENTER);
            player2Scores[i] = new JLabel("", SwingConstants.CENTER);

            resultPanel.add(player1Scores[i]);
            resultPanel.add(categories[i]);
            resultPanel.add(player2Scores[i]);
        }

        geUppButton = new JButton("   Ge upp       ");
        geUppButton.addActionListener(e -> {
            // Skicka 'give_up' till servern
            sendGiveUpToServer();

            // Stäng anslutningen till servern (antag att du har en socket anslutning)
            closeConnection();

            // Skapa och hantera "Spela igen"-knappen
            playAgainButton = new JButton("    Spela igen   ");
            playAgainButton.addActionListener(e1 -> reset());

            // Lägg till knapparna i panelen
            add(resultPanel, BorderLayout.CENTER);
            buttonPanel.add(geUppButton);
            buttonPanel.add(playAgainButton);
            add(buttonPanel, BorderLayout.SOUTH);

            // Inaktivera Ge upp-knappen så att den inte kan klickas igen
            geUppButton.setEnabled(false);
        });}

    public void updateRound(int round, int player1Score, int player2Score, String category){
            if (round < 1 || round > 6) {
                throw new IllegalArgumentException("Omgången måste vara mellan 1 och 6.");
            }
            player1Scores[round - 1].setText(String.valueOf(player1Score));
            player2Scores[round - 1].setText(String.valueOf(player2Score));
            categories[round - 1].setText(category);
        }

    public void reset() {
        for (int i = 0; i < 6; i++) {
            player1Scores[i].setText("");
            categories[i].setText("");
            player2Scores[i].setText("");
        }
    }
        private void sendGiveUpToServer() {
            try {

                out.writeObject("ge upp");
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Error sending give-up message");
            }
        }
        private void closeConnection() {
            try {
                if (oos != null) {
                    oos.close();  // Stänger output stream
                }
                if (addressSocket != null) {
                    socket.close();  // Stänger socket-anslutningen
                }
                System.out.println("Anslutningen till servern stängdes.");
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Error while closing connection: " + ex.getMessage());
            }
        }

    public JButton getPlayAgainButton() {
        return playAgainButton;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("QuizKampen Resultat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ResultPanel resultPanel = new ResultPanel("Spelare 1", "Spelare 2");
        frame.add(resultPanel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        // Exempel på att uppdatera resultat
        resultPanel.updateRound(1, 10, 5, "Geografi");
        resultPanel.updateRound(2, 20, 15, "Historia");

    }
}

