package Fx_GUI;

import javafx.scene.layout.VBox;


class Side_bar extends VBox
{
    Side_bar(double width, double height)
    {
        resizes(width, height);
    }

    void resizes(double width, double height)
    {
        this.setPrefSize(width, height);
    }
}
