package Reversi;

import Fx_GUI.*;
import Fx_GUI.Court;
import Fx_GUI.Frame;
import Fx_GUI.Menu_bar;


public class Fx_Engine
{
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

    public Fx_Engine(double width, double height)
    {
        this.action_bar = new Action_bar();
        this.frame = new Frame(width, height, action_bar, "Reversi");
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
            action_bar.getUndo_button().setVisible(true);
        }

        action_bar.setP1_text(String.valueOf(rules.stats.getP1Sum()));
        action_bar.setP2_text(String.valueOf(rules.stats.getP2Sum()));
        action_bar.inCharge(in_charge[0]);

        if(!possibles[0] && possibles[1])
            action_bar.getSkip_button().setVisible(true);
    }

    private void endRound(int row, int col) {
        rules.endRound(row, col);
        newRound();
    }

    private void skipRound(){
        this.rules.skipRound();
        action_bar.getSkip_button().setVisible(false);
        newRound();
    }

    private void undoRound()
    {
        if(!this.rules.undoRound())
        {
            action_bar.getUndo_button().setVisible(false);
        }
        newRound();
    }

    private void gameOver() {
        action_bar.getSkip_button().setVisible(false);
        action_bar.getUndo_button().setVisible(false);
        action_bar.callWinner(rules.gameOver());
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
        action_bar.getSkip_button().setOnMouseReleased(event -> skipRound());
        action_bar.getUndo_button().setOnMouseReleased(event -> undoRound());
        this.menu_bar.getStart_pvp().setOnAction(e -> newGame());
        this.menu_bar.getShow_preview().setOnAction(e -> this.court.changePreview());
    }
}
