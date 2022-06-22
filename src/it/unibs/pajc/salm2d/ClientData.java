package it.unibs.pajc.salm2d;

import java.awt.*;
import java.io.Serializable;

public class ClientData implements Serializable {
    private Coords coords;
    private String name;
    private Image skin;
    private Direction direction;

    public ClientData(Coords coords, String name, Image skin) {
        this.coords = coords;
        this.name = name;
        this.skin = skin;
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
    public Image getSkin() {
        return skin;
    }
    public void setCoords(Coords coords) {
        this.coords = coords;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSkin(Image skin) {
        this.skin = skin;
    }
}
