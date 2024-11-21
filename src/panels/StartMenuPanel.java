package panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartMenuPanel extends MasterPanel {

    private JPanel startMenuPanel = new JPanel();
    private JButton newGameButton = new JButton("Nytt spel");
    private JLabel userNameLabel = new JLabel(" ");

    @Override
    public void setPanel() {
        setLayout(new BorderLayout());

        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setPreferredSize(new Dimension(0, 200));
        add(logoLabel, BorderLayout.NORTH);

        startMenuPanel.setLayout(new GridLayout(5, 1, 0, 7));
        startMenuPanel.setBackground(backgroundColor);
        add(startMenuPanel, BorderLayout.CENTER);

        newGameButton.setFont(buttonFont);
        add(newGameButton, BorderLayout.SOUTH);
    }

    @Override
    public void setActionListener(ActionListener actionListener) {
        newGameButton.addActionListener(actionListener);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("QuizKampen");
        StartMenuPanel startMenuPanel = new StartMenuPanel();
        startMenuPanel.setPanel();

        frame.add(startMenuPanel);
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
