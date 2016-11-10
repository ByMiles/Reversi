package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class Menu_bar extends JMenuBar
{
    private JMenu new_menu, pvp_menu, pvc_menu, option_menu;
    private Font font;
    private SO_MenuRadioButton n_6, n_7, n_8, n_9, n_10, v_1, v_2, v_3, s_1, s_2;
    public JMenuItem start_pvp, show_preview, bg_color, court_color, p1_color, p2_color, wrong_turn_color, default_color;

    private int x, variation, beginns;

    private ActionListener al, intern_al;
    public Menu_bar(ActionListener al)
    {
        super();
        this.al = al;
        createInternAl();
        font = new Font("San Serif", Font.PLAIN, 16);

        x = 8;
        variation = 1;
        beginns = 0;

        new_menu = createMenu("Neu");
        pvp_menu = createMenu("Spieler vs Spieler");
        pvc_menu = createMenu("Spieler vs Computer");
        option_menu = createMenu("Optionen");

        new_menu.add(pvp_menu);
        new_menu.add(pvc_menu);
        this.add(new_menu);
        this.add(option_menu);

        createCourt_x();
        createCourt_variation();
        createBeginner();
        createStartGame();

        createOptions();

    }

    private JMenu createMenu(String text)
    {
        JMenu menu = new JMenu(text);
        menu.setFont(font);
        return menu;
    }

    private void createCourt_x()
    {
        ButtonGroup court_x = new ButtonGroup();
        n_6 = createRadioButton("6 x 6", court_x, pvp_menu);
        n_7 = createRadioButton("7 x 7", court_x, pvp_menu);
        n_8 = createRadioButton("8 x 8", court_x, pvp_menu);
        n_8.setSelected(true);
        n_9 = createRadioButton("9 x 9", court_x, pvp_menu);
        n_10 = createRadioButton("10x10", court_x, pvp_menu);

        pvp_menu.addSeparator();
    }

    private void createCourt_variation()
    {
        ButtonGroup court_variation = new ButtonGroup();
        v_1 = createRadioButton("Variante_1", court_variation, pvp_menu);
        v_1.setSelected(true);
        v_2 = createRadioButton("Variante_2", court_variation, pvp_menu);
        v_3 = createRadioButton("Variante_3", court_variation, pvp_menu);

        pvp_menu.addSeparator();
    }

    private void createBeginner()
    {
        ButtonGroup beginner = new ButtonGroup();
        s_1 = createRadioButton("Spieler 1", beginner, pvp_menu);
        s_1.setSelected(true);
        s_2 = createRadioButton("Spieler 2", beginner, pvp_menu);

        pvp_menu.addSeparator();
    }

    private void createStartGame()
    {
        start_pvp = createMenuItem("Neues Spiel", pvp_menu);
    }

    private SO_MenuRadioButton createRadioButton(String text, ButtonGroup buttonGroup, JMenu menu)
    {
        SO_MenuRadioButton radioButtonMenuItem = new SO_MenuRadioButton(text);
        radioButtonMenuItem.setFont(font);
        radioButtonMenuItem.addActionListener(intern_al);

        buttonGroup.add(radioButtonMenuItem);
        menu.add(radioButtonMenuItem);
        return radioButtonMenuItem;
    }

    private void createOptions()
    {
        show_preview = createMenuItem("Vorschau anzeigen", option_menu);
        option_menu.addSeparator();
        bg_color = createMenuItem("Hintergrundfarbe", option_menu);
        court_color = createMenuItem("Feldfarbe", option_menu);
        p1_color = createMenuItem("Farbe Spieler 1", option_menu);
        p2_color = createMenuItem("Farbe Spieler 2", option_menu);
        wrong_turn_color = createMenuItem("Farbe Fehler", option_menu);
        default_color = createMenuItem("Farben zurücksetzen", option_menu);
    }

    private JMenuItem createMenuItem(String text, JMenu menu)
    {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setFont(font);
        menuItem.addActionListener(this.al);
        menu.add(menuItem);

        return menuItem;
    }

    public int getX()
    {
        return x;
    }
    public int getVariation()
    {
        return variation;
    }
    public int getBeginns()
    {
        return beginns;
    }

    private void createInternAl()
    {
        intern_al = e -> {
            if(e.getSource() == n_6)
            {
                x = 6;
            }
            if(e.getSource() == n_7)
            {
                x = 7;
            }
            if(e.getSource() == n_8)
            {
                x = 8;
            }
            if(e.getSource() == n_9)
            {
                x = 9;
            }
            if(e.getSource() == n_10)
            {
                x = 10;
            }
            if(e.getSource() == v_1)
            {
                variation = 1;
            }
            if(e.getSource() == v_2)
            {
                variation = 2;
            }
            if(e.getSource() == v_3)
            {
                variation = 3;
            }
            if(e.getSource() == s_1)
            {
                beginns = 0;
            }
            if(e.getSource() == s_2)
            {
                beginns = 1;
            }

        };
    }
}
