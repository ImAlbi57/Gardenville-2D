package it.unibs.pajc.salm2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PaintArea extends JComponent implements KeyListener {

    private int wr;
    private int hr;
    private ClientData clientDataPlayer;
    private final MapManager mm;
    private HashMap<Integer,Coords> clientData;
    private int skinCounter = 0;
    private int spriteCounter = 0;
    private CollisionCheckerTest cCheck = new CollisionCheckerTest(new MapManager());
    public Direction d = Direction.N;

    boolean[] statusMovement = new boolean[4];
    int delta;


    public PaintArea(MapManager mm) {
        this.mm = mm;
        wr = 2725;
        hr = 2971;
        delta = 2;
        clientData = new HashMap<>();
        clientDataPlayer = new ClientData(new Coords(0,0), "Paolooo");
        clientDataPlayer.collisionOn = false;

        Timer t = new Timer(10, (e) -> {
            checkCollision();
            updateCoords();
            repaint();
        });
        t.start();

        Timer t1 = new Timer(250, (e) -> {
            if(isMoving())
                updateSkinMovement();
        });
        t1.start();
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);
    }

    private boolean isMoving() {
        return
            keyControl.contains(""+KeyEvent.VK_W) ||
            keyControl.contains(""+KeyEvent.VK_S) ||
            keyControl.contains(""+KeyEvent.VK_A) ||
            keyControl.contains(""+KeyEvent.VK_D);
    }

    public void updateClientData(int idClient, Coords coords){
        clientData.put(idClient, coords);
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        Coords scale = new Coords(1600, 900);
        double s = Math.min(w /(double)scale.getX(), h /(double)scale.getY());

        g2.scale(s, s);
        g2.translate(scale.getX()/2., scale.getY()/2.);

        g2.setStroke(new BasicStroke(2f));

        mm.drawMap(g2, -wr, -hr);

        g2.setColor(Color.blue);
        for (Map.Entry<Integer, Coords> cd : clientData.entrySet()) {
            g2.drawOval(cd.getValue().getX(),cd.getValue().getY(),40,40);
        }

        g2.setColor(Color.GREEN);
        //g2.drawOval(0,0,40,40);

        boolean up, down, left, right;
        up = keyControl.contains(""+KeyEvent.VK_W);
        down = keyControl.contains(""+KeyEvent.VK_S);
        left = keyControl.contains(""+KeyEvent.VK_A);
        right = keyControl.contains(""+KeyEvent.VK_D);
        d = Direction.updateDirection(d, up, left, down, right);

        //player hitbox
        g2.drawRect(clientDataPlayer.solidArea.x, clientDataPlayer.solidArea.y, clientDataPlayer.solidArea.width, clientDataPlayer.solidArea.height);


        switch(d){
            case N:
                if (skinCounter == 0)
                    g2.drawImage(clientDataPlayer.getSkinImage(6), 0, 0, 60, 60, null);
                if (skinCounter == 1)
                    g2.drawImage(clientDataPlayer.getSkinImage(7), 0, 0, 60, 60, null);
                break;
            case S:
                if (skinCounter == 0)
                    g2.drawImage(clientDataPlayer.getSkinImage(0), 0, 0, 60, 60, null);
                if (skinCounter == 1)
                    g2.drawImage(clientDataPlayer.getSkinImage(1), 0, 0, 60, 60, null);
                break;
            case W:
                if (skinCounter == 0)
                    g2.drawImage(clientDataPlayer.getSkinImage(2), 0, 0, 60, 60, null);
                if (skinCounter == 1)
                    g2.drawImage(clientDataPlayer.getSkinImage(3), 0, 0, 60, 60, null);
                break;
            case E:
                if (skinCounter == 0)
                    g2.drawImage(clientDataPlayer.getSkinImage(4), 0, 0, 60, 60, null);
                if (skinCounter == 1)
                    g2.drawImage(clientDataPlayer.getSkinImage(5), 0, 0, 60, 60, null);
                break;
            case NE:
            case NW:
                if (skinCounter == 0)
                    g2.drawImage(clientDataPlayer.getSkinImage(6), 5, 0, 50, 60, null);
                if (skinCounter == 1)
                    g2.drawImage(clientDataPlayer.getSkinImage(7), 5, 0, 50, 60, null);
                break;
            case SE:
            case SW:
                if (skinCounter == 0)
                    g2.drawImage(clientDataPlayer.getSkinImage(0), 5, 0, 50, 60, null);
                if (skinCounter == 1)
                    g2.drawImage(clientDataPlayer.getSkinImage(1), 5, 0, 50, 60, null);
                break;
        }

    }


    private void updateCoords() {
        if(keyControl.contains(""+KeyEvent.VK_W) && statusMovement[0])
            setHr(getHr()-delta);
        if(keyControl.contains(""+KeyEvent.VK_A) && statusMovement[1])
            setWr(getWr()-delta);
        if(keyControl.contains(""+KeyEvent.VK_S) && statusMovement[2])
            setHr(getHr()+delta);
        if(keyControl.contains(""+KeyEvent.VK_D) && statusMovement[3])
            setWr(getWr()+delta);
    }

    private void updateSkinMovement(){
        skinCounter = 1 - skinCounter;
    }

    private boolean checkCollision(){
        cCheck.checkTileCollision(clientDataPlayer);
        return clientDataPlayer.collisionOn;
    }

    public ArrayList<String> keyControl = new ArrayList<>();
    @Override
    public synchronized void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SHIFT)
            delta = 5;

        if(!keyControl.contains(""+e.getKeyCode()))
            keyControl.add(""+e.getKeyCode());
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SHIFT)
            delta = 2;

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


