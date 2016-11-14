package Fx_GUI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;


class Cell extends StackPane
{
    private boolean stats[];
    private boolean show_preview;
    private double cellsize;
    private DropShadow dropShadow;

    Cell(double cellsize)
    {
        this.getStylesheets().add("Fx_GUI/Style.css");
        this.getStyleClass().add("cell");
        this.cellsize = cellsize;

        this.show_preview = true;

        dropShadow = new DropShadow();
        dropShadow.setRadius(cellsize*0.4);
        dropShadow.setOffsetX(5.0);
        dropShadow.setOffsetY(5.0);
        dropShadow.setColor(Color.color(0, 0, 0, 0.75));
    }

    void setStats(boolean possible, boolean p1, boolean p2, boolean in_charge)
    {
        this.stats = new boolean[4];

        this.stats[0] = possible;
        this.stats[1] = p1;
        this.stats[2] = p2;
        this.stats[3] = in_charge;

        actualize();
    }

    private void actualize()
    {
        this.getChildren().clear();

        Circle circle;
        if(stats[0] && show_preview)
        {
            circle = new Circle();
            circle.setCenterX(cellsize/2);
            circle.setCenterY(cellsize/2);
            circle.setRadius(cellsize*0.1);
            if(stats[3]) {
                circle.getStyleClass().add("p1_color");
            }
            else
                circle.getStyleClass().add("p2_color");
            this.getChildren().add(circle);
        }
        if(stats[1]) {
            circle = new Circle(cellsize*0.4);
            circle.getStyleClass().addAll("p1_color", "dropshadow");
            circle.setCenterX(cellsize/2);
            circle.setCenterY(cellsize/2);

            circle.setEffect(dropShadow);
            this.getChildren().add(circle);
        }

        if(stats[2]){
            circle = new Circle(cellsize*0.4);
            circle.getStyleClass().addAll("p2_color", "dropshadow");
            circle.setCenterX(cellsize/2);
            circle.setCenterY(cellsize/2);

            circle.setEffect(dropShadow);
            this.getChildren().add(circle);
        }
    }

    void changePreview()
    {
        this.show_preview = !this.show_preview;
        actualize();
    }

    void wrong_turn()
    {
        Node circle = null;
        if(this.getChildren().size() != 0)
            circle = this.getChildren().get(0);

        this.getChildren().clear();
        Rectangle rec = new Rectangle(0, 0, (int)this.getWidth(), (int)this.getHeight());
        rec.getStyleClass().add("error");
        this.getChildren().add(rec);
        if(circle != null)
            this.getChildren().add(circle);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> actualize()));
        timeline.play();



    }



}
