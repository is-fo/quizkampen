package panels;
import pojos.Connected;
import pojos.GameState;
import pojos.Score;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JPanel resultPanel;
    private JFrame resultFrame;
    JButton playButton;

    private int roundsPerGame;
    private ObjectOutputStream oos;

    public ResultPanel(GameState gameState, ObjectOutputStream oos, int roundsPerGame) {
        this.gameState = gameState;
        this.cardLayout = new CardLayout();
        this.roundsPerGame = roundsPerGame;
        this.oos = oos;
        this.panel = new JPanel(new BorderLayout());
        this.resultPanel = new JPanel(new GridLayout(roundsPerGame, 3));

    }

    public void createWindow() {
        createResultFrame();

        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Spelare1", SwingConstants.CENTER));
        titlePanel.add(new JLabel("0 - 0", SwingConstants.CENTER));
        titlePanel.add(new JLabel("Spelare2", SwingConstants.CENTER));
        panel.add(titlePanel, BorderLayout.NORTH);


        for (int i = 0; i < roundsPerGame; i++) {

            player1Scores.add(new JLabel(" ", SwingConstants.CENTER));
            categories.add(new JLabel(" ", SwingConstants.CENTER));
            player2Scores.add(new JLabel(" ", SwingConstants.CENTER));

            resultPanel.add(player1Scores.get(i));
            resultPanel.add(categories.get(i));
            resultPanel.add(player2Scores.get(i));
        }
        panel.add(resultPanel, BorderLayout.CENTER);

        createButton();

        panel.setVisible(true);
        resultFrame.add(panel);
    }

    public void createButton() {

        playButton = new JButton("Spela");
        playButton.setEnabled(false);
        panel.add(playButton, BorderLayout.SOUTH);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Spela klickad");
                try {
                    oos.writeObject(new Connected());
                    oos.flush();
                    hideFrame();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Fel vid kommunikation med servern.");
                }
            }
        });
    }

    public void updateWindow (List<Score> scores) {
        for (int i = 0; i < scores.get(0).getScores().size(); i++) { //spelare 1
            player1Scores.get(i).setText(String.valueOf(scores.get(0).getScores().get(i)));
        }
        for (int i = 0; i < scores.get(1).getScores().size(); i++) { //spelare 2
            player2Scores.get(i).setText(String.valueOf(scores.get(1).getScores().get(i)));
        }

        resultFrame.repaint();
        resultFrame.revalidate();
        showFrame();
    }


    private void createResultFrame() {
        resultFrame = new JFrame("Lobby");
        resultFrame.setSize(800, 700);
        resultFrame.setLocationRelativeTo(null);
        resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resultFrame.setVisible(true);

    }
    private void hideFrame() {
        resultFrame.setVisible(false);
    }

    private void showFrame() {
        resultFrame.setVisible(true);
    }

    public static void main(String[] args) {
        ResultPanel rp = new ResultPanel(null, null, 6);
        rp.createWindow();
    }

    public void disablePlayButton() {
        playButton.setEnabled(false);
        resultFrame.revalidate();
        resultFrame.repaint();
    }
    public void enablePlayButton() {
        playButton.setEnabled(true);
        resultFrame.revalidate();
        resultFrame.repaint();
    }

}