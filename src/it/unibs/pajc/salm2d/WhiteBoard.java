package it.unibs.pajc.salm2d;

import javax.swing.*;

public class WhiteBoard {
    public JFrame frame;
    int x;
    int y;
    private PaintArea paint = new PaintArea();


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

        frame.addKeyListener(paint);
        frame.getContentPane().add(paint);
        frame.setVisible(true);

    }

    public Coords getCoords(){
        return new Coords(paint.getWr(), paint.getHr());
    }




}
