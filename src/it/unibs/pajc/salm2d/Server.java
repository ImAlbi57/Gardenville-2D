package it.unibs.pajc.salm2d;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {

        int port = 1234;

        System.out.println("Avvio del server....");

        try(
                ServerSocket server = new ServerSocket(port)
        ){

            while(true) {
                Socket client = server.accept();
                Protocol p = new Protocol(client);
                Thread clientThread = new Thread(p);
                clientThread.start();
            }


        } catch(IOException ex) {
            System.err.println("Errore di comunicazione: " + ex);
        }

        System.out.println("exit....");

    }

}