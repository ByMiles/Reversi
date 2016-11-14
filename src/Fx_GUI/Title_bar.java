package Fx_GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Created by Miles on 12.11.2016 for me.
 */
public class Title_bar extends HBox
{
    Title_bar(double width, double height)
    {
        this.getStylesheets().add("Fx_GUI/Style.css");
        this.setPrefSize(width, height);
        this.setAlignment(Pos.CENTER);
        Label label = new Label("Reversi");
        label.getStyleClass().add("titleFont");
        this.getChildren().add(label);

    }
}
