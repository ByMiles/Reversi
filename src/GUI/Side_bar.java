package GUI;

import javax.swing.*;
import java.awt.*;


class Side_bar extends JPanel
{
    Side_bar(int width, int height, Color[] colors)
    {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(colors[0]);
    }

    void resizeIt(int width, int height)
    {
        this.setPreferredSize(new Dimension(width, height));
    }

    void change_colors(Color[] colors)
    {
        this.setBackground(colors[0]);
    }
}
