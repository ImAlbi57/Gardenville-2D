package it.unibs.pajc.salm2d;

import javax.swing.*;
import java.awt.*;

public class WhiteBoard {
    public JFrame frame;
    int x;
    int y;


    /**
     * Create the application.
     */
    public WhiteBoard() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        frame = new JFrame();
        frame.setBounds(100, 100, 750, 460);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PaintArea paint = new PaintArea();
        frame.addKeyListener(paint);
        frame.getContentPane().add(paint);
        frame.setVisible(true);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
