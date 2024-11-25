package panels;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

//TODO Koppla GameState till ResultPanel
public class ResultPanel extends JPanel {
    private final List<JLabel> player1Scores = new ArrayList<>();
    private final List<JLabel> categories = new ArrayList<>();
    private final List<JLabel> player2Scores = new ArrayList<>();
    private JButton playAgainButton;

    public ResultPanel(String player1Name, String player2Name, int roundsPerGame) {
        setLayout(new BorderLayout());
        JPanel resultPanel = new JPanel(new GridLayout(roundsPerGame + 1, 3, 5, 5));

        resultPanel.add(new JLabel(player1Name, SwingConstants.CENTER));
        resultPanel.add(new JLabel("Kategori", SwingConstants.CENTER));
        resultPanel.add(new JLabel(player2Name, SwingConstants.CENTER));

        for (int i = 0; i < roundsPerGame; i++) {
            JLabel player1Label = new JLabel("", SwingConstants.CENTER);
            JLabel categoryLabel = new JLabel("", SwingConstants.CENTER);
            JLabel player2Label = new JLabel("", SwingConstants.CENTER);

            player1Scores.add(player1Label);
            categories.add(categoryLabel);
            player2Scores.add(player2Label);

            resultPanel.add(player1Label);
            resultPanel.add(categoryLabel);
            resultPanel.add(player2Label);
        }

        playAgainButton = new JButton("Spela igen");
        playAgainButton.addActionListener(e -> reset());
        add(resultPanel, BorderLayout.CENTER);
        add(playAgainButton, BorderLayout.SOUTH);

    }
    public void updateRound(int round, int player1Score, int player2Score, String category) {
        if (round < 1 || round > player1Scores.size()) {
            throw new IllegalArgumentException("Omgången måste vara mellan 1 och 6.");
        }
        player1Scores.get(round - 1).setText(String.valueOf(player1Score));
        categories.get(round - 1).setText(category);
        player2Scores.get(round - 1).setText(String.valueOf(player2Score));
    }

    public void reset() {
        for (int i = 0; i < player1Scores.size(); i++) {
            player1Scores.get(i).setText("");
            categories.get(i).setText("");
            player2Scores.get(i).setText("");
        }
    }

    public JButton getPlayAgainButton() {
        return playAgainButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("QuizKampen Resultat");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            int roundsPerGame = 6;
            ResultPanel resultPanel = new ResultPanel("Spelare 1", "Spelare 2", 6);

            frame.add(resultPanel);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            // Exempel på att uppdatera resultat
            resultPanel.updateRound(1, 10, 5, "Geografi");
            resultPanel.updateRound(2, 20, 15, "Historia");

        });
    }
}

