package it.unibs.pajc.salm2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.HashMap;


public class PaintArea extends JComponent implements KeyListener{

    private static final Coords scale = new Coords(1600, 900);
    private static final Font font = new Font("TimesRoman", Font.PLAIN, 30);

    private ClientData myClientData;
    private ArrayList<NpcData> npcData;
    private final MapManager mm;
    private HashMap<Integer,ClientData> otherClientData;
    private int halfTimeCounter, skinCounterPlayer, skinCounterNpc;
    private CollisionChecker cCheck;
    String talkingNpc;
    Sound sound = new Sound();
    Timer t, t1, t2;


    public PaintArea(MapManager mm, ClientData cd) {
        this.mm = mm;
        this.myClientData = cd;
        this.npcData = new ArrayList<>();
        initNPCs();
        this.otherClientData = new HashMap<>();
        this.cCheck = new CollisionChecker(this.mm, myClientData);
        halfTimeCounter = 0;
        skinCounterPlayer = 0;
        skinCounterNpc = 0;
        talkingNpc = "";
        playMusicLoop(Sound.EXTERNALSOUND);

        t = new Timer(10, (e) -> {
            checkCollision();
            updateCoords();
            repaint();
        });
        t.start();

        t1 = new Timer(250, (e) -> {
            skinCounterNpc = 1 - skinCounterNpc;
            removeDeadPlayer();
            checkStamina();
            if(isMoving() && !isInPause){
                updateTimeCounter();
                if(myClientData.getSpeed() == 5 || halfTimeCounter == 0){
                    updateSkinMovement();
                    startSoundWalking();
                }

            }
        });
        t1.start();

        t2 = new Timer(20, (e) -> updateNpcMovement());
        t2.start();

        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);
    }

    private void updateNpcMovement() {
        for (NpcData npc : npcData) {
            npc.updateCoords();
        }
    }

    private void initNPCs() {
        npcData.add(new NpcData("Merlino", new Coords[]{new Coords(2725, 2970), new Coords(2795, 2970), new Coords(2795, 2900), new Coords(2725, 2900)}));
        npcData.add(new NpcData("Gandalf", new Coords[]{new Coords(4886, 5420), new Coords(5066, 5420), new Coords(5066, 5110), new Coords(4886, 5110)}));
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

        g2.setStroke(new BasicStroke(3f));

        mm.drawMap(g2, -myClientData.getCoords().getX(), -myClientData.getCoords().getY());

        g2.setColor(Color.RED);
        for (ClientData otherCd : otherClientData.values()) {
            int relX = otherCd.getCoords().getX() - myClientData.getCoords().getX();
            int relY = otherCd.getCoords().getY() - myClientData.getCoords().getY();
            Coords relativePos = new Coords(relX, relY);
            drawPlayer(g2, otherCd, relativePos, 1);
        }


        boolean up = keyControl.contains(""+KeyEvent.VK_W);
        boolean down = keyControl.contains(""+KeyEvent.VK_S);
        boolean left = keyControl.contains(""+KeyEvent.VK_A);
        boolean right = keyControl.contains(""+KeyEvent.VK_D);
        myClientData.updateDirection(up, left, down, right);
        System.out.println("Coords: " + myClientData.getCoords());

        //player hitbox
        //g2.drawRect(myClientData.solidArea.x, myClientData.solidArea.y, myClientData.solidArea.width, myClientData.solidArea.height);

        for (NpcData npc : npcData) {
            int relX = npc.getCoords().getX() - myClientData.getCoords().getX();
            int relY = npc.getCoords().getY() - myClientData.getCoords().getY();
            Coords relativePos = new Coords(relX, relY);
            drawNpc(g2, npc, relativePos);
        }

        drawPlayer(g2, myClientData, Coords.ZERO, 0);

        if(isInPause){
            printPauseMenu(g2);
        }

        printHUDPlayer(g2);

        if(cCheck.getNoKeyCollision()){
            printNoKeyDialog(g2);
        }

        if(cCheck.getChestCollision()){
            printWin(g2);
            t.stop();
            t1.stop();
            t2.stop();
        }

        if(!talkingNpc.equals("")){
            printDialogNPC(g2, talkingNpc);
        }

        if(isInPause){
            printPauseMenu(g2);
        }
    }

    private void checkCollisionNPC(){
        Coords myCoords = myClientData.getCoords();
        for(NpcData npc : npcData){
            if(npc.getCoords().isNear(myCoords)){
                talkingNpc = npc.getName();
            }
        }
    }

    private void printPauseMenu(Graphics2D g2){
        Font f = new Font("SansSerif", Font.BOLD, 150);
        g2.setColor(new Color(0, 0, 0, 150));
        g2.setFont(f);
        String state = "PAUSA";
        g2.fillRect(-scale.getX()/2, -scale.getY()/2, scale.getX()+100, scale.getY());
        g2.setColor(Color.WHITE);
        g2.drawString(state, -225, 75);


        //g2.fillRect(-225, 150, 200, 80);
        //Font f1 = new Font("SansSerif", Font.BOLD, 50);
        //g2.setFont(f1);
        //g2.setColor(Color.BLACK);
        //String quit = "QUIT";
        //g2.drawString(quit, -190, 210);

        //Rendere quit utilizzabile
    }

    private void printDialogNPC(Graphics2D g2, String talkingNpc) {
        String npcDialog1, npcDialog2, npcDialog3, npcDialog4;
        //Blur
        Font f1 = new Font("SansSerif", Font.BOLD, 27);
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(-scale.getX()/2, -scale.getY()/2, scale.getX()+100, scale.getY());
        g2.setColor(new Color(238, 150, 16, 255));
        //BackRect
        RoundRectangle2D rectHud2 = new RoundRectangle2D.Float(-360, -185, 820, 320, 25, 25);
        g2.fill(rectHud2);
        //Rect
        g2.setColor(new Color(235, 213, 190, 255));
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(-350, -175, 800, 300, 25, 25);
        g2.fill(roundedRectangle);
        //String
        g2.setFont(font);
        g2.setColor(Color.BLACK);
        if(talkingNpc.equals("Merlino")){
            npcDialog1 = "Benvenuto " + myClientData.getName();
            npcDialog2 = "Il tuo obiettivo sarà quello di trovare abbastanza";
            npcDialog3 = "chiavi per aprire tutte le porte";
            npcDialog4 = "Trova il secondo mago che ti aiuterà a superare le insidie";
        }
        else {
            npcDialog1 = "Ciao " + myClientData.getName();
            npcDialog2 = "Il mio consiglio è di controllare le porte";
            npcDialog3 = "in quanto alcune sono ingannevoli!";
            npcDialog4 = "Saranno contrassegnate dalle iniziali dei creatori!";
        }
        drawCenteredString(g2 , npcDialog1, new Rectangle(-350, -250, 800, 300), f1);
        drawCenteredString(g2 , npcDialog2, new Rectangle(-350, -200, 800, 300), f1);
        drawCenteredString(g2 , npcDialog3, new Rectangle(-350, -150, 800, 300), f1);
        drawCenteredString(g2 , npcDialog4, new Rectangle(-350, -100, 800, 300), f1);
    }

    private void printNoKeyDialog(Graphics2D g2){
        if(cCheck.getNoKeyCollision()){
            //Font
            Font f1 = new Font("SansSerif", Font.BOLD, 25);
            //Blur
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRect(-scale.getX()/2, -scale.getY()/2, scale.getX()+100, scale.getY());
            //Rect
            g2.setColor(new Color(250, 250, 250, 255));
            RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(-350, -175, 700, 350, 25, 25);
            g2.fill(roundedRectangle);
            //String
            g2.setFont(f1);
            g2.setColor(Color.BLACK);
            String noKeyString1 = "Non disponi della chiave per aprire questa porta!";
            String noKeyString2 = "Cercala per la mappa e riprova";
            drawCenteredString(g2 , noKeyString1, new Rectangle(-350, -225, 700, 350), f1);
            drawCenteredString(g2 , noKeyString2, new Rectangle(-350, -125, 700, 350), f1);

        }
    }

    private void printHUDPlayer(Graphics2D g2){
        String[] info = new String[10];
        info[0] = "STATISTICHE";
        info[1] = ""+mm.numKeys;
        info[2] = ""+cCheck.openDoor;
        info[3] = "Stamina";
        g2.setColor(new Color(255, 255, 255, 200));
        RoundRectangle2D rectHud2 = new RoundRectangle2D.Float(-scale.getX()/2f + 10, -scale.getY()/2f + 10, 270, 200, 25, 25);
        g2.fill(rectHud2);
        g2.setColor(new Color(0, 0, 0, 200));
        RoundRectangle2D rectHud = new RoundRectangle2D.Float(-scale.getX()/2f + 20, -scale.getY()/2f + 20, 250, 180, 25, 25);
        g2.fill(rectHud);

        g2.setColor(Color.WHITE);
        drawCenteredString(g2 , info[0], new Rectangle(-scale.getX()/2 , -scale.getY()/2 + 40, 250, 10) , font);
        drawCenteredString(g2 , "Chiavi: " + info[1] + "/" + mm.counterKey, new Rectangle(-scale.getX()/2 - 24, -scale.getY()/2 + 100, 250, 10) , font);
        drawCenteredString(g2 , "Porte Aperte: " + info[2] + "/" + mm.counterDoor, new Rectangle(-scale.getX()/2 + 18, -scale.getY()/2 + 140, 250, 10) , font);
        g2.setColor(new Color(30, 97, 252, 200));
        g2.fillRect(-518, 350, 11*myClientData.getStamina(), 30);
        g2.setColor(Color.WHITE);
        drawCenteredString(g2 , info[3], new Rectangle(-518, 350, 1100, 28) , font);
        g2.drawRect(-518, 350, 1100, 30);
    }

    private void printWin(Graphics2D g2){
        Font f = new Font("SansSerif", Font.BOLD, 150);
        g2.setColor(new Color(0, 0, 0, 150));
        g2.setFont(f);
        String state = "Hai vinto!";
        g2.fillRect(-scale.getX()/2, -scale.getY()/2, scale.getX()+100, scale.getY());
        g2.setColor(Color.WHITE);
        g2.drawString(state, -300, 75);
    }

    public void drawCenteredString(Graphics2D g2, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g2.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g2.setFont(font);
        // Draw the String
        g2.drawString(text, x, y);
    }

    private void drawPlayer(Graphics2D g2, ClientData cd, Coords pos, int playerType) {
        String nickname = cd.getName();
        g2.setColor(Color.BLUE);

        drawCenteredString(g2, nickname, new Rectangle(pos.getX(), pos.getY()-30, 60, 20), font);

        switch (cd.getDirection()) {
            case S -> drawSkinSprite(pos, g2, 0 + playerType*8 + skinCounterPlayer, 0);
            case W -> drawSkinSprite(pos, g2, 2 + playerType*8 + skinCounterPlayer, 0);
            case E -> drawSkinSprite(pos, g2, 4 + playerType*8 + skinCounterPlayer, 0);
            case N -> drawSkinSprite(pos, g2, 6 + playerType*8 + skinCounterPlayer, 0);
            case SE, SW -> drawSkinSprite(pos, g2, 0 + playerType*8 + skinCounterPlayer, 1);
            case NE, NW -> drawSkinSprite(pos, g2, 6 + playerType*8 + skinCounterPlayer, 1);
        }
    }

    private void drawNpc(Graphics2D g2, NpcData npc, Coords pos){
        String nickname = npc.getName();
        g2.setColor(Color.WHITE);

        drawCenteredString(g2, nickname, new Rectangle(pos.getX(), pos.getY()-30, 60, 20), font);

        int i;
        switch (npc.getDirection()) {
            case S -> i=0;
            case W -> i=2;
            case E -> i=4;
            case N -> i=6;
            default -> {System.out.println("MOVIMENTO NON PREVISTO"); i=0;}
        }
        g2.drawImage(npc.getSkinImage(i+ skinCounterNpc), pos.getX(), pos.getY(), 60, 60, null);
    }


    private void drawSkinSprite(Coords c, Graphics2D g2, int index, int diagonalMovement) {
        g2.drawImage(myClientData.getSkinImage(index), c.getX() + diagonalMovement*5, c.getY(), 60 - diagonalMovement*10, 60, null);
    }

    private void updateCoords() {
        if(isInPause) return;
        if(cCheck.getChestCollision()) return;

        if(keyControl.contains(""+KeyEvent.VK_W) && myClientData.isMovementAvailable(ClientData.MOVEMENT_W))
            myClientData.moveY(-1);
        if(keyControl.contains(""+KeyEvent.VK_A) && myClientData.isMovementAvailable(ClientData.MOVEMENT_A))
            myClientData.moveX(-1);
        if(keyControl.contains(""+KeyEvent.VK_S) && myClientData.isMovementAvailable(ClientData.MOVEMENT_S))
            myClientData.moveY(1);
        if(keyControl.contains(""+KeyEvent.VK_D) && myClientData.isMovementAvailable(ClientData.MOVEMENT_D))
            myClientData.moveX(1);
        if(keyControl.contains(""+KeyEvent.VK_SHIFT) && myClientData.getStamina() == 0)
            myClientData.setSpeed(2);
    }

    private void checkStamina(){
        if(isMoving() && keyControl.contains(""+KeyEvent.VK_SHIFT)){
            if(myClientData.getStamina() >= 1)
                myClientData.setStamina(myClientData.getStamina() - 1);
        }
        else{
            myClientData.setStamina(Math.min(100, myClientData.getStamina() + 2));
        }
    }

    private void updateTimeCounter() {
        halfTimeCounter = 1 - halfTimeCounter;
    }

    private void updateSkinMovement(){
        skinCounterPlayer = 1 - skinCounterPlayer;
    }

    private void checkCollision(){
        cCheck.checkTileCollision();
    }

    public ArrayList<String> keyControl = new ArrayList<>();
    private boolean isInPause = false;
    @Override
    public synchronized void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SHIFT)
            myClientData.setSpeed(5);

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            isInPause = !isInPause;

        if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_E)
            cCheck.resetNoKeyCollision();

        if(e.getKeyCode() == KeyEvent.VK_E){
            if(!talkingNpc.equals(""))
                talkingNpc = "";
            else checkCollisionNPC();
        }

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