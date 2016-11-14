package Fx_GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class Action_bar extends HBox
{
    Button skip_button, undo_button;
    StackPane winner_label;
    Coin_panel p1_coin, p2_coin;
    boolean p1;

    double size;


    Action_bar(double width, double height)
    {
        super(width/50);
        this.setAlignment(Pos.CENTER);
        this.getStylesheets().add("Fx_GUI/Style.css");
        this.setPrefSize(width, height);

        this.size = height*0.8;

        p1_coin = new Coin_panel(size, true);
        p2_coin = new Coin_panel(size, false);

        skip_button = new Button("Skip");
        undo_button = new Button("Undo");

        winner_label = new StackPane();

        this.getChildren().addAll(p1_coin, skip_button, winner_label, undo_button, p2_coin);
        this.getStyleClass().add("actionFont");

        skip_button.setVisible(false);
        undo_button.setVisible(false);
    }

    void setP1_text(String text)
    {
        p1_coin.setText(text);
    }

    void setP2_text(String text)
    {
        p2_coin.setText(text);
    }

    void inCharge(boolean p1)
    {
        winner_label.getChildren().clear();
        Text text = new Text();
        this.p1 = p1;
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

    void showWinner(int state)
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
        winner_label.getChildren().add(text);
    }

}
