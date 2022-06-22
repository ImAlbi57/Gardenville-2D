package it.unibs.pajc.salm2d;

import javax.swing.*;
import javax.swing.Timer;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

// Client class
public class Client {

    private static int port;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private static int ID;

    public Client(int port){
        this.port = port;
        try {
            socket = new Socket("localhost", port);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Utente connesso");
    }

    public static void main(String[] args){
        port = Integer.parseInt(JOptionPane.showInputDialog("Inserisci porta server:", 1234));
        Client client = new Client(port);
        client.start();
    }


    public void start(){
        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            ID = Integer.parseInt(in.readLine());
            //if (ID == -1) {
            //    System.out.println("Numero massimo di utenti raggiunto");
            //    return;
            //}
            System.out.println("Benvenuto Utente: " + ID);

            WhiteBoard wb = new WhiteBoard();

            Thread t1 = new Thread(() -> clientToClientHandler(wb));
            Thread t2 = new Thread(() -> clientHandlerToClient());
            t1.start();
            t2.start();

            //in.close();
            //out.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void clientToClientHandler(WhiteBoard wb) {
        while(true){
            Coords c = wb.getCoords();
            Direction d = wb.getDirection();
            out.println(ID + ":" + d + ":" + c.toString());
            out.flush();
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
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
    }
}

