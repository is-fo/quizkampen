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

import static javax.swing.SwingConstants.CENTER;

public class ResultPanel {

    JButton playButton;
    int player = -1;
    private final List<JLabel> player1Scores = new ArrayList<>();
    private final List<JLabel> categories = new ArrayList<>();
    private final List<JLabel> player2Scores = new ArrayList<>();
    private final JLabel scoreKeeping = new JLabel("0 - 0", CENTER);
    private final JPanel panel;
    private final JPanel resultPanel;
    private JFrame resultFrame;
    private final int roundsPerGame;
    private final ObjectOutputStream oos;

    public ResultPanel(GameState gameState, ObjectOutputStream oos, int roundsPerGame) {
        this.roundsPerGame = roundsPerGame;
        this.oos = oos;
        this.panel = new JPanel(new BorderLayout());
        this.resultPanel = new JPanel(new GridLayout(roundsPerGame, 3));

    }

    public void createWindow(int player) {
        this.player = player;
        createResultFrame();

        JPanel titlePanel = new JPanel();
        ImageIcon image1 = new ImageIcon(("src/images/" + (player) + ".png"));
        ImageIcon image2 = new ImageIcon("src/images/" + ((player + 1) % 2) + ".png");
        titlePanel.add(new JLabel(image1, CENTER));
        titlePanel.add(scoreKeeping);
        titlePanel.add(new JLabel(image2, CENTER));
        panel.add(titlePanel, BorderLayout.NORTH);


        for (int i = 0; i < roundsPerGame; i++) {

            player1Scores.add(new JLabel(" ", CENTER));
            categories.add(new JLabel(" ", CENTER));
            player2Scores.add(new JLabel(" ", CENTER));

            resultPanel.add(player1Scores.get(i));
            resultPanel.add(categories.get(i));
            resultPanel.add(player2Scores.get(i));
        }
        panel.add(resultPanel, BorderLayout.CENTER);

        createButton();

        panel.setVisible(true);
        resultFrame.add(panel);
        resultFrame.repaint();
        resultFrame.revalidate();
    }

    public void createButton() {

        playButton = new JButton("Spela");
        playButton.setEnabled(true);
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
                    System.err.println("Fel vid kommunikation med servern: " + ex.getCause());
                }
            }
        });
    }

    public void blablabla() {
        for (int i = 0; i < roundsPerGame; i++) {
            if (player1Scores.get(i).getText().equals(" ")) {
                player1Scores.get(i).setText("?");
                return;
            } else if (player2Scores.get(i).getText().equals(" ")) {
                player2Scores.get(i).setText("?");
                return;
            }
        }

        System.out.println("failfailfail");
    }

    public void updateWindow(List<Score> scores, List<String> categories) {
        int left = player;
        int right = (player + 1) % 2;
        int p1score = 0;
        for (int i = 0; i < scores.get(left).getScores().size(); i++) { //spelare 1
            player1Scores.get(i).setText(String.valueOf(scores.get(left).getScores().get(i)));
            p1score += scores.get(left).getScores().get(i);
        }
        int p2score = 0;
        for (int i = 0; i < scores.get(right).getScores().size(); i++) { //spelare 2
            player2Scores.get(i).setText(String.valueOf(scores.get(right).getScores().get(i)));
            p2score += scores.get(right).getScores().get(i);
        }
        for (int i = 0; i < categories.size(); i++) {
            this.categories.get(i).setIcon(new ImageIcon("src/images/"  + categories.get(i) + ".png"));
        }
        scoreKeeping.setText(p1score + " - " + p2score);

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