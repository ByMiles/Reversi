package Swing_GUI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;


public class Frame extends JFrame
{
    private int width;
    private int height;
    private int side_width;
    private int side_height;
    private double size_factor;

    private Color[] colors;

    private Title_bar title_bar;
    private Side_bar left_bar, right_bar;
    private Action_bar action_bar;
    private Court court;
    private JPanel court_edge;

    public Frame(int width, int height, String title, Menu_bar menu_bar, Action_bar action_bar, Color[] colors)
    {
        this.colors = colors;
        this.setSize(width, height);
        title += " by Miles ;-)";
        this.setTitle(title);
        this.addMenuBar(menu_bar);
        this.setLocationRelativeTo(null);


        this.getContentPane().setLayout(new BorderLayout());
        calculateSizes(this.getContentPane().getWidth(), this.getContentPane().getHeight());

        this.getContentPane().add(createTitlebar(title), BorderLayout.PAGE_START);

        this.left_bar = createSidebar();
        this.getContentPane().add(left_bar, BorderLayout.LINE_START);

        this.right_bar = createSidebar();
        this.getContentPane().add(right_bar, BorderLayout.LINE_END);

        this.getContentPane().add(addActionbar(action_bar), BorderLayout.PAGE_END);

        this.getContentPane().add(createCourtedge(), BorderLayout.CENTER);

        this.getContentPane().revalidate();
        this.setVisible(true);

        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeIt();
            }
            @Override
            public void componentMoved(ComponentEvent e) {}
            @Override
            public void componentShown(ComponentEvent e) {}
            @Override
            public void componentHidden(ComponentEvent e) {}
        });
    }

    private Title_bar createTitlebar(String title)
    {
        title_bar = new Title_bar(this.width, this.side_height,this.colors, this.size_factor, title);

        return title_bar;
    }

    private Side_bar createSidebar()
    {
        return new Side_bar(this.side_width, this.height, this.colors);
    }

    private Action_bar addActionbar(Action_bar action_bar)
    {
        this.action_bar = action_bar;
        this.action_bar.resizeIt(this.width, this.side_height, this.size_factor);

        return action_bar;
    }

    private JPanel createCourtedge()
    {
        court_edge = new JPanel(new GridLayout(1, 1));

        Color color[] = new Color[4];
        color[0] = new Color(200, 200, 200);
        color[1] = new Color(230, 230, 230);
        color[2] = new Color(130, 130, 130);
        color[3] = new Color(100, 100, 100);

        JPanel edgies[] = new JPanel[10];
        for (int i = 0; i < edgies.length; i++)
        {
            edgies[i] = new JPanel(new GridLayout(1,1));
            edgies[i].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, color[0], color[1], color[2], color[3]));
            if(i != 0)
                edgies[i-1].add(edgies[i]);
        }
        edgies[edgies.length-1].add(court_edge);
        return edgies[0];
    }

    public void addCourt(Court court)
    {
        try
        {
            court_edge.remove(this.court);
        }
        catch (Exception ignored){}

        this.court = court;
        court_edge.add(this.court);
        this.court_edge.revalidate();
    }

    private void addMenuBar(JMenuBar menu)
    {
        this.setJMenuBar(menu);
    }

    public void change_colors(Color[] colors)
    {
        this.colors = colors;
        this.title_bar.change_colors(colors);
        this.left_bar.change_colors(colors);
        this.right_bar.change_colors(colors);
        this.action_bar.change_colors(colors);
        this.court.change_colors(colors);
    }

    private void calculateSizes(int width, int height)
    {
        this.width = width;
        this.height = height;

        int court_size;
        if(this.height >= this.width)
        {
            this.size_factor = (double)this.width / 800.;
            court_size = (int)((double)this.width * 0.8);
        }
        else
        {
            this.size_factor = (double)this.height / 800.;
            court_size = (int)((double)this.height * 0.8);
        }

        side_width = (this.width - court_size)/2;
        side_height = (this.height - court_size)/2;
    }

    private void resizeIt()
    {
        calculateSizes(this.getContentPane().getWidth(), this.getContentPane().getHeight());
        this.title_bar.resizeIt(this.width, this.side_height, this.size_factor);
        this.left_bar.resizeIt(this.side_width, this.height);
        this.right_bar.resizeIt(this.side_width, this.height);
        this.action_bar.resizeIt(this.width, this.side_height, this.size_factor);
    }
}
