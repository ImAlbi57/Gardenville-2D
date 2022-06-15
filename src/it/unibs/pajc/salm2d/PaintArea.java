package it.unibs.pajc.salm2d;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class PaintArea extends JComponent implements KeyListener {

    private int h;
    private int w;
    private int wr = 0;
    private int hr = 0;

    public PaintArea() {
        Timer t = new Timer(10, (e) -> {
            updateCoords();
            repaint();
        });
        t.start();
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);
    }



    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        updateCoords();

        w = getWidth();
        h = getHeight();
        //System.out.println(w + " " + " " +h);

        g2.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(2f));
        g2.translate(0, h); // Traslo il punto di origine di 0 punti in orizzontale e 400 punti in verticale
        g2.scale(1, -1);
        g2.translate(w/2, h/2);

        g2.drawOval(wr,hr,50,50);

    }

    int delta = 1;
    private void updateCoords() {
        if(moveUp)
            setHr(getHr()+delta);
        if(moveDown)
            setHr(getHr()-delta);
        if(moveRight)
            setWr(getWr()+delta);
        if(moveLeft)
            setWr(getWr()-delta);
    }

    boolean moveUp = false, moveDown = false, moveLeft = false, moveRight = false;
    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_W)
            moveUp = true;
        if (e.getKeyCode() == KeyEvent.VK_S)
            moveDown = true;
        if(e.getKeyCode() == KeyEvent.VK_A)
            moveLeft = true;
        if (e.getKeyCode() == KeyEvent.VK_D)
            moveRight = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W)
            moveUp = false;
        if (e.getKeyCode() == KeyEvent.VK_S)
            moveDown = false;
        if(e.getKeyCode() == KeyEvent.VK_A)
            moveLeft = false;
        if (e.getKeyCode() == KeyEvent.VK_D)
            moveRight = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // NON UTILIZZARE
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
