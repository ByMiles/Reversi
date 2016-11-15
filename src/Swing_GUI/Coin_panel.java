package Swing_GUI;

import javax.swing.*;
import java.awt.*;


class Coin_panel extends JPanel
{
    private Color[] colors;
    private boolean p1;
    private JLabel label;
    private int size;
    Coin_panel(Color[] colors, boolean p1)
    {
        this.colors = colors;
        this.p1 = p1;
        this.setBackground(colors[0]);

        label = new JLabel();

        if(p1)
            label.setForeground(colors[3]);
        else
            label.setForeground(colors[2]);

        label.setVerticalAlignment(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);
        this.add(label);

    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if(p1)
            g.setColor(colors[2]);
        else
            g.setColor(colors[3]);

        g.fillOval((int)((double)size * 0.1),(int)((double)size * 0.1), (int)((double)size * 0.8), (int)((double)size * 0.8));
        repaint();
    }

    void setText(String text)
    {
        this.label.setText(text);
    }

    void change_Colors(Color[] colors)
    {
        this.colors = colors;
        this.setBackground(colors[0]);
    }

    void resizeIt(int size, double size_factor)
    {
        this.size = size;
        this.setPreferredSize(new Dimension(size, size));
        label.setPreferredSize(new Dimension((int)((double)size * 0.8), (int)((double)size * 0.8)));
        this.label.setFont(new Font("San Serif", Font.BOLD, (int)(32*size_factor)));
    }
}
