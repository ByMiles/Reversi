package Game;

import GUI.*;
import GUI.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Engine {
    private Frame frame;
    private Court court;
    private Menu_bar menu_bar;
    private Stats stats;
    private boolean[] in_charge;
    private boolean[][] possible;
    private boolean[] possibles;
    private boolean[][][] round;
    private int x;

    private Color[] colors;

    public Engine(int width, int height) {
        setDefaultColors();
        menu_bar = new Menu_bar();
        frame = new Frame(width, height, menu_bar, colors);
        createActionListener();

        newGame();
    }

    private void newGame() {

        this.x = menu_bar.getX();
        this.round = new boolean[this.x][this.x][2];
        this.possibles = new boolean[]{false, true};

        switch (menu_bar.getVariation()) {

            case 2:
                round[x / 2 - 1][x / 2 - 1][0] = true;
                round[x / 2 - 1][x / 2][1] = true;
                round[x / 2][x / 2 - 1][1] = true;
                round[x / 2][x / 2][0] = true;
                break;
            case 3:
                round[x / 2 - 1][x / 2 - 1][1] = true;
                round[x / 2 - 1][x / 2][0] = true;
                round[x / 2][x / 2 - 1][1] = true;
                round[x / 2][x / 2][0] = true;
                break;
            default:
                round[x / 2 - 1][x / 2 - 1][1] = true;
                round[x / 2 - 1][x / 2][1] = true;
                round[x / 2][x / 2 - 1][0] = true;
                round[x / 2][x / 2][0] = true;
        }

        stats = new Stats(x, this.round);
        court = new Court(x, colors);
        createMouseAdapter();
        frame.showWinner(0);
        frame.addCourt(court);

        in_charge = new boolean[2];
        in_charge[menu_bar.getBeginns()] = true;

        nextRound();
    }

    private void nextRound() {
        this.round = stats.getCurrentRound();
        if (this.stats.getCurrent() == 1)
            this.frame.getUndoButton().setVisible(true);
        this.frame.actualizeActionBar(this.stats.getP1Sum(), this.stats.getP2Sum(), in_charge[0]);
        showPossible();
        showCourt();
        checkWin();
    }

    private void showCourt() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                this.court.modifyCell(i, j, this.possible[i][j], this.round[i][j][0], this.round[i][j][1], this.in_charge[0]);
            }
        }
    }

    private void createMouseAdapter() {

        for (int row = 0; row < this.x; row++) {
            for (int col = 0; col < this.x; col++) {
                final int row_f = row;
                final int col_f = col;
                this.court.setMouseListener(row, col, new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e)
                    {
                        if (possible[row_f][col_f])
                        {
                            endRound(row_f, col_f);
                        }
                        else
                        {
                            court.wrongTurn(row_f, col_f);
                        }
                    }
                });
            }
        }
    }

    private void createActionListener()
    {
        this.frame.getSkipButton().addActionListener(e -> skipRound());
        this.frame.getUndoButton().addActionListener(e -> undoRound());
        this.menu_bar.start_pvp.addActionListener(e -> newGame());
        this.menu_bar.show_preview.addActionListener(e -> this.court.changePreview());
        this.menu_bar.bg_color.addActionListener(e -> { Color buffer = colors[0];
            colors[0] = JColorChooser.showDialog(null, "Hintergrundfarbe", colors[0]);
            if (colors[0] == null)
                colors[0] = buffer;
            else
                this.frame.change_colors(colors);});
        this.menu_bar.court_color.addActionListener(e -> { Color buffer = colors[1];
            colors[1] = JColorChooser.showDialog(null, "Spielfeldfarbe", colors[1]);
            if (colors[1] == null)
                colors[1] = buffer;
            else
                this.frame.change_colors(colors);});
        this.menu_bar.p1_color.addActionListener(e -> { Color buffer = colors[2];
            colors[0] = JColorChooser.showDialog(null, "Farbe Spieler 1", colors[2]);
            if (colors[2] == null)
                colors[2] = buffer;
            else
                this.frame.change_colors(colors);});
        this.menu_bar.p2_color.addActionListener(e -> { Color buffer = colors[3];
            colors[3] = JColorChooser.showDialog(null, "Farbe Spieler 2", colors[3]);
            if (colors[3] == null)
                colors[3] = buffer;
            else
                this.frame.change_colors(colors);});
        this.menu_bar.wrong_turn_color.addActionListener(e -> { Color buffer = colors[4];
            colors[4] = JColorChooser.showDialog(null, "Farbe für Fehler", colors[4]);
            if (colors[4] == null)
                colors[4] = buffer;
            else
                this.frame.change_colors(colors);});
        this.menu_bar.default_color.addActionListener(e -> setDefaultColors());
    }

    private void endRound(int row, int col) {
        this.round[row][col][0] = this.in_charge[0];
        this.round[row][col][1] = this.in_charge[1];
        switchCoins(row, col);
        this.stats.addNextRound(this.round);
        switchPlayer();
        nextRound();
    }

    private void switchPlayer() {
        in_charge[0] = !in_charge[0];
        in_charge[1] = !in_charge[1];
    }

    private void skipRound() {
        this.stats.addNextRound(this.round);
        this.frame.getSkipButton().setVisible(false);
        switchPlayer();
        nextRound();
    }

    private void undoRound() {
        this.stats.undoRound();
        if (this.stats.getCurrent() == 0)
            this.frame.getUndoButton().setVisible(false);
        switchPlayer();
        nextRound();
    }

    private void checkWin() {
        if (this.stats.getEmptySum() == 0)
            gameOver();
    }

    private void showPossible() {
        possibles[0] = false;
        possible = new boolean[x][x];

        for (int row = 0; row < x; row++) {
            for (int col = 0; col < x; col++) {
                if (!this.round[row][col][0] && !this.round[row][col][1]) {

                    for (int row_change = -1; row_change <= 1; row_change++) {
                        for (int col_change = -1; col_change <= 1; col_change++) {
                            if (checkPossible(row, col, row_change, col_change))
                                possibles[0] = true;
                        }
                    }
                }
            }
        }
        if (possibles[0]) {
            possibles[1] = true;
        } else if (possibles[1]) {
            this.frame.getSkipButton().setVisible(true);
            possibles[1] = false;
        } else {
            gameOver();
        }
    }

    private void gameOver() {
        this.frame.getSkipButton().setVisible(false);
        this.frame.getUndoButton().setVisible(false);

        if (stats.getP1Sum() > stats.getP2Sum())
            this.frame.showWinner(1);
        else if (stats.getP1Sum() < stats.getP2Sum())
            this.frame.showWinner(2);
        else
            this.frame.showWinner(3);
    }

    private boolean checkPossible(int row, int col, int row_change, int col_change) {
        int new_row = row + row_change;
        int new_col = col + col_change;

        if (new_row != -1 && new_row != x && new_col != -1 && new_col != x) {

            if (round[new_row][new_col][0] != in_charge[0] && round[new_row][new_col][1] != in_charge[1]) {
                new_row += row_change;
                new_col += col_change;

                while (new_row != -1 && new_row != x && new_col != -1 && new_col != x) {
                    if (!round[new_row][new_col][0] && !round[new_row][new_col][1]) {
                        return false;
                    }
                    if (round[new_row][new_col][0] == in_charge[0] && round[new_row][new_col][1] == in_charge[1]) {
                        possible[row][col] = true;
                        return true;
                    }
                    new_row += row_change;
                    new_col += col_change;
                }
            }
        }
        return false;
    }

    private void switchCoins(int row, int col) {
        for (int row_change = -1; row_change <= 1; row_change++) {
            for (int col_change = -1; col_change <= 1; col_change++) {
                checkCoins(row, col, row_change, col_change);
            }
        }
    }

    private void checkCoins(int row, int col, int row_change, int col_change) {
        int new_row = row + row_change;
        int new_col = col + col_change;

        if (new_row != -1 && new_row != x && new_col != -1 && new_col != x) {

            int[][] switchies = new int[7][2];
            int switched = 0;

            if (round[new_row][new_col][0] != in_charge[0] && round[new_row][new_col][1] != in_charge[1]) {

                while (new_row != -1 && new_row != x && new_col != -1 && new_col != x) {
                    if (!round[new_row][new_col][0] && !round[new_row][new_col][1]) {
                        return;
                    }
                    if (round[new_row][new_col][0] != in_charge[0] && round[new_row][new_col][1] != in_charge[1]) {
                        switchies[switched][0] = new_row;
                        switchies[switched][1] = new_col;
                        switched++;
                    }
                    if (round[new_row][new_col][0] == in_charge[0] && round[new_row][new_col][1] == in_charge[1]) {
                        for (int k = 0; k < switched; k++) {
                            round[switchies[k][0]][switchies[k][1]][0] = !round[switchies[k][0]][switchies[k][1]][0];
                            round[switchies[k][0]][switchies[k][1]][1] = !round[switchies[k][0]][switchies[k][1]][1];
                        }
                        return;
                    }
                    new_row += row_change;
                    new_col += col_change;
                }
            }
        }
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
