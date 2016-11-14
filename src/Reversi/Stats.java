package Reversi;

import java.util.ArrayList;


class Stats
{
    private int x, current;

    private ArrayList<boolean[][][]> rounds;
    private int[] sums;

    Stats(int x, boolean[][][] start_up)
    {
        this.x = x;
        this.current = 0;
        rounds = new ArrayList<>();

        beginn(start_up);
    }

    private void beginn(boolean [][][] start_up)
    {
        rounds.add(start_up);
    }

    boolean[][][] getCurrentRound()
    {
        boolean[][][] round = new boolean[x][x][2];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++)
            {
                round[i][j][0]  = rounds.get(current)[i][j][0];
                round[i][j][1]  = rounds.get(current)[i][j][1];
            }

        }
        calcSums();
        return round;
    }

    void addNextRound(boolean[][][] round)
    {
        rounds.add(round);
        current++;
    }

    boolean undoRound()
    {
        if(current == 0)
            return false;

        rounds.remove(current);
        current--;
        return true;
    }

    int getCurrent()
    {
        return current;
    }

    private void calcSums()
    {
        sums = new int[3];
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.x; j++) {
                if (rounds.get(current)[i][j][0] && !rounds.get(current)[i][j][1])
                    sums[0]++;
                else if (rounds.get(current)[i][j][1] && !rounds.get(current)[i][j][0])
                    sums[1]++;
                else
                    sums[2]++;
            }

        }
    }

    int getP1Sum()
    {
        return sums[0];
    }

    int getP2Sum()
    {
        return sums[1];
    }

    int getEmptySum()
    {
        return sums[2];
    }
}
