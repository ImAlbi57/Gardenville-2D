package it.unibs.pajc.gardenville;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

// Client class
public class Client {

    private ClientData cd;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private static int ID;
    private WhiteBoard wb;
    private static Homepage hp;

    public Client(int port){
        try {
            socket = new Socket("localhost", port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Utente connesso");
    }

    public Client(InetAddress ipAddress, int port){
        try {
            socket = new Socket(ipAddress, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Utente connesso");
    }

    public static void main(String[] args){
        hp = new Homepage();
        hp.setVisible(true);
    }


    public void start(){
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Object obj;
            while ((obj = in.readObject()) != null) {
                String str = (String) obj;
                if (str.startsWith("#")) {
                    ID = Integer.parseInt(str.substring(1));
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        String username = hp.getUsername();
        System.out.println("Benvenuto Utente: " + ID);

        cd = new ClientData(ID, new Coords(2975,2700), username);
        wb = new WhiteBoard(cd);


        Thread t1 = new Thread(this::clientToClientHandler);
        Thread t2 = new Thread(this::clientHandlerToClient);
        t1.start();
        t2.start();
    }

    private void clientToClientHandler() {
        while(true){
            try {
                this.out.writeObject(new ClientData(cd));
                this.out.flush();
                //System.out.println(cd.getCoords());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                TimeUnit.MILLISECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void clientHandlerToClient() {
        try {
            Object obj;
            while((obj = in.readObject()) != null) {
                updatePlayer( (ClientData) obj );
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePlayer(ClientData cd){
        wb.updateClientData(cd);
    }
}

