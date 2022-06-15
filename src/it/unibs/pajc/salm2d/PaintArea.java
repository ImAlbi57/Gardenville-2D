package it.unibs.pajc.salm2d;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class PaintArea extends JComponent implements KeyListener {

    private int h;
    private int w;
    private int wr = 200;
    private int hr = 200;

    public PaintArea() {
        addKeyListener(this);
    }



    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        w = getWidth();
        h = getHeight();
        System.out.println(w + " " + " " +h);

        g2.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(2f));
        g2.translate(0, h); // Traslo il punto di origine di 0 punti in orizzontale e 400 punti in verticale
        g2.scale(1, -1);
        g2.translate(w/2, h/2);
        g2.drawRect(0,0,10,10);

    }



    @Override
    /*
    87 -> w
    83 -> s
    65 -> a
    68 -> d
     */
    public void keyTyped(KeyEvent e) {
        String keyType;
        System.out.println(e.getKeyCode());
        if(e.getKeyCode() == 87){
            hr = getHr() + 10;
        } else if (e.getKeyCode() == 83){
            hr = getHr() - 10;
        } else if(e.getKeyCode() == 65){
            wr = getWr() - 10;
        } else if (e.getKeyCode() == 68) {
            wr = getWr() + 10;
        }
        this.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public int getWr() {
        return wr;
    }

    public void setWr(int wr) {
        this.wr = wr;
    }

    public int getHr() {
        return hr;
    }

    public void setHr(int hr) {
        this.hr = hr;
    }
}
