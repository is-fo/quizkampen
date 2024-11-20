package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameMenuPanel extends MasterPanel {

    private final JPanel gameMenuPanel = new JPanel();

    public JButton randomPlayerButton = new JButton("Okänd spelare");
    public JButton searchButton = new JButton("Leta efter spelare");


    @Override
    public void setPanel() {
        setLayout(new BorderLayout());
        setBackground(backgroundColor);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setPreferredSize(new Dimension(0, 200));
        logoLabel.setForeground(logoColor);
        logoLabel.setFont(logoFont);

        gameMenuPanel.setLayout(new GridLayout(5, 1, 0, 7));
        gameMenuPanel.setBackground(backgroundColor);
        randomPlayerButton.setFont(buttonFont);
        searchButton.setFont(buttonFont);
        gameMenuPanel.add(randomPlayerButton);
        gameMenuPanel.add(searchButton);


        add(logoLabel, BorderLayout.NORTH);
        add(gameMenuPanel, BorderLayout.CENTER);

    }

    @Override
    public void setActionListener(ActionListener actionListener) {
        randomPlayerButton.addActionListener(actionListener);
        searchButton.addActionListener(actionListener);
    }
}

