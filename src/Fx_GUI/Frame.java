package Fx_GUI;

import Reversi.Fx_Engine;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Frame extends Application {

    VBox root;
    Court court;
    Action_bar action_bar;
    Title_bar title_bar;
    BorderPane contentPane;
    Menu_bar menu_bar;
    public double width, height;
    Fx_Engine engine;


   @Override
    public void start(Stage primaryStage) throws Exception
    {
        //primaryStage.setOnCloseRequest(event -> Platform.exit());

        root = new VBox();
        root.getStylesheets().add("Fx_GUI/Style.css");

        this.width = 1200;
        this.height = 800;
        primaryStage.setTitle("Reversi by Miles :-)");

        contentPane = new BorderPane();
        contentPane.setBottom(createAction_bar());
        contentPane.setTop(createTitle_bar());
        contentPane.getStyleClass().add("root");

        engine = new Fx_Engine(this);
        menu_bar.prefWidthProperty().bind(primaryStage.widthProperty());
        root.getChildren().add(contentPane);

        Scene scene = new Scene(root,width, height);


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void addCourt(Court court)
    {
        try
        {
            contentPane.getChildren().remove(this.court);
        }
        catch (Exception ignored){}
        this.court = court;
        contentPane.setCenter(this.court);
    }

    public Action_bar createAction_bar()
    {
        action_bar = new Action_bar(this.width, this.height*0.1);
        return action_bar;

    }

    private Title_bar createTitle_bar()
    {
        title_bar = new Title_bar(this.width, this.height*0.1);

        return title_bar;
    }

    public void addMenu_bar(Menu_bar menu_bar)
    {
        this.menu_bar = menu_bar;
        root.getChildren().add(menu_bar);


    }

    public Button getSkipButton()
    {
        return this.action_bar.skip_button;
    }

    public Button getUndoButton()
    {
        return this.action_bar.undo_button;
    }

    public void actualizeActionBar(int p1, int p2, boolean p1_)
    {
        action_bar.setP1_text(String.valueOf(p1));
        action_bar.setP2_text(String.valueOf(p2));
        action_bar.inCharge(p1_);
    }

    public void showWinner(int winner)
    {
        this.action_bar.showWinner(winner);
    }

}