public class CollisionCheckerTest {

    MapManager mm;

    public CollisionCheckerTest(MapManager mm) {
        this.mm = mm;
    }

    public void checkTileCollision(ClientData client) {

        int entityLeftWorldX = wr + client.solidArea.x;
        int entityRightWorldX = wr + client.solidArea.x + client.solidArea.width;
        int entityTopWorldY = hr + client.solidArea.y;
        int entityBottomWorldY = hr + client.solidArea.y + client.solidArea.height;

        int entityLeftCol = entityLeftWorldX / mm.tileDim;
        int entityRightCol = entityRightWorldX / mm.tileDim;
        int entityTopRow = entityTopWorldY / mm.tileDim;
        int entityBottomRow = entityBottomWorldY / mm.tileDim;

        int tileNum1, tileNum2;


        if(d != null) {
            statusMovement[0] = true; // W
            statusMovement[1] = true; // A
            statusMovement[2] = true; // S
            statusMovement[3] = true; // D
            if(d.equals(Direction.N) || d.equals(Direction.NE) || d.equals(Direction.NW)) {
                entityTopRow = (entityTopWorldY - delta) / mm.tileDim;
                tileNum1 = mm.mapTileNums[entityLeftCol][entityTopRow];
                tileNum2 = mm.mapTileNums[entityRightCol][entityTopRow];
                if (mm.tileList[tileNum1].isCollidable || mm.tileList[tileNum2].isCollidable) {
                    client.collisionOn = true;
                    statusMovement[0] = false;
                } else {
                    statusMovement[0] = true;
                }
            }
            if(d.equals(Direction.W) || d.equals(Direction.NW) || d.equals(Direction.SW)) {
                entityLeftCol = (entityLeftWorldX - delta) / mm.tileDim;
                tileNum1 = mm.mapTileNums[entityLeftCol][entityTopRow];
                tileNum2 = mm.mapTileNums[entityLeftCol][entityBottomRow];
                if (mm.tileList[tileNum1].isCollidable || mm.tileList[tileNum2].isCollidable) {
                    client.collisionOn = true;
                    statusMovement[1] = false;
                }
                else{
                    statusMovement[1] = true;
                }
            }
            if(d.equals(Direction.S) || d.equals(Direction.SE) || d.equals(Direction.SW) ){
                entityBottomRow = (entityBottomWorldY + delta) / mm.tileDim;
                tileNum1 = mm.mapTileNums[entityLeftCol][entityBottomRow];
                tileNum2 = mm.mapTileNums[entityRightCol][entityBottomRow];
                if (mm.tileList[tileNum1].isCollidable || mm.tileList[tileNum2].isCollidable) {
                    client.collisionOn = true;
                    statusMovement[2] = false;
                }
                else{
                    statusMovement[2] = true;
                }
            }
            if (d.equals(Direction.E) || d.equals(Direction.NE) ||d.equals(Direction.SE)) {
                entityRightCol = (entityRightWorldX + delta) / mm.tileDim;
                tileNum1 = mm.mapTileNums[entityRightCol][entityTopRow];
                tileNum2 = mm.mapTileNums[entityRightCol][entityBottomRow];
                if (mm.tileList[tileNum1].isCollidable || mm.tileList[tileNum2].isCollidable) {
                    client.collisionOn = true;
                    statusMovement[3] = false;
                }
                else{
                    statusMovement[3] = true;
                }
            }
        }
    }
}
}

