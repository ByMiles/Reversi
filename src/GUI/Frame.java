package GUI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;


public class Frame extends JFrame
{
    private ActionListener al;

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

    public Frame(int width, int height, ActionListener al, Menu_bar menu_bar, Color[] colors)
    {
        this.al = al;
        this.colors = colors;
        this.setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addMenuBar(menu_bar);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.getContentPane().setLayout(new BorderLayout());
        calculateSizes(this.getContentPane().getWidth(), this.getContentPane().getHeight());

        this.getContentPane().add(createTitlebar(), BorderLayout.PAGE_START);

        this.left_bar = createSidebar();
        this.getContentPane().add(left_bar, BorderLayout.LINE_START);

        this.right_bar = createSidebar();
        this.getContentPane().add(right_bar, BorderLayout.LINE_END);

        this.getContentPane().add(createActionbar(), BorderLayout.PAGE_END);

        this.getContentPane().add(createCourtedge(), BorderLayout.CENTER);

        this.getContentPane().revalidate();
    }

    private Title_bar createTitlebar()
    {
        title_bar = new Title_bar(this.width, this.side_height,this.colors, this.size_factor);

        return title_bar;
    }

    private Side_bar createSidebar()
    {
        return new Side_bar(this.side_width, this.height, this.colors);
    }

    private Action_bar createActionbar()
    {
        action_bar = new Action_bar(this.width, this.side_height, colors, this.size_factor, this.al);

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

    public void actualizeActionBar(int p1, int p2, boolean p1_)
    {
        action_bar.setP1_text(String.valueOf(p1));
        action_bar.setP2_text(String.valueOf(p2));
        action_bar.inCharge(p1_);
    }

    public JButton getSkipButton()
    {
        return action_bar.getSkip_button();
    }

    public JButton getUndoButton()
    {
        return action_bar.getUndo_button();
    }

    public void showWinner(int state)
    {
        this.action_bar.callWinner(state);
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

    void resizeIt(int width, int height)
    {
        calculateSizes(width, height);
        this.title_bar.resizeIt(this.width, this.side_height, this.size_factor);
        this.left_bar.resizeIt(this.side_width, this.height);
        this.right_bar.resizeIt(this.side_width, this.height);
        this.action_bar.resizeIt(this.width, this.side_height, this.size_factor);
    }
}
