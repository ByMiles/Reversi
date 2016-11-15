package Reversi;


class Rules
{
    Stats stats;

    private boolean[] in_charge;
    private boolean[][] possible;
    private boolean[] possibles;
    private boolean[][][] round;
    private int x;

    Rules(int x, int variation, int beginns)
    {
        this.x = x;
        possibles = new boolean[]{false, true};
        round = new boolean[x][x][2];

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

        in_charge = new boolean[]{false, false};
        in_charge[beginns] = true;

        stats = new Stats(x, this.round);
    }

    boolean[][][] getRound()
    {
        return round;
    }
    boolean[][] getPossible()
    {
        return possible;
    }
    boolean[] getPossibles()
    {
        return possibles;
    }
    boolean[] getIn_charge()
    {
        return in_charge;
    }

    void newRound() {
        this.round = stats.getCurrentRound();
        showPossible();
    }

    private void showPossible() {

        if(!possibles[0])
            possibles[1] = false;

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
        }
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

    boolean checkWin()
    {
        return ((!possibles[0] && !possibles[1]) || stats.getEmptySum() == 0);
    }

    void endRound(int row, int col) {
        this.round[row][col][0] = this.in_charge[0];
        this.round[row][col][1] = this.in_charge[1];
        switchCoins(row, col);
        this.stats.addNextRound(this.round);
        switchPlayer();
    }

    private void switchPlayer() {
        in_charge[0] = !in_charge[0];
        in_charge[1] = !in_charge[1];
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

            int[][] switchies = new int[this.x-1][2];
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

    void skipRound()
    {
        this.stats.addNextRound(this.round);
        switchPlayer();
    }

    boolean undoRound()
    {
        this.stats.undoRound();
        switchPlayer();

        return(stats.getCurrent() > 0);
    }

    int gameOver()
    {
        if (stats.getP1Sum() > stats.getP2Sum())
            return 1;
        if (stats.getP1Sum() < stats.getP2Sum())
            return 2;

        return 3;
    }

}
