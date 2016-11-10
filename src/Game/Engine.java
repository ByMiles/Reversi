package Game;

import GUI.*;
import GUI.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
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

    private MouseAdapter ma;
    private ActionListener al;

    public Engine(int width, int height) {
        createActionListener(this);
        setDefaultColors();
        menu_bar = new Menu_bar(this.al);
        frame = new Frame(width, height, al, menu_bar, colors);

        createMouseAdapter(this);

        newGame(8, 0, 0);
        new Thread_Resizer(this.frame).run();
    }

    private void newGame(int x, int variation, int beginner) {
        this.x = x;
        this.round = new boolean[this.x][this.x][2];
        this.possibles = new boolean[]{false, true};

        switch (variation) {

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
        court = new Court(x, ma, colors);
        frame.showWinner(0);
        frame.addCourt(court);

        in_charge = new boolean[2];
        in_charge[beginner] = true;

        nextRound();
    }

    private void nextRound() {
        this.round = stats.getCurrentRound();
        if(this.stats.getCurrent() == 1)
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

    private void createMouseAdapter(Engine engine) {
        ma = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                for (int row = 0; row < engine.x; row++) {
                    for (int col = 0; col < engine.x; col++) {
                        if (e.getSource() == engine.court.getCell(row, col)) {
                            if (possible[row][col]) {
                                endRound(row, col);
                            } else {
                                engine.court.wrongTurn(row, col);
                            }
                        }
                    }
                }
            }
        };
    }

    private void createActionListener(Engine engine)
    {
        al = e -> {
            if(e.getSource() == engine.frame.getSkipButton())
                skipRound();
            if(e.getSource() == engine.frame.getUndoButton())
                undoRound();
            if(e.getSource() == engine.menu_bar.start_pvp)
                newGame(menu_bar.getX(), menu_bar.getVariation(), menu_bar.getBeginns());
            if(e.getSource() == engine.menu_bar.show_preview)
                this.court.changePreview();
            if(e.getSource() == engine.menu_bar.bg_color)
            {
                Color buffer = colors[0];
                colors[0] = JColorChooser.showDialog(null, "Hintergrundfarbe", colors[0]);
                if(colors[0] == null)
                    colors[0] = buffer;
                else
                    engine.frame.change_colors(colors);
            }
            if(e.getSource() == engine.menu_bar.court_color)
            {
                Color buffer = colors[1];
                colors[1] = JColorChooser.showDialog(null, "Spielfeldfarbe", colors[1]);
                if(colors[1] == null)
                    colors[1] = buffer;
                else
                    engine.frame.change_colors(colors);
            }
            if(e.getSource() == engine.menu_bar.p1_color)
            {
                Color buffer = colors[2];
                colors[2] = JColorChooser.showDialog(null, "Farbe Spieler 1", colors[2]);
                if(colors[2] == null)
                    colors[2] = buffer;
                else
                    engine.frame.change_colors(colors);
            }
            if(e.getSource() == engine.menu_bar.p2_color)
            {
                Color buffer = colors[3];
                colors[3] = JColorChooser.showDialog(null, "Farbe Spieler 2", colors[3]);
                if(colors[3] == null)
                    colors[3] = buffer;
                else
                    engine.frame.change_colors(colors);
            }
            if(e.getSource() == engine.menu_bar.wrong_turn_color)
            {
                Color buffer = colors[4];
                colors[4] = JColorChooser.showDialog(null, "Farbe Falscher Zug", colors[4]);
                if(colors[4] == null)
                    colors[4] = buffer;
                else
                    engine.frame.change_colors(colors);
            }
            if(e.getSource() == engine.menu_bar.default_color)
            {
                engine.setDefaultColors();
                engine.frame.change_colors(colors);
            }


        };
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

    private void skipRound()
    {
        this.stats.addNextRound(this.round);
        this.frame.getSkipButton().setVisible(false);
        switchPlayer();
        nextRound();
    }

    private void undoRound()
    {
        this.stats.undoRound();
        if(this.stats.getCurrent() == 0)
            this.frame.getUndoButton().setVisible(false);
        switchPlayer();
        nextRound();
    }

    private void checkWin()
    {
        if(this.stats.getEmptySum() == 0)
            gameOver();
    }

    private void showPossible() {
        possibles[0] = false;
        possible = new boolean[x][x];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                if (!this.round[i][j][0] && !this.round[i][j][1]) {
                    if (checkSS(i, j) || checkSE(i, j) || checkEE(i, j) || checkNE(i, j) || checkNN(i, j) || checkNW(i, j) || checkWW(i, j) || checkSW(i, j))
                        possibles[0] = true;
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

    private void gameOver()
    {
        this.frame.getSkipButton().setVisible(false);
        this.frame.getUndoButton().setVisible(false);

        if(stats.getP1Sum() > stats.getP2Sum())
            this.frame.showWinner(1);
        else if(stats.getP1Sum() < stats.getP2Sum())
            this.frame.showWinner(2);
        else
            this.frame.showWinner(3);
    }


    private boolean checkSS(int row, int col) {
        if (row + 1 < this.x) {
            if (round[row + 1][col][0] != in_charge[0] && round[row + 1][col][1] != in_charge[1]) {
                for (int i = row + 2; i < this.x; i++) {
                    if (!round[i][col][0] && !round[i][col][1])
                        return false;
                    if (round[i][col][0] == in_charge[0] && round[i][col][1] == in_charge[1]) {
                        possible[row][col] = true;
                        return true;
                    }
                }
            }
            return false;
        } else
            return false;
    }

    private boolean checkSE(int row, int col) {
        if (row + 1 < this.x && col + 1 < this.x) {
            if (round[row + 1][col + 1][0] != in_charge[0] && round[row + 1][col + 1][1] != in_charge[1]) {
                int j = col + 2;
                for (int i = row + 2; i < this.x; i++) {
                    if (j < this.x) {
                        if (!round[i][j][0] && !round[i][j][1])
                            return false;
                        if (round[i][j][0] == in_charge[0] && round[i][j][1] == in_charge[1]) {
                            possible[row][col] = true;
                            return true;
                        }
                        j++;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }

    private boolean checkEE(int row, int col) {
        if (col + 1 < this.x) {
            if (round[row][col + 1][0] != in_charge[0] && round[row][col + 1][1] != in_charge[1]) {
                for (int i = col + 2; i < this.x; i++) {
                    if (!round[row][i][0] && !round[row][i][1])
                        return false;
                    if (round[row][i][0] == in_charge[0] && round[row][i][1] == in_charge[1]) {
                        possible[row][col] = true;
                        return true;
                    }
                }
            }
            return false;
        } else
            return false;
    }

    private boolean checkNE(int row, int col) {
        if (row - 1 >= 0 && col + 1 < this.x) {
            if (round[row - 1][col + 1][0] != in_charge[0] && round[row - 1][col + 1][1] != in_charge[1]) {
                int j = col + 2;
                for (int i = row - 2; i >= 0; i--) {
                    if (j < this.x) {
                        if (!round[i][j][0] && !round[i][j][1])
                            return false;
                        if (round[i][j][0] == in_charge[0] && round[i][j][1] == in_charge[1]) {
                            possible[row][col] = true;
                            return true;
                        }
                        j++;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }

    private boolean checkNN(int row, int col) {
        if (row - 1 >= 0) {
            if (round[row - 1][col][0] != in_charge[0] && round[row - 1][col][1] != in_charge[1]) {
                for (int i = row - 2; i >= 0; i--) {
                    if (!round[i][col][0] && !round[i][col][1])
                        return false;
                    if (round[i][col][0] == in_charge[0] && round[i][col][1] == in_charge[1]) {
                        possible[row][col] = true;
                        return true;
                    }
                }
            }
            return false;
        } else
            return false;
    }

    private boolean checkNW(int row, int col) {
        if (row - 1 >= 0 && col - 1 >= 0) {
            if (round[row - 1][col - 1][0] != in_charge[0] && round[row - 1][col - 1][1] != in_charge[1]) {
                int j = col - 2;
                for (int i = row - 2; i >= 0; i--) {
                    if (j >= 0) {
                        if (!round[i][j][0] && !round[i][j][1])
                            return false;
                        if (round[i][j][0] == in_charge[0] && round[i][j][1] == in_charge[1]) {
                            possible[row][col] = true;
                            return true;
                        }
                        j--;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }

    private boolean checkWW(int row, int col) {
        if (col - 1 >= 0) {
            if (round[row][col - 1][0] != in_charge[0] && round[row][col - 1][1] != in_charge[1]) {
                for (int i = col - 2; i >= 0; i--) {
                    if (!round[row][i][0] && !round[row][i][1])
                        return false;
                    if (round[row][i][0] == in_charge[0] && round[row][i][1] == in_charge[1]) {
                        possible[row][col] = true;
                        return true;
                    }
                }
            }
            return false;
        } else
            return false;
    }

    private boolean checkSW(int row, int col) {
        if (row + 1 < this.x && col - 1 >= 0) {
            if (round[row + 1][col - 1][0] != in_charge[0] && round[row + 1][col - 1][1] != in_charge[1]) {
                int j = col - 2;
                for (int i = row + 2; i < this.x; i++) {
                    if (j >= 0) {
                        if (!round[i][j][0] && !round[i][j][1])
                            return false;
                        if (round[i][j][0] == in_charge[0] && round[i][j][1] == in_charge[1]) {
                            possible[row][col] = true;
                            return true;
                        }
                        j--;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }

    private void switchCoins(int row, int col) {
        switchSS(row, col);
        switchSE(row, col);
        switchEE(row, col);
        switchNE(row, col);
        switchNN(row, col);
        switchNW(row, col);
        switchWW(row, col);
        switchSW(row, col);
    }

    private void switchSS(int row, int col) {
        if (row + 1 < this.x) {
            int[][] switchies = new int[7][2];
            int switched = 0;

            if (round[row + 1][col][0] != in_charge[0] && round[row + 1][col][1] != in_charge[1]) {

                for (int i = row + 1; i < this.x; i++) {
                    if (!round[i][col][0] && !round[i][col][1])
                        return;
                    if (round[i][col][0] != in_charge[0] && round[i][col][1] != in_charge[1]) {
                        switchies[switched][0] = i;
                        switchies[switched][1] = col;
                        switched++;
                    }
                    if (round[i][col][0] == in_charge[0] && round[i][col][1] == in_charge[1]) {
                        for (int k = 0; k < switched; k++) {
                            round[switchies[k][0]][switchies[k][1]][0] = !round[switchies[k][0]][switchies[k][1]][0];
                            round[switchies[k][0]][switchies[k][1]][1] = !round[switchies[k][0]][switchies[k][1]][1];
                        }
                        return;
                    }
                }
            }
        }
    }

    private void switchSE(int row, int col) {
        if (row + 1 < this.x && col + 1 < this.x) {
            int[][] switchies = new int[7][2];
            int switched = 0;
            if (round[row + 1][col + 1][0] != in_charge[0] && round[row + 1][col + 1][1] != in_charge[1]) {
                int j = col + 1;
                for (int i = row + 1; i < this.x; i++) {
                    if (j < this.x) {
                        if (!round[i][j][0] && !round[i][j][1])
                            return;
                        if (round[i][j][0] != in_charge[0] && round[i][j][1] != in_charge[1]) {
                            switchies[switched][0] = i;
                            switchies[switched][1] = j;
                            switched++;
                        }
                        if (round[i][j][0] == in_charge[0] && round[i][j][1] == in_charge[1]) {
                            for (int k = 0; k < switched; k++) {
                                round[switchies[k][0]][switchies[k][1]][0] = !round[switchies[k][0]][switchies[k][1]][0];
                                round[switchies[k][0]][switchies[k][1]][1] = !round[switchies[k][0]][switchies[k][1]][1];
                            }
                            return;
                        }
                        j++;
                    } else {
                        return;
                    }
                }
            }

        }
    }

    private void switchEE(int row, int col) {
        if (col + 1 < this.x) {
            int[][] switchies = new int[7][2];
            int switched = 0;

            if (round[row][col + 1][0] != in_charge[0] && round[row][col + 1][1] != in_charge[1]) {
                for (int i = col + 1; i < this.x; i++) {
                    if (!round[row][i][0] && !round[row][i][1])
                        return;
                    if (round[row][i][0] != in_charge[0] && round[row][i][1] != in_charge[1]) {
                        switchies[switched][0] = row;
                        switchies[switched][1] = i;
                        switched++;
                    }
                    if (round[row][i][0] == in_charge[0] && round[row][i][1] == in_charge[1]) {
                        for (int k = 0; k < switched; k++) {
                            round[switchies[k][0]][switchies[k][1]][0] = !round[switchies[k][0]][switchies[k][1]][0];
                            round[switchies[k][0]][switchies[k][1]][1] = !round[switchies[k][0]][switchies[k][1]][1];
                        }
                        return;
                    }
                }
            }
        }
    }

    private void switchNE(int row, int col) {
        if (row - 1 >= 0 && col + 1 < this.x) {
            int[][] switchies = new int[7][2];
            int switched = 0;
            if (round[row - 1][col + 1][0] != in_charge[0] && round[row - 1][col + 1][1] != in_charge[1]) {
                int j = col + 1;
                for (int i = row - 1; i >= 0; i--) {
                    if (j < this.x) {
                        if (!round[i][j][0] && !round[i][j][1])
                            return;
                        if (round[i][j][0] != in_charge[0] && round[i][j][1] != in_charge[1]) {
                            switchies[switched][0] = i;
                            switchies[switched][1] = j;
                            switched++;
                        }
                        if (round[i][j][0] == in_charge[0] && round[i][j][1] == in_charge[1]) {
                            for (int k = 0; k < switched; k++) {
                                round[switchies[k][0]][switchies[k][1]][0] = !round[switchies[k][0]][switchies[k][1]][0];
                                round[switchies[k][0]][switchies[k][1]][1] = !round[switchies[k][0]][switchies[k][1]][1];
                            }
                            return;
                        }
                        j++;
                    } else {
                        return;
                    }
                }
            }

        }
    }

    private void switchNN(int row, int col) {
        if (row - 1 >= 0) {
            int[][] switchies = new int[7][2];
            int switched = 0;

            if (round[row - 1][col][0] != in_charge[0] && round[row - 1][col][1] != in_charge[1]) {
                for (int i = row - 1; i >= 0; i--) {
                    if (!round[i][col][0] && !round[i][col][1])
                        return;
                    if (round[i][col][0] != in_charge[0] && round[i][col][1] != in_charge[1]) {
                        switchies[switched][0] = i;
                        switchies[switched][1] = col;
                        switched++;
                    }
                    if (round[i][col][0] == in_charge[0] && round[i][col][1] == in_charge[1]) {
                        for (int k = 0; k < switched; k++) {
                            round[switchies[k][0]][switchies[k][1]][0] = !round[switchies[k][0]][switchies[k][1]][0];
                            round[switchies[k][0]][switchies[k][1]][1] = !round[switchies[k][0]][switchies[k][1]][1];
                        }
                        return;
                    }
                }
            }
        }
    }

    private void switchNW(int row, int col) {
        if (row - 1 >= 0 && col - 1 >= 0) {
            int[][] switchies = new int[7][2];
            int switched = 0;
            if (round[row - 1][col - 1][0] != in_charge[0] && round[row - 1][col - 1][1] != in_charge[1]) {
                int j = col - 1;
                for (int i = row - 1; i >= 0; i--) {
                    if (j >= 0) {
                        if (!round[i][j][0] && !round[i][j][1])
                            return;
                        if (round[i][j][0] != in_charge[0] && round[i][j][1] != in_charge[1]) {
                            switchies[switched][0] = i;
                            switchies[switched][1] = j;
                            switched++;
                        }
                        if (round[i][j][0] == in_charge[0] && round[i][j][1] == in_charge[1]) {
                            for (int k = 0; k < switched; k++) {
                                round[switchies[k][0]][switchies[k][1]][0] = !round[switchies[k][0]][switchies[k][1]][0];
                                round[switchies[k][0]][switchies[k][1]][1] = !round[switchies[k][0]][switchies[k][1]][1];
                            }
                            return;
                        }
                        j--;
                    } else {
                        return;
                    }
                }
            }

        }
    }

    private void switchWW(int row, int col) {
        if (col - 1 >= 0) {
            int[][] switchies = new int[7][2];
            int switched = 0;

            if (round[row][col - 1][0] != in_charge[0] && round[row][col - 1][1] != in_charge[1]) {
                for (int i = col - 1; i >= 0; i--) {
                    if (!round[row][i][0] && !round[row][i][1])
                        return;
                    if (round[row][i][0] != in_charge[0] && round[row][i][1] != in_charge[1]) {
                        switchies[switched][0] = row;
                        switchies[switched][1] = i;
                        switched++;
                    }
                    if (round[row][i][0] == in_charge[0] && round[row][i][1] == in_charge[1]) {
                        for (int k = 0; k < switched; k++) {
                            round[switchies[k][0]][switchies[k][1]][0] = !round[switchies[k][0]][switchies[k][1]][0];
                            round[switchies[k][0]][switchies[k][1]][1] = !round[switchies[k][0]][switchies[k][1]][1];
                        }
                        return;
                    }
                }
            }
        }
    }

    private void switchSW(int row, int col) {
        if (row + 1 < this.x && col - 1 >= 0) {
            int[][] switchies = new int[7][2];
            int switched = 0;
            if (round[row + 1][col - 1][0] != in_charge[0] && round[row + 1][col - 1][1] != in_charge[1]) {
                int j = col - 1;
                for (int i = row + 1; i < this.x; i++) {
                    if (j >= 0) {
                        if (!round[i][j][0] && !round[i][j][1])
                            return;
                        if (round[i][j][0] != in_charge[0] && round[i][j][1] != in_charge[1]) {
                            switchies[switched][0] = i;
                            switchies[switched][1] = j;
                            switched++;
                        }
                        if (round[i][j][0] == in_charge[0] && round[i][j][1] == in_charge[1]) {
                            for (int k = 0; k < switched; k++) {
                                round[switchies[k][0]][switchies[k][1]][0] = !round[switchies[k][0]][switchies[k][1]][0];
                                round[switchies[k][0]][switchies[k][1]][1] = !round[switchies[k][0]][switchies[k][1]][1];
                            }
                            return;
                        }
                        j--;
                    } else {
                        return;
                    }
                }
            }

        }
    }

    private void setDefaultColors()
    {
        Color bg_color = new Color(135, 206, 235);
        Color court_color = new Color(34, 139, 34);
        Color p1_color = Color.BLACK;
        Color p2_color = Color.WHITE;
        Color wrong_turn_color = Color.RED;

        colors = new Color[]{bg_color, court_color, p1_color, p2_color, wrong_turn_color};
    }
}
