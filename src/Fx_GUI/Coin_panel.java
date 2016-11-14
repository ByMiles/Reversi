package Fx_GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

class Coin_panel extends StackPane
{
    private Text text;

    Coin_panel(double size, boolean p1)
    {
        this.setPrefSize(size, size);
        Circle circle = new Circle();
        circle.setCenterX(size/2);
        circle.setCenterY(size/2);
        circle.setRadius(size*0.5);
        this.getChildren().add(circle);

        if(p1)
            circle.getStyleClass().addAll("p1_color", "dropshadow");
        else
            circle.getStyleClass().addAll("p2_color", "dropshadow");


        text = new Text("32");

        if(!p1)
            text.getStyleClass().addAll("p1_color", "actionFont");
        else
            text.getStyleClass().addAll("p2_color", "actionFont");

        this.getChildren().add(text);
    }

    public void setText(String text)
    {
        this.text.setText(text);
    }


}
