package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ResultPanel extends JFrame{
    private JLabel[] player1Scores;
    private JLabel[] categories;
    private JLabel[] player2Scores;
    private JButton playAgainButton;


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

        playAgainButton = new JButton("Spela igen");

        add(resultPanel, BorderLayout.CENTER);
        add(playAgainButton, BorderLayout.SOUTH);

    }
    public void updateRound(int round, int player1Score, int player2Score, String category) {
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

    public JButton getPlayAgainButton() {
        return playAgainButton;
    }

    public static void main(String[] args) {
        ResultPanel rp = new ResultPanel();
        JFrame frame = new JFrame("QuizKampen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);

    }
}

