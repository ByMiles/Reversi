package Fx_GUI;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

class Coin_panel extends StackPane
{
    private Text text;

    private boolean p1;

    Coin_panel(boolean p1)
    {
        this.p1 = p1;

        text = new Text("");

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

    void resizes(double size){

        this.getChildren().clear();
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

        this.getChildren().add(text);

    }


}
