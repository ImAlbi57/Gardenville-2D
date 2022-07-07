package it.unibs.pajc.salm2d;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class NpcData{
    private static final int DOWN_LEFT = 0;
    private static final int DOWN_RIGHT = 1;
    private static final int LEFT_1 = 2;
    private static final int LEFT_2 = 3;
    private static final int RIGHT_1 = 4;
    private static final int RIGHT_2 = 5;
    private static final int UP_LEFT = 6;
    private static final int UP_RIGHT = 7;

    private transient BufferedImage[] skin;
    private String name;
    private Path path;

    public NpcData(String name, Coords[] ccs) {
        this.name = name;
        this.path = new Path(ccs);
        this.skin = new BufferedImage[30];
        setSkinImages();
    }

    public void setSkinImages() {
        try {
            this.skin[DOWN_LEFT] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/npc/oldman_down_1.png")));
            this.skin[DOWN_RIGHT] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/npc/oldman_down_2.png")));
            this.skin[LEFT_1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/npc/oldman_left_1.png")));
            this.skin[LEFT_2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/npc/oldman_left_2.png")));
            this.skin[RIGHT_1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/npc/oldman_right_1.png")));
            this.skin[RIGHT_2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/npc/oldman_right_2.png")));
            this.skin[UP_LEFT] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/npc/oldman_up_1.png")));
            this.skin[UP_RIGHT] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/sprites/npc/oldman_up_2.png")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateCoords(){
        path.stepNext();
    }

    public BufferedImage getSkinImage(int i){
        return skin[i];
    }

    public Coords getCoords() {
        return path.getCoords();
    }

    public Direction getDirection(){
        return path.getDirection();
    }

    public String getName() {
        return name;
    }
}
