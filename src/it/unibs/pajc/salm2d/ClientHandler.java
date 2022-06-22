package it.unibs.pajc.salm2d;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

// ClientHandler class
public class ClientHandler implements Runnable {
    // socket ricezione messaggio client
    Socket socket;
    private static ArrayList<ClientHandler> clientList = new ArrayList<>();
    private BufferedReader in;
    private PrintWriter out;
    private int ID;
    private boolean isAlive;


    //Costruttore
    ClientHandler(Socket socket, int ID) {
        this.ID = ID;
        this.socket = socket;
        isAlive = true;
        clientList.add(this);
    }

    @Override
    public void run() {
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println(ID);
            out.flush();

            Coords c = new Coords();
            Direction d;
            String msg;
            while( (msg = in.readLine()) != null){
                String parts[] = msg.split(":");
                int idMsg = Integer.parseInt(parts[0]);
                d = Direction.valueOf(parts[1]);
                c.update(parts[2]);

                //Messaggio ricevuto dal proprio client -> mando agli altri handler
                if(this.ID == idMsg){
                    sendToOthers(this, msg);
                    System.out.println(ID + "#: " + c + " -> " + d);
                }
                //Messaggio ricevuto da un altro clientHandler -> mando al client
                else{
                    out.println(msg);
                }
            }
        } catch (IOException e) {
            System.out.println("Utente " + ID + " disconnesso");
            isAlive = false;
        }
    }

    protected void sendToAll(ClientHandler sender, String message) {
        clientList.forEach(c -> c.sendMessage(sender, message));
    }

    protected void sendToOthers(ClientHandler sender, String message) {
        clientList.stream().filter(ch -> !ch.equals(sender)).forEach(c -> sendMessage(c, message));
    }

    protected void sendMessage(ClientHandler destination, String message) {
        try {
            OutputStream os = destination.getSocket().getOutputStream();
            PrintWriter toDestination = new PrintWriter(os, true);
            toDestination.println(message);
            this.out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    public Socket getSocket() {
        return socket;
    }

    public boolean isAlive() {
        return isAlive;
    }
}