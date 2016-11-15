package Fx_GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class Action_bar extends HBox
{
    private Button skip_button, undo_button;
    private StackPane winner_label;
    private Coin_panel p1_coin, p2_coin;

    private double size;
    private int font;


    public Action_bar()
    {
        this.setAlignment(Pos.CENTER);
        this.getStylesheets().add("Fx_GUI/Style.css");

        fontsize();

        p1_coin = new Coin_panel(true);
        p2_coin = new Coin_panel(false);

        skip_button = new Button("Skip");
        skip_button.getStyleClass().add("glass-grey");
        undo_button = new Button("Undo");
        undo_button.getStyleClass().add("glass-grey");

        winner_label = new StackPane();

        this.getChildren().addAll(p1_coin, skip_button, winner_label, undo_button, p2_coin);
        this.getStyleClass().add("actionFont");

        skip_button.setVisible(false);
        undo_button.setVisible(false);
    }
    public Button getSkip_button()
    {
        return skip_button;
    }

    public Button getUndo_button()
    {
        return undo_button;
    }

    public void setP1_text(String text)
    {
        p1_coin.setText(text);
    }

    public void setP2_text(String text)
    {
        p2_coin.setText(text);
    }

    public void inCharge(boolean p1)
    {
        winner_label.getChildren().clear();
        Text text = new Text();
        if(p1) {
            text.setText("Spieler 1");
            text.getStyleClass().addAll("actionFont", "p1_font");
        }
        else
        {
            text.setText("Spieler 2");
            text.getStyleClass().addAll("actionFont", "p2_font");
        }
        winner_label.getChildren().add(text);
    }

    public void callWinner(int state)
    {
        winner_label.getChildren().clear();
        Text text = new Text();
        switch(state)
        {
            case 1:
                text.setText("<< Sieg");
                text.getStyleClass().addAll("actionFont", "p1_font");
                break;
            case 2:
                text.setText("Sieg >>");
                text.getStyleClass().addAll("actionFont", "p2_font");
                break;
            case 3:
                text.setText("unentschieden");
                text.getStyleClass().addAll("actionFont", "neutral_font");
                break;
        }
        text.setStyle(String.format("-fx-font-size: %dpx", font));
        winner_label.getChildren().add(text);
    }

    void resizes(double width, double height)
    {
        this.setPrefSize(width, height);

        if(width* 0.08 > height*0.8)
            size = height * 0.8;
        else
            size = width * 0.08;

        this.p1_coin.resizes(size);
        this.p2_coin.resizes(size);
        fontsize();
    }

    private void fontsize()
    {
        int defaultFont = 32;
        font = (int)((double) defaultFont *size/64.);
        this.setStyle(String.format("-fx-font-size: %dpx", font));
    }

}
