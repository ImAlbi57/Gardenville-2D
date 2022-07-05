package it.unibs.pajc.salm2d;

import java.awt.*;
import java.awt.image.BufferedImage;
/*
    Qua ho cercato di assegnare al tile relativo alla porta, una dimensione cubica per il controllo delle collisioni
    Come facevamo per le collisioni standard
 */

public class Tile {
    private BufferedImage img;
    private MapManager mm;
    public Rectangle solidArea = new Rectangle();
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    int x, y;
    public final boolean isCollidable;

    public Tile(BufferedImage img, boolean isCollidable) {
        this.img = img;
        this.isCollidable = isCollidable;
        /* -> */this.solidArea.x = 14;
        /* -> */this.solidArea.y = 16;
        /* -> */this.solidArea.width = 32;
        /* -> */this.solidArea.height = 32;
        /* -> */this.solidAreaDefaultX = solidArea.x;
        /* -> */this.solidAreaDefaultY = solidArea.y;
    }

    public BufferedImage getImg() {
        return img;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
