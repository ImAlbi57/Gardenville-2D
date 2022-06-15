package it.unibs.pajc.salm2d;

import java.util.regex.Pattern;

public class Coords {
    private double x;
    private double y;

    //Metodo costruttore
    public Coords(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //Getters
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    //Setters
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }

    //Metodo Equals, ritorna true se la x e la y corrispondono
    public boolean equals(Coords c) {
        return this.x == c.getX() && this.y == c.getY();
    }

    //Verifica se una stringa è una valida coordinata
    public static boolean validateCoords(String str) {
        String[] parts = str.split(";");
        if(parts.length != 2)
            return false;
        //^[-+]?[0-9]+([\,|\.][0-9]+)?$ regex
        // [+-]? = possibili "+" e "-", [0-9]+ = 1+ corrispondenze, gruppo dopo il + opzionale per i decimali (con virgola o punto)
        return Pattern.matches("^[-+]?[0-9]+([\\,|\\.][0-9]+)?$",parts[0]) && Pattern.matches("^[-+]?[0-9]+([\\,|\\.][0-9]+)?$",parts[1]);
    }

    @Override
    public String toString() {
        return "(" + String.format("%.3f", x) + "; " + String.format("%.3f", y) + ")";
    }

}