package it.unibs.pajc.salm2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PaintArea extends JComponent implements KeyListener {

    private int h;
    private int w;
    private int wr = 0;
    private int hr = 0;
    private MapManager mm;
    private HashMap<Integer,Coords> clientData;

    public PaintArea(MapManager mm) {
        this.mm = mm;
        clientData = new HashMap<>();

        Timer t = new Timer(10, (e) -> {
            updateCoords();
            repaint();
        });
        t.start();
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);
    }

    public void updateClientData(int idClient, Coords coords){
        clientData.put(idClient, coords);
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        updateCoords();

        w = getWidth();
        h = getHeight();

        g2.setStroke(new BasicStroke(2f));
        g2.translate(0, h); // Traslo il punto di origine di 0 punti in orizzontale e 400 punti in verticale
        g2.scale(1, -1);
        g2.translate(w/2, h/2);

        mm.drawMap(g2, -w/2, -h/2);

        g2.setColor(Color.blue);
        for (Map.Entry<Integer, Coords> cd : clientData.entrySet()) {
            g2.drawOval(cd.getValue().getX(),cd.getValue().getY(),40,40);
        }

        g2.setColor(Color.GREEN);
        g2.drawOval(wr,hr,40,40);

    }

    int delta = 1;
    private void updateCoords() {
        if(keyControl.contains(""+KeyEvent.VK_W))
            setHr(getHr()+delta);
        if(keyControl.contains(""+KeyEvent.VK_S))
            setHr(getHr()-delta);
        if(keyControl.contains(""+KeyEvent.VK_D))
            setWr(getWr()+delta);
        if(keyControl.contains(""+KeyEvent.VK_A))
            setWr(getWr()-delta);
    }

    ArrayList<String> keyControl = new ArrayList<>();
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SHIFT)
            delta = 5;

        if(!keyControl.contains(""+e.getKeyCode()))
            keyControl.add(""+e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SHIFT)
            delta = 1;

        keyControl.remove(""+e.getKeyCode());
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

    public Direction getDirection(){
        return Direction.getDirection(
                keyControl.contains(""+KeyEvent.VK_W),
                keyControl.contains(""+KeyEvent.VK_A),
                keyControl.contains(""+KeyEvent.VK_S),
                keyControl.contains(""+KeyEvent.VK_D)
        );
    }
}
