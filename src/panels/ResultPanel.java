package panels;

import server.GameState;

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
    private GameState gameState;
    private JPanel mainPanel;

    public ResultPanel(String player1Name, String player2Name, int roundsPerGame, GameState gameState, JPanel mainPanel) {
        this.gameState = gameState;
        this.mainPanel = mainPanel;
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

    public void updateFinalResults(int roundsPlayed, int[] player1ScoresArray, int[] player2ScoresArray, String[] categoriesArray) {
        for (int round = 0; round < roundsPlayed; round++) {
            updateRound(round + 1, player1ScoresArray[round], player2ScoresArray[round], categoriesArray[round]);
        }
    }

    private void switchToNextScreen() {
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        if (gameState.getCurrentRound() >= 6) {
            int[] player1ScoresArray = new int[6];
            int[] player2ScoresArray = new int[6];
            String[] categoriesArray = new String[6];

            for (int round = 0; round < 6; round++) {
                player1ScoresArray[round] = gameState.getScore(0);
                player2ScoresArray[round] = gameState.getScore(1);
                //categoriesArray[round] = getCategoryForRound(round);
                gameState.incrementRound();
            }
            updateFinalResults(6, player1ScoresArray, player2ScoresArray, categoriesArray);
            cardLayout.show(mainPanel, "resultPanel");
        } else if (gameState.getCurrentRound() % 2 == 0) {
            cardLayout.show(mainPanel, "categoryPanel");
        } else {
            cardLayout.show(mainPanel, "questionPanel");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("QuizKampen Resultat");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            int roundsPerGame = 6;
            GameState gameState = new GameState(6, 6);
            JPanel mainPanel = new JPanel(new CardLayout());
            ResultPanel resultPanel = new ResultPanel("Spelare 1", "Spelare 2", roundsPerGame, gameState, mainPanel);

            mainPanel.add(resultPanel, "resultPanel");
            frame.add(mainPanel);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

