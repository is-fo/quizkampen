package Panels;

import javax.swing.*;
import java.awt.*;

abstract public class MasterPanel extends JFrame implements IPanel{

    protected JLabel logoLabel = new JLabel("QuizKampen");
    protected Color logoColor = new Color(118, 0, 150);
    protected Font logoFont = new Font("Arial Black", 2, 80);
    protected Font buttonFont = new Font("Arial Black", Font.BOLD, 20);
    protected Color backgroundColor = new Color(0, 150, 85);
    protected Color infoTextColor = new Color(253, 253, 253);
    protected Color nuance = new Color(0, 150, 85);

    public MasterPanel() {
        setSize(800, 800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logoLabel.setFont(new Font("Arial Black", Font.BOLD, 16));
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        logoLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
        add(logoLabel, BorderLayout.NORTH);
    }


    @Override
    public void setCustomColor(Color backgroundColor, Color logoColor, Color infoTextColor, Color nuance) {
        this.backgroundColor = backgroundColor;
        this.logoColor = logoColor;
        this.infoTextColor = infoTextColor;
        this.nuance = nuance;
        removeAll();
        setPanel();
        revalidate();
        repaint();
    }

    @Override
    public abstract void setPanel();
}
