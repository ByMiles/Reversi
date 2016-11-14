package Swing_GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

class Cell extends JPanel
{
    private Color[] colors;
    private boolean stats[];
    private boolean show_preview;

    Cell(Color[] colors)
    {
        this.colors = colors;
        this.setBackground(colors[1]);
        stats = new boolean[]{false, false, false, true};
        show_preview = true;
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);


        if(stats[0] && show_preview)
        {
            if(stats[3])
                g.setColor(colors[2]);
            else
                g.setColor(colors[3]);

            g.fillOval((int)(this.getWidth()*0.35), (int)(this.getHeight()*0.35),(int)(this.getWidth()*0.3),(int)(this.getHeight()*0.3));
        }
        if(stats[1])
        {
            g.setColor(colors[1].darker().darker().darker());
            g.fillOval((int)(this.getWidth()*0.15), (int)(this.getHeight()*0.15),(int)(this.getWidth()*0.7) + 5,(int)(this.getHeight()*0.7) + 5);

            g.setColor(colors[2]);
            g.fillOval((int)(this.getWidth()*0.15), (int)(this.getHeight()*0.15),(int)(this.getWidth()*0.7),(int)(this.getHeight()*0.7));
        }
        if(stats[2])
        {
            g.setColor(colors[1].darker().darker().darker());
            g.fillOval((int)(this.getWidth()*0.15), (int)(this.getHeight()*0.15),(int)(this.getWidth()*0.7) + 5,(int)(this.getHeight()*0.7) + 5);

            g.setColor(colors[3]);
            g.fillOval((int)(this.getWidth()*0.15), (int)(this.getHeight()*0.15),(int)(this.getWidth()*0.7),(int)(this.getHeight()*0.7));
        }

        repaint();

    }

    void setMouseListener(MouseListener mouseListener)
    {
        this.addMouseListener(mouseListener);
    }


    void change_colors(Color[] colors)
    {
        this.colors = colors;
        this.setBackground(colors[1]);
    }

    void setStats(boolean possible, boolean p1, boolean p2, boolean in_charge)
    {
        this.stats[0] = possible;
        this.stats[1] = p1;
        this.stats[2] = p2;
        this.stats[3] = in_charge;
    }

    void changePreview()
    {
        this.show_preview = !this.show_preview;
    }

    void wrongTurn()
    {
        this.setBackground(colors[4]);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                setBackground(colors[1]);
            }
        }, 500);
    }


}
