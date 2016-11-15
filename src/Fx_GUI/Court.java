package Fx_GUI;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;


public class Court extends GridPane
{
    private Cell[][] cells;
    private int x;

    public Court(int x, double width, double height)
    {
        this.getStylesheets().add("Fx_GUI/Style.css");
        this.getStyleClass().add("bevelborder");
        this.getStyleClass().add("court");
        this.setPadding(new Insets(5));
        this.x = x;

        this.setHgap(5);
        this.setVgap(5);

        double size;
        if(width < height)
            size = width*0.8;
        else
            size = height*0.8;

        double cellsize = ((size - 40.) / x) - 5;

        this.setMaxSize(size, size);
        this.setPrefSize(size, size);

        this.cells = new Cell[x][x];

        for (int row = 0; row < x; row++) {
            for (int col = 0; col < x; col++) {
                this.cells[row][col] = new Cell(cellsize);
                this.add(cells[row][col],col, row);
                this.cells[row][col].setPrefSize((size)/x, (size)/x);
            }
        }

    }

    public void setMouseListener(int row, int col, EventHandler<MouseEvent> eh)
    {
        cells[row][col].setOnMouseReleased(eh);
    }

    public void modifyCell(int row, int col, boolean possible, boolean p1, boolean p2, boolean in_charge)
    {
        cells[row][col].setStats(possible, p1, p2, in_charge);
    }

    public void changePreview()
    {
        for (int row = 0; row < x; row++) {
            for (int col = 0; col < x; col++) {
                cells[row][col].changePreview();
            }

        }
    }

    public void wrongTurn(int row, int col)
    {
        cells[row][col].wrong_turn();
    }

    void resizes(double size)
    {
        double cellsize = ((size - 40.) / x) - 5;

        this.setMaxSize(size, size);
        this.setPrefSize(size, size);

        for (int row = 0; row < x; row++) {
            for (int col = 0; col < x; col++) {
                cells[row][col].resizes(cellsize);
            }

        }
    }

}
