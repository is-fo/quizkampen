package panels;
/*
import pojos.GameState;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

//TODO Koppla GameState till ResultPanel
public class ResultPanel extends JPanel {
    private static final int TOTAL_ROUNDS = 6; //TODO värd att ha? Se updateFinalResults()
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
        playAgainButton.addActionListener(e -> resetGame());//Fixas eller tänker jag helt fel???
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

    /*public void reset() {
        for (int i = 0; i < player1Scores.size(); i++) {
            player1Scores.get(i).setText("");
            categories.get(i).setText("");
            player2Scores.get(i).setText("");
        }
    } Tog bort denna och lägger till updateFinalResults och resetGame??
*/
/*
    public void updateFinalResults() {
        for (int round = 0; round < TOTAL_ROUNDS; round++) {
            updateRound(round + 1,
                    gameState.getScore(0, round),
                    gameState.getScore(1, round),
                    gameState.getCategoryForRound(round));////Fixas eller tänker jag helt fel???
        }
    }

    public void resetGame() {
        for (int i = 0; i < TOTAL_ROUNDS; i++) {
            player1Scores.get(i).setText("");
            categories.get(i).setText("");
            player2Scores.get(i).setText("");
        }
        gameState.resetGame();//Fixas eller tänker jag helt fel???
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, "categoryPanel");
    }

   /* public void updateFinalResults(int roundsPlayed, int[] player1ScoresArray, int[] player2ScoresArray, String[] categoriesArray) {
        for (int round = 0; round < roundsPlayed; round++) {
            updateRound(round + 1, player1ScoresArray[round], player2ScoresArray[round], categoriesArray[round]);
        }
    }*/
/*
   public JButton getPlayAgainButton() {
       return playAgainButton; //Få in den när speler är slut?
   }

    public void switchToResultPanel() {
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, "resultPanel");
    }
}

        private void showResultPanel(String player1Name, String player2Name, int roundsPerGame, GameState gameState){

            JFrame rp = new JFrame("QuizKampen Lobby");
            rp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel(new CardLayout());
            ResultPanel resultPanel = new ResultPanel(player1Name, player2Name, roundsPerGame, gameState, mainPanel);

            mainPanel.add(resultPanel, "Lobby");
            rp.add(mainPanel);
            rp.setSize(800, 600);
            rp.setLocationRelativeTo(null);
            rp.setVisible(true);

            return rp;
        }


        public static void main(String[] args) {
        showResultPanel("Spelare 1", "Spelare 2");
    }
}

*/
