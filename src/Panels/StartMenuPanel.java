package Panels;

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

        startMenuPanel.setLayout(new GridLayout(5, 1, 0, 7));
        startMenuPanel.setBackground(backgroundColor);
        newGameButton.setFont(buttonFont);
        startMenuPanel.add(newGameButton);
        add(logoLabel, BorderLayout.NORTH);

    }

    @Override
    public void setActionListener(ActionListener actionListener) {
        newGameButton.addActionListener(actionListener);
    }
}
