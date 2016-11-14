package Reversi;

import Fx_GUI.*;
import javafx.scene.paint.Color;



public class Fx_Engine
{
    private Frame frame;
    private Court court;
    private Menu_bar menu_bar;
    private Rules rules;

    private boolean[] in_charge;
    private boolean[][] possible;
    private boolean[] possibles;
    private boolean[][][] round;
    private int x;

    public Fx_Engine(Frame frame)
    {
        this.frame = frame;
        this.menu_bar = new Menu_bar(frame);
        this.frame.addMenu_bar(menu_bar);
        createActionListener();

        newGame();
    }

    private void newGame() {
        this.x = menu_bar.getX();
        int variation = menu_bar.getVariation();
        int beginns = menu_bar.getBeginns();
        this.rules = new Rules(this.x, variation, beginns);

        rules.getRound();
        court = new Court(this.x, frame.width, frame.height);
        createMouseAdapter();
        frame.addCourt(court);

        newRound();

    }

    private void newRound() {
        rules.newRound();
        this.round = rules.getRound();
        this.possible = rules.getPossible();
        this.possibles = rules.getPossibles();
        this.in_charge = rules.getIn_charge();

        showCourt();
        actualizeAction_bar();
        
        if(rules.checkWin())
            gameOver();
    }

    private void showCourt() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                this.court.modifyCell(i, j, this.possible[i][j], this.round[i][j][0], this.round[i][j][1], this.in_charge[0]);
            }
        }
    }

    private void actualizeAction_bar()
    {
        if(rules.stats.getCurrent() == 1)
        {
            frame.getUndoButton().setVisible(true);
        }
        frame.actualizeActionBar(rules.stats.getP1Sum(), rules.stats.getP2Sum(), in_charge[0]);

        if(!possibles[0] && possibles[1])
            this.frame.getSkipButton().setVisible(true);
    }

    private void endRound(int row, int col) {
        rules.endRound(row, col);
        newRound();
    }

    private void skipRound(){
        this.rules.skipRound();
        this.frame.getSkipButton().setVisible(false);
        newRound();
    }

    private void undoRound()
    {
        if(!this.rules.undoRound())
        {
            this.frame.getUndoButton().setVisible(false);
        }
        newRound();
    }

    private void gameOver() {
        this.frame.getSkipButton().setVisible(false);
        this.frame.getUndoButton().setVisible(false);
        this.frame.showWinner(rules.gameOver());
    }

    private void createMouseAdapter() {

        for (int row = 0; row < this.x; row++) {
            for (int col = 0; col < this.x; col++) {
                final int row_f = row;
                final int col_f = col;
                this.court.setMouseListener(row, col, event -> {
                    if (possible[row_f][col_f])
                    {
                        endRound(row_f, col_f);
                    }
                    else
                    {
                        this.court.wrongTurn(row_f, col_f);
                    }
                });
            }
        }
    }

    private void createActionListener(){
        this.frame.getSkipButton().setOnMouseReleased(event -> skipRound());
        this.frame.getUndoButton().setOnMouseReleased(event -> undoRound());
        this.menu_bar.start_pvp.setOnAction(e -> newGame());
        this.menu_bar.show_preview.setOnAction(e -> this.court.changePreview());
    }
}
