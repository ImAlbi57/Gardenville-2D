package it.unibs.pajc.salm2d;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

// Client class
public class Client {

    private static int port;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private static int ID;
    private WhiteBoard wb;
    private static Homepage hp;

    public Client(int port){
        this.port = port;
        try {
            socket = new Socket("localhost", port);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Utente connesso");
    }

    public Client(InetAddress ipAddress, int port){
        this.port = port;
        try {
            socket = new Socket(ipAddress, port);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Utente connesso");
    }

    public static void main(String[] args){
        hp = new Homepage();
        hp.setVisible(true);
    }


    public void start(){
        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String str;
            while((str = in.readLine() ) != null){
                if(str.startsWith("#")){
                    ID = Integer.parseInt(str.substring(1));
                    break;
                }
            }
            //if (ID == -1) {
            //    System.out.println("Numero massimo di utenti raggiunto");
            //    return;
            //}
            System.out.println("Benvenuto Utente: " + ID);

            wb = new WhiteBoard();

            Thread t1 = new Thread(this::clientToClientHandler);
            Thread t2 = new Thread(this::clientHandlerToClient);
            t1.start();
            t2.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void clientToClientHandler() {
        while(true){
            Coords c = wb.getCoords();
            Direction d = wb.getDirection();
            out.println(ID + ":" + d + ":" + c.toString());
            out.flush();
            try {
                TimeUnit.MILLISECONDS.sleep(15);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void clientHandlerToClient() {
        try {
            String message;
            while((message = in.readLine()) != null) {
                updatePlayer(message);
            }

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void updatePlayer(String message){
        String parts[] = message.split(":");
        int playerID = Integer.parseInt(parts[0]);
        Direction playerDirection = Direction.valueOf(parts[1]);
        Coords playerCoords = new Coords(parts[2]);
        System.out.println(message);

        wb.updateClientData(playerID, playerCoords);
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        Client.port = port;
    }
}

