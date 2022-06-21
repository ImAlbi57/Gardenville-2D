package it.unibs.pajc.salm2d;

import it.unibs.pajc.salm2d.Coords;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Objects;

// ClientHandler class
public class ClientHandler implements Runnable {
    // socket ricezione messaggio client
    Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private int ID;
    private boolean isAlive;


    //Costruttore
    ClientHandler(Socket socket, int ID) {
        this.ID = ID;
        this.socket = socket;
        isAlive = true;
    }

    @Override
    public void run() {
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println(ID);
            out.flush();

            while(in.readLine() != null){
                Coords c = new Coords(in.readLine());
                System.out.println("(x, y): " + c);
            }
        } catch (IOException e) {
            System.out.println("Utente " + ID + " disconnesso");
            isAlive = false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientHandler that = (ClientHandler) o;
        return this.ID == that.ID;
    }

    public BufferedReader getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public int getID() {
        return ID;
    }

    public boolean isAlive() {
        return isAlive;
    }
}