package it.unibs.pajc.salm2d;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;

    public void draw(Graphics2D g2, MapManager mm){
        g2.drawImage(image, worldX, worldY, mm.tileDim, mm.tileDim, null);
    }
}
