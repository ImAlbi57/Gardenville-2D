package it.unibs.pajc.salm2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.HashMap;


public class PaintArea extends JComponent implements KeyListener {

    private static final Coords scale = new Coords(1600, 900);
    private ClientData myClientData;
    private final MapManager mm;
    private HashMap<Integer,ClientData> otherClientData;
    private int halfTimeCounter;
    private int skinCounter;
    private CollisionChecker cCheck;
    Sound sound = new Sound();
    Timer t, t1;

    public PaintArea(MapManager mm, ClientData cd) {
        this.mm = mm;
        this.myClientData = cd;
        this.otherClientData = new HashMap<>();
        this.cCheck = new CollisionChecker(this.mm, myClientData);
        halfTimeCounter = 0;
        skinCounter = 0;
        playMusicLoop(Sound.EXTERNALSOUND);

        t = new Timer(10, (e) -> {
            checkCollision();
            updateCoords();
            repaint();
        });
        t.start();

        t1 = new Timer(250, (e) -> {
            removeDeadPlayer();
            if(isMoving() && !isInInventory){
                updateTimeCounter();
                if(myClientData.getSpeed() == 5 || halfTimeCounter == 0){
                    updateSkinMovement();
                    startSoundWalking();
                }

            }
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

    public void updateClientData(int idClient, ClientData cd){
        otherClientData.put(idClient, cd);
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        double s = Math.min(w /(double)scale.getX(), h /(double)scale.getY());

        g2.scale(s, s);
        g2.translate(scale.getX()/2., scale.getY()/2.);

        g2.setStroke(new BasicStroke(2f));

        mm.drawMap(g2, -myClientData.getCoords().getX(), -myClientData.getCoords().getY());

        g2.setColor(Color.RED);
        for (ClientData otherCd : otherClientData.values()) {
            int relX = otherCd.getCoords().getX() - myClientData.getCoords().getX();
            int relY = otherCd.getCoords().getY() - myClientData.getCoords().getY();
            Coords relativePos = new Coords(relX, relY);
            drawPlayer(g2, otherCd, relativePos, 1);
        }

        g2.setColor(Color.BLUE);

        boolean up = keyControl.contains(""+KeyEvent.VK_W);
        boolean down = keyControl.contains(""+KeyEvent.VK_S);
        boolean left = keyControl.contains(""+KeyEvent.VK_A);
        boolean right = keyControl.contains(""+KeyEvent.VK_D);
        myClientData.updateDirection(up, left, down, right);

        //player hitbox
        //g2.drawRect(myClientData.solidArea.x, myClientData.solidArea.y, myClientData.solidArea.width, myClientData.solidArea.height);

        drawPlayer(g2, myClientData, Coords.ZERO, 0);

        if(isInInventory){
            printInventory(g2);
        }
    }

    private void printInventory(Graphics2D g2) {
        //blur background
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(-scale.getX()/2, -scale.getY()/2, scale.getX(), scale.getY());

        //print inventory popup
        g2.setColor(new Color(250, 250, 250, 150));
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(-500, -350, 1000, 700, 50, 50);
        g2.fill(roundedRectangle);
    }

    private void drawPlayer(Graphics2D g2, ClientData cd, Coords pos, int playerType) {
        String nickname = cd.getName();
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g2.drawString(nickname, pos.getX() - nickname.length()*5, pos.getY() - 10);

        switch (cd.getDirection()) {
            case S -> drawSkinSprite(pos, g2, 0 + playerType*8, skinCounter, 0);
            case W -> drawSkinSprite(pos, g2, 2 + playerType*8, skinCounter, 0);
            case E -> drawSkinSprite(pos, g2, 4 + playerType*8, skinCounter, 0);
            case N -> drawSkinSprite(pos, g2, 6 + playerType*8, skinCounter, 0);
            case SE, SW -> drawSkinSprite(pos, g2, 0 + playerType*8, skinCounter, 1);
            case NE, NW -> drawSkinSprite(pos, g2, 6 + playerType*8, skinCounter, 1);
        }
    }


    private void drawSkinSprite(Coords c, Graphics2D g2, int index, int alternativeSkin, int diagonalMovement) {
        g2.drawImage(myClientData.getSkinImage(index + alternativeSkin), c.getX() + diagonalMovement*5, c.getY(), 60 - diagonalMovement*10, 60, null);
    }

    private void updateCoords() {
        if(isInInventory) return;

        if(keyControl.contains(""+KeyEvent.VK_W) && myClientData.isMovementAvailable(ClientData.MOVEMENT_W))
            myClientData.moveY(-1);
        if(keyControl.contains(""+KeyEvent.VK_A) && myClientData.isMovementAvailable(ClientData.MOVEMENT_A))
            myClientData.moveX(-1);
        if(keyControl.contains(""+KeyEvent.VK_S) && myClientData.isMovementAvailable(ClientData.MOVEMENT_S))
            myClientData.moveY(1);
        if(keyControl.contains(""+KeyEvent.VK_D) && myClientData.isMovementAvailable(ClientData.MOVEMENT_D))
            myClientData.moveX(1);

    }

    private void updateTimeCounter() {
        halfTimeCounter = 1 - halfTimeCounter;
    }

    private void updateSkinMovement(){
        skinCounter = 1 - skinCounter;
    }

    private void checkCollision(){
        cCheck.checkTileCollision(myClientData);

        //Richiamo per il metodo nella classe MapManager
        //cCheck.checkObjectCollision(myClientData);
    }

    public ArrayList<String> keyControl = new ArrayList<>();
    private boolean isInInventory = false;
    @Override
    public synchronized void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SHIFT)
            myClientData.setSpeed(5);

        if(e.getKeyCode() == KeyEvent.VK_E)
            isInInventory = !isInInventory;

        if(!keyControl.contains(""+e.getKeyCode()))
            keyControl.add(""+e.getKeyCode());


    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SHIFT)
            myClientData.setSpeed(2);

        keyControl.remove(""+e.getKeyCode());
        stopMusic();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public void resetKeyPressed(){
        keyControl.clear();
        myClientData.setSpeed(2);
    }

    private void startSoundWalking(){
        playMusic(Sound.WALKINGSOUND);
    }

    public void playMusic(int i){
        sound.setFile(i);
        sound.play();
    }

    public void playMusicLoop(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic(){
        sound.stop();
    }

    private void removeDeadPlayer(){
        for (ClientData cd : otherClientData.values()) {
            if(!cd.isAlive()){
                System.out.printf("Player %d rimosso\n", cd.getID());
                otherClientData.remove(cd.getID());
                return;
            }
        }
    }
}