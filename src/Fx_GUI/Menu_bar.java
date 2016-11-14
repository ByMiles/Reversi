package Fx_GUI;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Menu_bar extends MenuBar
{
    private Menu new_menu, pvp_menu, pvc_menu, option_menu;
    public MenuItem start_pvp, show_preview, change_colors;
    private RadioButton n_6, n_7, n_8, n_9, n_10, v_1, v_2, v_3, s_1, s_2;

    Frame frame;

    private int x, variation, beginns;

    public Menu_bar(Frame frame)
    {
        super();
        this.frame = frame;
        this.getStylesheets().add("Fx_GUI/Style.css");
        this.getStyleClass().add("menuFont");

        this.x = 8;
        this.variation = 1;
        this.beginns = 0;

        new_menu = new Menu("Neu");

        pvc_menu = new Menu("Spieler vs Computer");

        this.getMenus().addAll(new_menu, createOptions_menu());
        new_menu.getItems().addAll(createPvp_menu(), pvc_menu);
    }

    private Menu createPvp_menu()
    {
        CustomMenuItem[] cmi_x = new CustomMenuItem[5];
        pvp_menu = new Menu("Spieler vs Spieler");

        ToggleGroup court_x = new ToggleGroup();

        n_6 = new RadioButton("6 x 6");
        n_6.setOnAction(e -> x = 6);
        n_6.setToggleGroup(court_x);
        n_6.setStyle("-fx-text-fill: black;");
        cmi_x[0] = new CustomMenuItem(n_6, false);

        n_7 = new RadioButton("7 x 7");
        n_7.setOnAction(e -> x = 7);
        n_7.setStyle("-fx-text-fill: black;");
        cmi_x[1] = new CustomMenuItem(n_7, false);
        n_7.setToggleGroup(court_x);
        
        n_8 = new RadioButton("8 x 8");
        n_8.setOnAction(e -> x = 8);
        n_8.setStyle("-fx-text-fill: black;");
        n_8.setSelected(true);
        cmi_x[2] = new CustomMenuItem(n_8, false);
        n_8.setToggleGroup(court_x);

        n_9 = new RadioButton("9 x 9");
        n_9.setOnAction(e -> x = 9);
        n_9.setStyle("-fx-text-fill: black;");
        cmi_x[3] = new CustomMenuItem(n_9, false);
        n_9.setToggleGroup(court_x);

        n_10 = new RadioButton("10x10");
        n_10.setStyle("-fx-text-fill: black;");
        n_10.setOnAction(e -> x = 10);
        cmi_x[4] = new CustomMenuItem(n_10, false);
        n_10.setToggleGroup(court_x);

        for (CustomMenuItem m: cmi_x) {
            pvp_menu.getItems().add(m);
        }

        pvp_menu.getItems().add(new SeparatorMenuItem());

        ToggleGroup variation = new ToggleGroup();

        CustomMenuItem[] cmi_v = new CustomMenuItem[3];

        v_1 = new RadioButton("Variante 1");
        v_1.setOnAction(e -> this.variation = 1);
        v_1.setSelected(true);
        v_1.setStyle("-fx-text-fill: black;");
        cmi_v[0] = new CustomMenuItem(v_1, false);
        v_1.setToggleGroup(variation);

        v_2 = new RadioButton("Variante 2");
        v_2.setOnAction(e -> this.variation = 2);
        v_2.setStyle("-fx-text-fill: black;");
        cmi_v[1] = new CustomMenuItem(v_2, false);
        v_2.setToggleGroup(variation);

        v_3 = new RadioButton("Variante 3");
        v_3.setOnAction(e -> this.variation = 3);
        v_3.setStyle("-fx-text-fill: black;");
        cmi_v[2] = new CustomMenuItem(v_3, false);
        v_3.setToggleGroup(variation);

        for (CustomMenuItem m: cmi_v) {
            pvp_menu.getItems().add(m);
        }

        pvp_menu.getItems().add(new SeparatorMenuItem());

        ToggleGroup beginns = new ToggleGroup();

        CustomMenuItem[] cmi_s = new CustomMenuItem[2];

        s_1 = new RadioButton("Spieler 1");
        s_1.setOnAction(e -> this.beginns = 0);
        s_1.setSelected(true);
        s_1.setStyle("-fx-text-fill: black;");
        cmi_s[0] = new CustomMenuItem(s_1, false);
        s_1.setToggleGroup(beginns);

        s_2 = new RadioButton("Spieler 2");
        s_2.setOnAction(e -> this.beginns = 1);
        s_2.setStyle("-fx-text-fill: black;");
        cmi_s[1] = new CustomMenuItem(s_2, false);
        s_2.setToggleGroup(beginns);

        for (CustomMenuItem m: cmi_s) {
            pvp_menu.getItems().add(m);
        }

        pvp_menu.getItems().add(new SeparatorMenuItem());

        start_pvp = new MenuItem("Spiel starten");

        pvp_menu.getItems().add(start_pvp);

        return pvp_menu;
    }

    private Menu createOptions_menu()
    {
        option_menu = new Menu("Optionen");

        show_preview = new MenuItem("Vorschau anzeigen");
        option_menu.getItems().add(show_preview);


        change_colors = new MenuItem("Farben anpassen");
        change_colors.setOnAction(e -> colorWindow());
        option_menu.getItems().add(change_colors);

        return option_menu;
    }

    public int getX()
    {
        return x;
    }
    public int getVariation()
    {
        return variation;
    }
    public int getBeginns()
    {
        return beginns;
    }

    private void colorWindow()
    {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);

        VBox box = new VBox();
        Scene scene = new Scene(box);
        stage.setTitle("Farben anpassen");

        scene.getStylesheets().add("Fx_GUI/Style.css");
        box.getStyleClass().add("actionFont");

        HBox[] hBoxes = new HBox[6];

        ColorPicker cp_bg = new ColorPicker();
        cp_bg.setOnAction(event ->
        {
            int g = (int)(cp_bg.getValue().getGreen()*255);
            int r = (int)(cp_bg.getValue().getRed()*255);
            int b = (int)(cp_bg.getValue().getBlue()*255);

            this.frame.contentPane.setStyle(String.format("-bg-color: rgb(%d, %d, %d);", r, g, b));
        });

        hBoxes[0] = new HBox();
        hBoxes[0].getChildren().addAll(new Label("Hintergrund: "), cp_bg);

        ColorPicker cp_court = new ColorPicker();
        cp_court.setOnAction(event ->
        {
            int g = (int)(cp_court.getValue().getGreen()*255);
            int r = (int)(cp_court.getValue().getRed()*255);
            int b = (int)(cp_court.getValue().getBlue()*255);

            this.frame.contentPane.setStyle(String.format("-court-color: rgb(%d, %d, %d);", r, g, b));
        });

        hBoxes[1] = new HBox();
        hBoxes[1].getChildren().addAll(new Label("Spielbrett: "), cp_court);

        ColorPicker cp_p1 = new ColorPicker();
        cp_p1.setOnAction(event ->
        {
            int g = (int)(cp_p1.getValue().getGreen()*255);
            int r = (int)(cp_p1.getValue().getRed()*255);
            int b = (int)(cp_p1.getValue().getBlue()*255);

            this.frame.contentPane.setStyle(String.format("-coin-p1-color: rgb(%d, %d, %d);", r, g, b));
        });

        hBoxes[2] = new HBox();
        hBoxes[2].getChildren().addAll(new Label("Spieler 1: "), cp_p1);

        ColorPicker cp_p2 = new ColorPicker();
        cp_p2.setOnAction(event ->
        {
            int g = (int)(cp_p2.getValue().getGreen()*255);
            int r = (int)(cp_p2.getValue().getRed()*255);
            int b = (int)(cp_p2.getValue().getBlue()*255);

            this.frame.contentPane.setStyle(String.format("-coin-p2-color: rgb(%d, %d, %d);", r, g, b));
        });

        hBoxes[3] = new HBox();
        hBoxes[3].getChildren().addAll(new Label("Spieler 2: "), cp_p2);


        box.getChildren().addAll(hBoxes[0], hBoxes[1], hBoxes[2], hBoxes[3]);

        stage.setScene(scene);
        stage.show();
    }
}
