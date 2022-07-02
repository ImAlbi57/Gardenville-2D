package it.unibs.pajc.salm2d;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
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

    public static final int MOVEMENT_W = 0;
    public static final int MOVEMENT_A = 1;
    public static final int MOVEMENT_S = 2;
    public static final int MOVEMENT_D = 3;


    private Coords coords;
    private String name;
    private Direction direction;
    private int speed;
    public Rectangle solidArea;
    private boolean[] availableMovements;
    private BufferedImage[] skin;


    public ClientData(Coords coords, String name) {
        this.coords = coords;
        this.name = name;
        this.direction = Direction.S;
        this.speed = 2;
        this.solidArea = new Rectangle();
        this.solidArea.x = 14;
        this.solidArea.y = 16;
        this.solidArea.width = 32;
        this.solidArea.height = 32;
        this.availableMovements = new boolean[4];
        this.skin = new BufferedImage[30];
        setSkinImages();
    }

    public void resetAvailableMovements(){
        for (int i = 0; i < 4; i++)
            this.availableMovements[i] = true;
    }

    public void setSkinImages() {
        try {
            this.skin[DOWN_LEFT] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/player/walking/boy_down_1.png")));
            this.skin[DOWN_RIGHT] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/player/walking/boy_down_2.png")));
            this.skin[LEFT_1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/player/walking/boy_left_1.png")));
            this.skin[LEFT_2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/player/walking/boy_left_2.png")));
            this.skin[RIGHT_1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/player/walking/boy_right_1.png")));
            this.skin[RIGHT_2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/player/walking/boy_right_2.png")));
            this.skin[UP_LEFT] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/player/walking/boy_up_1.png")));
            this.skin[UP_RIGHT] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/player/walking/boy_up_2.png")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public BufferedImage getSkinImage(int i){
       return skin[i];
    }

    public void updateDirection(boolean up, boolean down, boolean left, boolean right){
        this.direction = Direction.updateDirection(this.direction, up, down, left, right);
    }

    public boolean isMovementAvailable(int index){
        return this.availableMovements[index];
    }

    public void setMovement(int index, boolean status){
        this.availableMovements[index] = status;
    }

    public void moveX(int sign){
        coords.setX(coords.getX() + sign*speed);
    }

    public void moveY(int sign){
        coords.setY(coords.getY() + sign*speed);
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public Coords getCoords() {
        return coords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
