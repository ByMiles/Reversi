package Fx_GUI;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Frame extends Stage {

    private VBox root;
    private Court court;
    private Action_bar action_bar;
    private Title_bar title_bar;
    private Side_bar left_bar, right_bar;
    private BorderPane contentPane;

    private double courtsize, side_width, side_height;

    private String[] colors;
    public double width, height;

    
    public Frame(double width, double height, Action_bar action_bar, String title)
    {
        root = new VBox();
        root.getStylesheets().add("Fx_GUI/Style.css");

        calculate_sizes(width, height);
        title += " by Miles ;-)";
        this.setTitle(title);

        contentPane = new BorderPane();
        contentPane.setMinSize(400., 400.);
        contentPane.setPrefSize(width, height);
        contentPane.setBottom(addAction_bar(action_bar));
        contentPane.setTop(createTitle_bar(title));
        contentPane.getStyleClass().add("root");


        left_bar = new Side_bar(side_width, height);
        right_bar = new Side_bar(side_width, height);
        contentPane.setLeft(left_bar);
        contentPane.setRight(right_bar);

        setDefaultColors();

        root.getChildren().add(contentPane);

        Scene scene = new Scene(root,width, height);

        scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> resizes(contentPane.getWidth(), contentPane.getHeight()));
        scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> resizes(contentPane.getWidth(), contentPane.getHeight()));

        this.setScene(scene);
        this.show();
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
        resizes(contentPane.getWidth(), contentPane.getHeight());
    }

    private Action_bar addAction_bar(Action_bar action_bar)
    {
        this.action_bar = action_bar;
        this.action_bar.resizes(this.width, this.side_height);
        return action_bar;
    }

    private Title_bar createTitle_bar(String title)
    {
        title_bar = new Title_bar(this.width, this.height*0.1 - 50, title);

        return title_bar;
    }

    public void addMenu_bar(Menu_bar menu_bar)
    {
        menu_bar.prefWidthProperty().bind(this.widthProperty());
        root.getChildren().add(0, menu_bar);
    }


    void setDefaultColors(){

        colors = new String [5];
        colors[0] = "-bg-color: rgb(135, 206, 235);\n";
        colors[1] = "-court-color: rgb(34, 139, 34);\n";
        colors[2] = "-coin-p1-color: rgb( 0, 0, 0);\n";
        colors[3] = "-coin-p2-color: rgb( 255, 255, 255);\n";
        colors[4] = "-error-color: rgb( 255, 0, 0);";

        String color = "";

        for (String s: colors) {
            color += s;
        }

        contentPane.setStyle(color);
    }

    void changeColor(int type, int r, int g, int b)
    {
        switch (type)
        {
            case 0: colors[0] = String.format("-bg-color: rgb(%d, %d, %d);\n", r, g, b);
                break;
            case 1: colors[1] = String.format("-court-color: rgb(%d, %d, %d);\n", r, g, b);
                break;
            case 2: colors[2] = String.format("-coin-p1-color: rgb(%d, %d, %d);\n", r, g, b);
                break;
            case 3: colors[3] = String.format("-coin-p2-color: rgb(%d, %d, %d);\n", r, g, b);
                break;
            case 4: colors[4] = String.format("-error-color: rgb(%d, %d, %d);\n", r, g, b);
                break;
        }

        String color = "";

        for (String s: colors) {
            color += s;
        }

        contentPane.setStyle(color);
    }

        private void calculate_sizes(double width, double height)
        {
            this.width = width;
            this.height = height;


            if(this.height >= this.width)
            {
                courtsize = this.width * 0.8;
            }
            else
            {
                courtsize = this.height * 0.8;
            }

            side_width = (this.width - courtsize)/2;
            side_height = (this.height - courtsize)/2;
        }


        private void resizes(double width, double height)
        {
            calculate_sizes(width, height);
            court.resizes(courtsize);

            action_bar.resizes(width, side_height);
            left_bar.resizes(side_width, height);
            right_bar.resizes(side_width, height);

            title_bar.resizes(width, side_height);
        }

}
