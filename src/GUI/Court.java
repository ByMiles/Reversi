package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;


public class Court extends JPanel
{
    private Cell cells[][];
    private int x;

    public Court(int x, MouseListener ma, Color[] colors)
    {
        this.x = x;
        this.setLayout(new GridLayout(x, x, 5, 5));
        this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
        this.setBackground(new Color(47, 79, 79));

        cells = new Cell[x][x];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                cells[i][j] = new Cell(ma, colors);
                this.add(cells[i][j]);
            }
        }
    }

    public Cell getCell(int row, int col)
    {
        return cells[row][col];
    }

    public void modifyCell(int row, int col, boolean possible, boolean p1, boolean p2, boolean in_charge)
    {
        cells[row][col].setStats(possible, p1, p2, in_charge);
        this.revalidate();
    }

    public void changePreview()
    {
        for (int i = 0; i < this.x; i++)
        {
            for (int j = 0; j < this.x; j++)
            {
                cells[i][j].changePreview();
            }
        }
    }

    public void wrongTurn(int row, int col)
    {
        cells[row][col].wrongTurn();
    }

    void change_colors(Color[] colors)
    {
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.x; j++) {
                cells[i][j].change_colors(colors);
            }

        }
    }
}
