package it.unibs.pajc.salm2d;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

public class ClientData implements Serializable {

    private static final int DOWN_LEFT = 0;
    private static final int DOWN_RIGHT = 1;
    private static final int LEFT_1 = 2;
    private static final int LEFT_2 = 3;
    private static final int RIGHT_1 = 4;
    private static final int RIGHT_2 = 5;
    private static final int UP_LEFT = 6;
    private static final int UP_RIGHT = 7;


    private Coords coords;
    private String name;
    private BufferedImage[] skin;
    private Direction direction;

    public ClientData(Coords coords, String name) {
        this.coords = coords;
        this.name = name;
        skin = new BufferedImage[30];
        setSkinImages();
    }

    public void setSkinImages() {
        try {
            skin[DOWN_LEFT] = ImageIO.read(getClass().getResourceAsStream("/res/sprites/player/walking/boy_down_1.png"));
            skin[DOWN_RIGHT] = ImageIO.read(getClass().getResourceAsStream("/res/sprites/player/walking/boy_down_2.png"));
            skin[LEFT_1] = ImageIO.read(getClass().getResourceAsStream("/res/sprites/player/walking/boy_left_1.png"));
            skin[LEFT_2] = ImageIO.read(getClass().getResourceAsStream("/res/sprites/player/walking/boy_left_2.png"));
            skin[RIGHT_1] = ImageIO.read(getClass().getResourceAsStream("/res/sprites/player/walking/boy_right_1.png"));
            skin[RIGHT_2] = ImageIO.read(getClass().getResourceAsStream("/res/sprites/player/walking/boy_right_2.png"));
            skin[UP_LEFT] = ImageIO.read(getClass().getResourceAsStream("/res/sprites/player/walking/boy_up_1.png"));
            skin[UP_RIGHT] = ImageIO.read(getClass().getResourceAsStream("/res/sprites/player/walking/boy_up_2.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public BufferedImage getSkinImage(int i){
       return skin[i];
    }

    public void updatePosition(Direction direction, Coords coords){
        this.direction = direction;
        this.coords = coords;
    }

    public Coords getCoords() {
        return coords;
    }

    public String getName() {
        return name;
    }


    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public void setName(String name) {
        this.name = name;
    }
}
