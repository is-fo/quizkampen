package panels;
import pojos.GameState;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

//TODO Koppla GameState till ResultPanel

public class ResultPanel{
    private List<JLabel> player1Scores = new ArrayList<>();
    private List<JLabel> categories = new ArrayList<>();
    private List<JLabel> player2Scores = new ArrayList<>();
    private GameState gameState;
    private CardLayout cardLayout;
    private JPanel panel;
    private JPanel cardPanel;
    private JFrame resultFrame;
    private int currentRound;
    private int roundsPerGame;
    private ObjectOutputStream oos;


    public ResultPanel(GameState gameState, ObjectOutputStream oos) {
        this.gameState = gameState;
        this.cardPanel = new JPanel();
        this.cardLayout = new CardLayout();
        this.oos = oos;
        cardPanel.setLayout(cardLayout);

    }

    public void drawResult() {
        createResultFrame();
        panel = new JPanel(new BorderLayout(10, 10));
        JPanel resultPanel = new JPanel(new GridLayout(roundsPerGame + 1, 3, 5, 5));
        resultPanel.add(new JLabel("player1", SwingConstants.CENTER));
        resultPanel.add(new JLabel("Kategori", SwingConstants.CENTER));
        resultPanel.add(new JLabel("player2", SwingConstants.CENTER));

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

        for (int i = 0; i < categories.size(); i++) {
            player1Scores.get(i).setText(String.valueOf(gameState.getPlayerScores().get(0).getScoreForRound(i)));
            categories.get(i).setText(String.valueOf(gameState.getCategory(i)));
            player2Scores.get(i).setText(String.valueOf(gameState.getPlayerScores().get(1).getScoreForRound(i)));
        }
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

    public void updateResults() {
        player1Scores.add(new JLabel(String.valueOf(gameState.getScore(0)), SwingConstants.CENTER));
        player2Scores.add(new JLabel(String.valueOf(gameState.getScore(1)), SwingConstants.CENTER));
        categories.add(new JLabel(String.valueOf(gameState.getCategories())));
    }



    public void switchToResultPanel() {
        CardLayout cardLayout = (CardLayout) panel.getLayout();
        cardLayout.show(panel, "resultPanel");
    }


    private void createResultFrame() {

        resultFrame = new JFrame("Lobby");
        resultFrame.setSize(800, 600);
        resultFrame.setLocationRelativeTo(null);
        resultFrame.setVisible(true);

    }
    private void closeresultFrame() {
        resultFrame.dispose();
    }


}
