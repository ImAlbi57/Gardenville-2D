package it.unibs.pajc.gardenville;

import java.io.Serializable;

public class Coords implements Serializable {
    public static final Coords ZERO = new Coords(0, 0);
    private int x;
    private int y;

    //Metodo costruttore
    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //Costruttore di copia
    public Coords(Coords coords) {
        this.x = coords.getX();
        this.y = coords.getY();
    }

    //Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    //Setters
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    //Metodo per verificare le interazioni
    public boolean isNear(Coords c) {
        int diff = Math.abs(this.getX()-c.getX()) + Math.abs(this.getY()-c.getY());
        return diff < 100;
    }

    //Metodo Equals, ritorna true se la x e la y corrispondono
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords c = (Coords) o;
        return this.x == c.getX() && this.y == c.getY();
    }

    @Override
    public String toString() {
        return "(" + x + " ; " + y + ")";
    }
}