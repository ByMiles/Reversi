package Swing_GUI;

import javax.swing.*;
import java.awt.*;


public class Action_bar extends JPanel
{
    private Color[] colors;
    private Font font;

    private Coin_panel p1_coin, p2_coin;
    private JButton skip_button, undo_button;

    private boolean p1;

    private JLabel winner_label;

    private JPanel spaces[];

    public Action_bar(Color[] colors)
    {
        this.setLayout(new GridLayout(1, 6));
        this.setBackground(colors[0]);
        this.colors = colors;

        this.spaces = new JPanel[9];

        for (int i = 0; i < spaces.length; i++)
        {
            spaces[i] = new JPanel();
            spaces[i].setBackground(colors[0]);
            this.add(spaces[i]);
        }

        p1_coin = new Coin_panel(colors, true);
        spaces[2].add(p1_coin);

        p2_coin = new Coin_panel(colors, false);
        spaces[6].add(p2_coin);

        skip_button = new JButton("Skip");
        skip_button.setFont(font);
        spaces[3].add(skip_button);
        skip_button.setVisible(false);

        undo_button = new JButton("Undo");
        undo_button.setFont(font);
        spaces[5].add(undo_button);
        undo_button.setVisible(false);

        winner_label = new JLabel();
        winner_label.setFont(font);
        winner_label.setHorizontalTextPosition(JLabel.CENTER);
        winner_label.setHorizontalAlignment(JLabel.CENTER);
        winner_label.setHorizontalTextPosition(JLabel.CENTER);
        winner_label.setVerticalAlignment(JLabel.CENTER);
        spaces[4].add(winner_label);
    }
    public JButton getSkip_button()
    {
        return skip_button;
    }

    public JButton getUndo_button()
    {
        return undo_button;
    }

    public void setP1_text(String text)
    {
    p1_coin.setText(text);
    }

    public void setP2_text(String text)
    {
        p2_coin.setText(text);
    }

    public void callWinner(int state)
    {
        switch(state)
        {
            case 1:
                winner_label.setText("<< Sieg");
                winner_label.setForeground(colors[2]);
                break;
            case 2:
                winner_label.setText(" Sieg >>");
                winner_label.setForeground(colors[3]);
                break;
            case 3:
                winner_label.setText("unentschieden");
                winner_label.setForeground(Color.cyan);
                break;
            default: winner_label.setText("");
        }
    }

    public void inCharge(boolean p1)
    {
        this.p1 = p1;
        if(p1) {
            winner_label.setText("Spieler 1");
            winner_label.setForeground(colors[2]);
        }
        else
        {
            winner_label.setText("Spieler 2");
            winner_label.setForeground(colors[3]);
        }
    }

    void change_colors(Color[] colors)
    {
        this.colors = colors;

        this.setBackground(colors[0]);
        for (JPanel space : spaces) {
            space.setBackground(colors[0]);
        }

        if(p1)
            winner_label.setForeground(colors[2]);
        else
            winner_label.setForeground(colors[3]);
        p1_coin.change_Colors(colors);
        p2_coin.change_Colors(colors);
    }

    void resizeIt(int width, int height, double size_factor)
    {
        this.setPreferredSize(new Dimension(width, height));
        this.font = new Font("San Serif", Font.BOLD, (int)(20*size_factor));
        this.winner_label.setFont(this.font);
        this.undo_button.setFont(this.font);
        this.skip_button.setFont(this.font);
        int size;
        if(height < width/7)
            size = (int)((double)height * 0.8);
        else
            size = (int)((double)width/7. * 0.8);

        int border = (int)((double) height * 0.1);

        for (JPanel s:spaces) {
            s.setBorder(BorderFactory.createEmptyBorder(border, border, border, border));
        }


        this.p1_coin.resizeIt(size, size_factor);
        this.p2_coin.resizeIt(size, size_factor);



    }
}
