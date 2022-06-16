package it.unibs.pajc.salm2d;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
  
// Client class
public class Client {

    static int port;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    // driver code
    public static void main(String[] args)
    {

        port = Integer.parseInt(JOptionPane.showInputDialog("Inserisci porta server:", 1234));
        try (Socket socket = new Socket("localhost", port)) {
            System.out.println("Utente connesso");

            WhiteBoard wb = new WhiteBoard();

            // Le 3 righe seguenti sono utilizzate per ricevere e stampare il messaggio del server
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println(reader.readLine());

        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

