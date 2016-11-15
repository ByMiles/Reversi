package Fx_GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

class Title_bar extends HBox
{
    private Label label;
    Title_bar(double width, double height, String title)
    {
        this.getStylesheets().add("Fx_GUI/Style.css");
        this.setAlignment(Pos.CENTER);
        label = new Label(title);
        label.getStyleClass().add("titleFont");
        resizes(width, height);
        this.getChildren().add(label);

    }

    void resizes(double width, double height)
    {
        this.setPrefSize(width, height);
        if(width/10 > height)
            label.setStyle(String.format("-fx-font-size: %dpx", ((int)(48./64.*height))));
        else
            label.setStyle(String.format("-fx-font-size: %dpx", ((int)(48./640.*width))));
    }
}
