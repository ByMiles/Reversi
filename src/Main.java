import Fx_GUI.Frame;
import javafx.application.Application;
import Reversi.Swing_Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Main
{
    public static void main(String[] args){

        JFrame start = new JFrame();
        start.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        start.setTitle("Oberfläche auswählen!");

        JButton swingbutton = new JButton("Swing-Style");
        swingbutton.addActionListener(e -> new Swing_Engine(1200, 800));

        JButton fxbutton = new JButton("JavaFx-Style");
        fxbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Application.launch(Frame.class, args);
            }
        });

        start.getContentPane().setLayout(new FlowLayout());
        start.getContentPane().add(swingbutton);
        start.getContentPane().add(fxbutton);

        start.pack();
        start.setVisible(true);

        //new Swing_Engine(1200, 800);
        //Application.launch(Frame.class, args);
    }
}
