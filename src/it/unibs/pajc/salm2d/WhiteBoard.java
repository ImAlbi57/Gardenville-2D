package it.unibs.pajc.salm2d;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WhiteBoard {
    public JFrame frame;
    private final PaintArea paint;
    private final MapManager mm;


    /**
     * Create the application.
     */
    public WhiteBoard(ClientData cd) {
        this.mm = new MapManager();
        this.paint = new PaintArea(mm, cd);
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
        //1600x930
        frame.setBounds(100, 100, 800, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addKeyListener(paint);
        frame.getContentPane().add(paint);
        frame.setVisible(true);

        paint.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_F11){
                    frame.dispose();

                    if(frame.isUndecorated()){
                        frame.setUndecorated(false);
                        frame.setExtendedState(JFrame.NORMAL);
                        frame.setBounds(100, 100, 1600, 930);
                    }
                    else{
                        frame.setUndecorated(true);
                        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    }

                    frame.setVisible(true);
                }
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                paint.resetKeyPressed();
            }
        });

    }

    public void updateClientData(ClientData cd) {
        paint.updateClientData(cd.getID(), cd);
    }

}
