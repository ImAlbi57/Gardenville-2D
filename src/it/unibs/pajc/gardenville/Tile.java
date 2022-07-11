package it.unibs.pajc.gardenville;

import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage img;
    public final boolean isCollidable;

    public Tile(BufferedImage img, boolean isCollidable) {
        this.img = img;
        this.isCollidable = isCollidable;
    }

    public BufferedImage getImg() {
        return img;
    }
}
