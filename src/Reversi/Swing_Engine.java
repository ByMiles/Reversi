package Reversi;

import Swing_GUI.Action_bar;
import Swing_GUI.Court;
import Swing_GUI.Frame;
import Swing_GUI.Menu_bar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Swing_Engine {
    private Frame frame;
    private Court court;
    private Menu_bar menu_bar;
    private Action_bar action_bar;
    private Rules rules;

    private boolean[] in_charge;
    private boolean[][] possible;
    private boolean[] possibles;
    private boolean[][][] round;
    private int x;

    private Color[] colors;

    public Swing_Engine(int width, int height) {
        setDefaultColors();
        menu_bar = new Menu_bar();
        action_bar = new Action_bar(colors);
        frame = new Frame(width, height, "Reversi", menu_bar, action_bar, colors);
        createActionListener();

        newGame();
    }

    private void newGame() {

        this.x = menu_bar.getX();
        int variation = menu_bar.getVariation();
        int beginns = menu_bar.getBeginns();

        this.rules = new Rules(this.x, variation, beginns);

        court = new Court(x, colors);
        createMouseAdapter();
        action_bar.callWinner(0);
        frame.addCourt(court);

        newRound();
    }

    private void newRound() {
        this.rules.newRound();
        rules.newRound();
        this.round = rules.getRound();
        this.possible = rules.getPossible();
        this.possibles = rules.getPossibles();
        this.in_charge = rules.getIn_charge();

        showCourt();
        actualizeAction_bar();

        if (rules.checkWin())
            gameOver();
    }

    private void showCourt() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                this.court.modifyCell(i, j, this.possible[i][j], this.round[i][j][0], this.round[i][j][1], this.in_charge[0]);
            }
        }
    }

    private void actualizeAction_bar() {
        if (rules.stats.getCurrent() == 1) {
            action_bar.getUndo_button().setVisible(true);
        }
        action_bar.setP1_text(String.valueOf(rules.stats.getP1Sum()));
        action_bar.setP2_text(String.valueOf(rules.stats.getP2Sum()));
        action_bar.inCharge(in_charge[0]);

        if (!possibles[0] && possibles[1])
            action_bar.getSkip_button().setVisible(true);
    }

    private void createMouseAdapter() {

        for (int row = 0; row < this.x; row++) {
            for (int col = 0; col < this.x; col++) {
                final int row_f = row;
                final int col_f = col;
                this.court.setMouseListener(row, col, new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (possible[row_f][col_f]) {
                            endRound(row_f, col_f);
                        } else {
                            court.wrongTurn(row_f, col_f);
                        }
                    }
                });
            }
        }
    }

    private void createActionListener() {
        action_bar.getSkip_button().addActionListener(e -> skipRound());
        action_bar.getUndo_button().addActionListener(e -> undoRound());
        this.menu_bar.start_pvp.addActionListener(e -> newGame());
        this.menu_bar.show_preview.addActionListener(e -> this.court.changePreview());

        this.menu_bar.bg_color.addActionListener(e -> {
            Color buffer = colors[0];
            colors[0] = JColorChooser.showDialog(null, "Hintergrundfarbe", colors[0]);
            if (colors[0] == null)
                colors[0] = buffer;
            else
                this.frame.change_colors(colors);
        });

        this.menu_bar.court_color.addActionListener(e -> {
            Color buffer = colors[1];
            colors[1] = JColorChooser.showDialog(null, "Spielfeldfarbe", colors[1]);
            if (colors[1] == null)
                colors[1] = buffer;
            else
                this.frame.change_colors(colors);
        });

        this.menu_bar.p1_color.addActionListener(e -> {
            Color buffer = colors[2];
            colors[0] = JColorChooser.showDialog(null, "Farbe Spieler 1", colors[2]);
            if (colors[2] == null)
                colors[2] = buffer;
            else
                this.frame.change_colors(colors);
        });

        this.menu_bar.p2_color.addActionListener(e -> {
            Color buffer = colors[3];
            colors[3] = JColorChooser.showDialog(null, "Farbe Spieler 2", colors[3]);
            if (colors[3] == null)
                colors[3] = buffer;
            else
                this.frame.change_colors(colors);
        });

        this.menu_bar.wrong_turn_color.addActionListener(e -> {
            Color buffer = colors[4];
            colors[4] = JColorChooser.showDialog(null, "Farbe für Fehler", colors[4]);
            if (colors[4] == null)
                colors[4] = buffer;
            else
                this.frame.change_colors(colors);
        });

        this.menu_bar.default_color.addActionListener(e -> setDefaultColors());
    }

    private void endRound(int row, int col) {
        this.rules.endRound(row, col);
        newRound();
    }

    private void skipRound() {
        this.rules.skipRound();
        action_bar.getSkip_button().setVisible(false);
        newRound();
    }

    private void undoRound() {
        if (!this.rules.undoRound()) {
            action_bar.getUndo_button().setVisible(false);
        }
        newRound();
    }

    private void gameOver() {
        action_bar.getSkip_button().setVisible(false);
        action_bar.getUndo_button().setVisible(false);
        action_bar.callWinner(rules.gameOver());
    }

    private void setDefaultColors() {
        Color bg_color = new Color(135, 206, 235);
        Color court_color = new Color(34, 139, 34);
        Color p1_color = Color.BLACK;
        Color p2_color = Color.WHITE;
        Color wrong_turn_color = Color.RED;

        colors = new Color[]{bg_color, court_color, p1_color, p2_color, wrong_turn_color};
    }
}
