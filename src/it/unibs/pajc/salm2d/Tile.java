package it.unibs.pajc.salm2d;

import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage img;
    public boolean isCollidable;

    public Tile(BufferedImage img, boolean isCollidable) {
        this.img = img;
        this.isCollidable = isCollidable;
    }

    public BufferedImage getImg() {
        return img;
    }
}
