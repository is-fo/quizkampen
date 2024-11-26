package panels;
import pojos.Connected;
import pojos.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ResultPanel {

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

    public ResultPanel(GameState gameState, ObjectOutputStream oos, int roundsPerGame) {
        this.gameState = gameState;
        this.cardPanel = new JPanel();
        this.cardLayout = new CardLayout();
        this.roundsPerGame = roundsPerGame;
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
            JLabel player1ScoreLabel = new JLabel("sp1", SwingConstants.CENTER);
            JLabel categoryLabel = new JLabel("kat", SwingConstants.CENTER);
            JLabel player2ScoreLabel = new JLabel("sp2", SwingConstants.CENTER);

            player1Scores.add(player1ScoreLabel);
            categories.add(categoryLabel);
            player2Scores.add(player2ScoreLabel);

            resultPanel.add(player1ScoreLabel);
            resultPanel.add(categoryLabel);
            resultPanel.add(player2ScoreLabel);

        }

        for (int i = 0; i < roundsPerGame; i++) {
            player1Scores.get(i).setText(String.valueOf(gameState.getPlayerScores().get(0).getScoreForRound(i)));
            categories.get(i).setText(String.valueOf(gameState.getCategory(i)));
            player2Scores.get(i).setText(String.valueOf(gameState.getPlayerScores().get(1).getScoreForRound(i)));
        }

        resultFrame.add(panel, BorderLayout.CENTER);
        panel.add(resultPanel, BorderLayout.CENTER);
        JButton playButton = new JButton("Spela");
        panel.add(playButton, BorderLayout.SOUTH);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Spela klickad");
                try {
                    oos.writeObject(new Connected());
                    oos.flush();
                    closeresultFrame();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Fel vid kommunikation med servern.");
                }
            }
        });
        resultFrame.revalidate();
        resultFrame.repaint();
    }

    public void updateRound(int round, int player1Score, int player2Score, String category) {
        if (round < 1 || round > player1Scores.size()) {
            throw new IllegalArgumentException("Omgången måste vara mellan 1 och 6.");
        }
        player1Scores.get(round - 1).setText(String.valueOf(player1Score));
        categories.get(round - 1).setText(category);
        player2Scores.get(round - 1).setText(String.valueOf(player2Score));
    }

    public void updateResults() {
        player1Scores.add(new JLabel(String.valueOf(gameState.getScore(0)), SwingConstants.CENTER));
        player2Scores.add(new JLabel(String.valueOf(gameState.getScore(1)), SwingConstants.CENTER));
        categories.add(new JLabel(String.valueOf(gameState.getCategories())));
    }

    public void switchToResultPanel() {
        cardLayout.show(cardPanel, "resultPanel");
    }

    private void createResultFrame() {

        resultFrame = new JFrame("Lobby");
        resultFrame.setSize(800, 600);
        resultFrame.setLocationRelativeTo(null);
        resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resultFrame.setVisible(true);

    }
    private void closeresultFrame() {
        resultFrame.dispose();
    }

}
