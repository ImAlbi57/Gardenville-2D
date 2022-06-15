package it.unibs.pajc.salm2d;

import javax.swing.*;
import java.awt.*;

public class WhiteBoard {
    public JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    WhiteBoard window = new WhiteBoard();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

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
        frame.getContentPane().add(paint);




    }
}
