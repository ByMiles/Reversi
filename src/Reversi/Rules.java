package Reversi;

/**
 * @author Miles Lorenz, S0556515
 * @version 2.1. <br>
 *
 * The class Rules contains all necessary methods to play the boardgame Reversi. It is completely GUI-independent.
 * By storing and handling the object stats of class Stats Rules also manages the game related data.
 */
class Rules
{
    Stats stats;

    private boolean[] in_charge;   // [1,0] = player one in charge, [0,1] = player two in charge
    private boolean[][] possible;  // stores data for each board-field if putting a stone their is a possible move
    private boolean[] possibles;   // [0,0] = no possible  moves for both players = Game over
                                   // [0,1] = no possible moves for current player = skipable
                                   // [1,1] = moves possible = play
    private boolean[][][] round;   // round stores the current board-informations for each field e.g. if there is a stone...
                                   // [row][col] ...[false, false] = no stone, ...[true, false] = player one stone placed, ...[false, true] = player two stone placed
    private int x;

    /**
     *
     * @param x number of rows and columns of the board
     * @param variation type of arrangement of the first four stones
     * @param beginns player who starts
     */
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

    /**
     * @return the current round (transfer to the GUI)
     */
    boolean[][][] getRound()
    {
        return round;
    }

    /**
     * @return the current possible (transfer to the GUI)
     */
    boolean[][] getPossible()
    {
        return possible;
    }

    /**
     * @return the current possibles (transfer to the GUI)
     */
    boolean[] getPossibles()
    {
        return possibles;
    }

    /**
     * @return the current in_charge (transfer to the GUI)
     */
    boolean[] getIn_charge()
    {
        return in_charge;
    }

    /**
     * starts a new round by loading the current round from stats and calling showPossible()
     */
    void newRound() {
        this.round = stats.getCurrentRound();
        showPossible();
    }

    /**
     * calculates possible moves for the current round by calling checkPossible() for every field in every direction (complexity = x^2 * 8)
     */
    private void showPossible() {

        if(!possibles[0])  // if the last round where no possible moves possibles[1] = false
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
        if (possibles[0])  // if there is a possible move possibles[1] = true;
            possibles[1] = true;
    }

    /**
     *
     * @param row row of the currently observed field
     * @param col col of the currently observed field
     * @param row_change vertical direction of the observed direction
     * @param col_change horizontal direction of the observed direction
     * @return if a move is possible (true / false)
     */
    private boolean checkPossible(int row, int col, int row_change, int col_change) {
        int new_row = row + row_change;
        int new_col = col + col_change;

        if (new_row != -1 && new_row != x && new_col != -1 && new_col != x) {

            if (round[new_row][new_col][0] != in_charge[0] && round[new_row][new_col][1] != in_charge[1]) {   // the call checkPossible(row, col, 0, 0) ends here
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

    /**
     * checks if the game ended (=> no more possible moves or no free fields)
     * @return if the game ended (true / false)
     */
    boolean checkWin()
    {
        return ((!possibles[0] && !possibles[1]) || stats.getEmptySum() == 0);
    }

    /**
     * ends the round by<br>
     *     - laying a new stone at the choosen position<br>
     *     - calling switchCoins()<br>
     *     - calling switchPlayer()<br>
     * ATTENTION!: the possibility of the turn is not checked again! (prevent wrong turns with your GUI-features)
     * @param row row of the chosen field
     * @param col column of the chosen field
     */
    void endRound(int row, int col) {
        this.round[row][col][0] = this.in_charge[0];
        this.round[row][col][1] = this.in_charge[1];
        switchCoins(row, col);
        this.stats.addNextRound(this.round);
        switchPlayer();
    }

    /**
     * changes in_charge and this way also the player who is in charge
     */
    private void switchPlayer() {
        in_charge[0] = !in_charge[0];
        in_charge[1] = !in_charge[1];
    }

    /**
     * swiches the color of all turned stones by calling checkCoins() for every direction
     * @param row row of the new stone
     * @param col column of the new stone
     */
    private void switchCoins(int row, int col) {
        for (int row_change = -1; row_change <= 1; row_change++) {
            for (int col_change = -1; col_change <= 1; col_change++) {
                checkCoins(row, col, row_change, col_change);
            }
        }
    }

    /**
     * checks for one direction if stones need to be turned.
     * @param row row of the new stone
     * @param col column of the new stone
     * @param row_change horizontal direction of the observed direction
     * @param col_change vertical direction of the observed direction
     */
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

    /**
     * skips the round by adding round to stats without changes, calling switchPlayer();
     */
    void skipRound()
    {
        this.stats.addNextRound(this.round);
        switchPlayer();
    }

    /**
     * undos the round by calling stats.undoRound(), calling switchPlayer();
     * @return if there are more undoable rounds (true / false)
     */
    boolean undoRound()
    {
        this.stats.undoRound();
        switchPlayer();

        return(stats.getCurrent() > 0);
    }

    /**
     * calculates the winner of the game (1 = player one, 2 = player two, 3 = tie
     * @return
     */
    int gameOver()
    {
        if (stats.getP1Sum() > stats.getP2Sum())
            return 1;
        if (stats.getP1Sum() < stats.getP2Sum())
            return 2;

        return 3;
    }

}
