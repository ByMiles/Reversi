package Swing_GUI;


import javax.swing.*;
import java.awt.*;

class Title_bar extends JPanel
{
    private JLabel title_label;
    private double size_factor;
    private Font font;

    Title_bar(int width, int height, Color[] colors, double size_factor)
    {
        this.size_factor = size_factor;

        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(colors[0]);

        this.add(createTilteLabel());
    }

    private JLabel createTilteLabel()
    {
        title_label = new JLabel("R e v e r s i");
        font = new Font("Serif", Font.BOLD, (int)(48. * size_factor) );
        title_label.setFont(font);

        title_label.setHorizontalAlignment(JLabel.CENTER);
        title_label.setVerticalAlignment(JLabel.CENTER);

        return title_label;
    }

    void change_colors(Color[] colors)
    {
        this.setBackground(colors[0]);

    }

    void resizeIt(int width, int height, double size_factor)
    {
        this.setPreferredSize(new Dimension(width, height));
        font = new Font("Serif", Font.BOLD, (int)(48. * size_factor) );
        title_label.setFont(font);
    }
}
