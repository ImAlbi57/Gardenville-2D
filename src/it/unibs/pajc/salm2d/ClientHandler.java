package it.unibs.pajc.salm2d;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

// ClientHandler class
public class ClientHandler implements Runnable {
    // socket ricezione messaggio client
    Socket socket;
    private static ArrayList<ClientHandler> clientList = new ArrayList<>();
    private ObjectInputStream in;
    private ObjectOutputStream out;
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
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();

            out.writeObject("#" + ID);
            out.flush();

            in = new ObjectInputStream(socket.getInputStream());

            Object obj;
            ClientData cd;
            while( (obj = in.readObject()) != null ){
                cd = (ClientData) obj;
                //System.out.println(cd.getCoords());

                //Messaggio ricevuto dal proprio client -> mando agli altri handler
                if(this.ID == cd.getID()){
                    sendToOthers(this, cd);
                    System.out.println(ID + "#: " + cd.getCoords()+ " -> " + cd.getDirection());
                }
                //Messaggio ricevuto da un altro clientHandler -> mando al client
                else{
                    out.writeObject(cd);
                }
            }
        } catch (IOException e) {
            System.out.println("Utente " + ID + " disconnesso");
            isAlive = false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void sendToAll(ClientHandler sender, ClientData cd) {
        clientList.forEach(c -> c.sendMessage(sender, cd));
    }

    protected void sendToOthers(ClientHandler sender, ClientData cd) {
        clientList.stream().filter(ch -> !ch.equals(sender)).forEach(c -> sendMessage(c, cd));
    }

    protected void sendMessage(ClientHandler destination, ClientData cd) {
        try {
            destination.getOut().writeObject(cd);
            destination.getOut().flush();
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

    public ObjectInputStream getIn() {
        return in;
    }

    public ObjectOutputStream getOut() {
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