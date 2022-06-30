package it.unibs.pajc.salm2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PaintArea extends JComponent implements KeyListener {

    private int wr = 0;
    private int hr = 0;
    private ClientData clientDataPlayer;
    private final MapManager mm;
    private HashMap<Integer,Coords> clientData;

    public PaintArea(MapManager mm) {
        this.mm = mm;
        clientData = new HashMap<>();
        clientDataPlayer = new ClientData(new Coords(0,0), "Paolooo");

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

        int w = getWidth();
        int h = getHeight();

        Coords scale = new Coords(1600, 900);
        double s = Math.min(w /(double)scale.getX(), h /(double)scale.getY());

        g2.scale(s, -s);
        g2.translate(scale.getX()/2., -scale.getY()/2.);

        g2.setStroke(new BasicStroke(2f));

        mm.drawMap(g2, -wr-500, hr-500);

        g2.setColor(Color.blue);
        for (Map.Entry<Integer, Coords> cd : clientData.entrySet()) {
            g2.drawOval(cd.getValue().getX(),cd.getValue().getY(),40,40);
        }

        g2.setColor(Color.GREEN);
        //g2.drawOval(0,0,40,40);
        if(keyControl.contains(""+KeyEvent.VK_W))
            g2.drawImage(clientDataPlayer.getSkinImage(6), 0,0 , 45,-45, null);
        else if(keyControl.contains(""+KeyEvent.VK_D))
            g2.drawImage(clientDataPlayer.getSkinImage(4), 0,0 , 45,-45, null);
        else if(keyControl.contains(""+KeyEvent.VK_A))
            g2.drawImage(clientDataPlayer.getSkinImage(2), 0,0 , 45,-45, null);
        else
            g2.drawImage(clientDataPlayer.getSkinImage(0), 0,0 , 45,-45, null);


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
