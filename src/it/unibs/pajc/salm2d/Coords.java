package it.unibs.pajc.salm2d;

import java.util.Objects;
import java.util.regex.Pattern;

public class Coords {
    private int x;
    private int y;

    //Metodo costruttore
    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //Metodo costruttore da stringa (stile toString)
    public Coords(String s){
        String cleaner = s.substring(1, s.length()-1).replaceAll("\\s+","");
        String parts[] = cleaner.split(";");
        this.x = Integer.parseInt(parts[0]);
        this.y = Integer.parseInt(parts[1]);
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