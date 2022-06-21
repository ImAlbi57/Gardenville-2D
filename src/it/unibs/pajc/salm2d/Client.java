package it.unibs.pajc.salm2d;

import javax.swing.*;
import javax.swing.Timer;
import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

// Client class
public class Client {

    static int port;
    private BufferedReader in;
    private PrintWriter out;
    private static int ID;
    private int x;
    private int y;

    public Client(int port){
        this.port = port;
    }

    public static void main(String[] args){
        port = Integer.parseInt(JOptionPane.showInputDialog("Inserisci porta server:", 1234));
        Client client = new Client(port);
        client.start();
    }


    public void start(){
        try (Socket socket = new Socket("localhost", port)) {
            System.out.println("Utente connesso");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            ID = Integer.parseInt(in.readLine());
            //if (ID == -1) {
            //    System.out.println("Numero massimo di utenti raggiunto");
            //    return;
            //}
            System.out.println("Benvenuto Utente: " + ID);

            WhiteBoard wb = new WhiteBoard();

            while(wb.getCoords() != null){
                Coords c = wb.getCoords();
                out.println(c.toString());
                out.flush();
                TimeUnit.MILLISECONDS.sleep(500);
            }

            in.close();
            out.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

