package it.unibs.pajc.salm2d;

import javax.swing.*;

public class WhiteBoard {
    public JFrame frame;
    private PaintArea paint;
    private MapManager mm;


    /**
     * Create the application.
     */
    public WhiteBoard() {
        mm = new MapManager();
        paint = new PaintArea(mm);
        mapInit();
        initialize();
    }

    private void mapInit() {
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

    public void updateClientData(int idClient, Coords coords) {
        paint.updateClientData(idClient, coords);
    }

    public Coords getCoords(){
        return new Coords(paint.getWr(), paint.getHr());
    }

    public Direction getDirection(){
        return paint.getDirection();
    }



}
