
import Reversi.Fx_Engine;
import javafx.application.Application;
import Reversi.Swing_Engine;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;


public class Main extends Application
{
    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setOnCloseRequest(arg0 -> System.exit(0));
        primaryStage.setTitle("Oberfläche auswählen!");

        VBox root = new VBox();

        HBox[]boxes = new HBox[4];
        for (int i = 0; i < boxes.length; i++)
        {
            boxes[i] = new HBox(10);
            boxes[i].setAlignment(Pos.CENTER);
        }
        boxes[0].getChildren().add(new Text("SpielOberfläche auswählen: "));

        TextField widthField = new TextField("1200");
        widthField.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        boxes[1].getChildren().addAll(new Text("Fenster-Breite: "), widthField);

        TextField heightField = new TextField("800");
        widthField.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        boxes[2].getChildren().addAll(new Text("Fenster-Höhe: "), heightField);

        Button swingbutton = new Button("Swing-Style");
        swingbutton.setOnAction(e -> new Swing_Engine((int)parseWidth(widthField.getText()), (int)parseHeight(heightField.getText())));

        Button fxbutton = new Button("JavaFx-Style");
        fxbutton.setOnAction(e -> new Fx_Engine(parseWidth(widthField.getText()), parseHeight(heightField.getText())));
        boxes[3].getChildren().addAll(swingbutton, fxbutton);
        root.getChildren().addAll(boxes[0], boxes[1], boxes[2], boxes[3]);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.setX(100);
        primaryStage.setY(100);
        primaryStage.show();

    }

    private double parseWidth(String text)
    {
        double value;
        try
        {
            value = Double.parseDouble(text);
        }
        catch (Exception e){ return 1200.;}

        if(value < 400 || value > GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth())
        {
            return 1200.;
        }

        return value;
    }

    private double parseHeight(String text)
    {
        double value;
        try
        {
            value = Double.parseDouble(text);
        }
        catch (Exception e){ return 800.;}

        if(value < 400 || value > GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight())
        {
            return 800.;
        }

        return value;
    }


}
