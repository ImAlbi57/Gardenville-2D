package it.unibs.pajc.salm2d;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

// Server class
public class Server {

    public static int ID;
    private final Socket clientSocket = null;
    private ArrayList<ClientHandler> users;
    private int port;
    private boolean connection;
    private BufferedReader in;
    private PrintWriter out;

    private int xPos;
    private int yPos;


    public Server(int port){
        this.port = port;
        users = new ArrayList<ClientHandler>();
    }



    public void start(){
        connection = true;
        try {
            ServerSocket server = new ServerSocket(port);
            server.setReuseAddress(true);

            int countUsers = users.size();
            System.out.println("Server in ascolto sulla porta: " + port);

            while (connection) {

                Socket client = server.accept();
                removeDeadUser();
                if(users.size() < 2){
                    ClientHandler handler = new ClientHandler(client, ++countUsers);
                    users.add(handler);

                    System.out.println("Utente " + countUsers + " connesso");

                    Thread clientThread = new Thread(handler);
                    clientThread.start();

                }
                else{
                    //out.println(-1);
                    client.close();
                }


            }

            try {
                server.close();
                for (int i = 0; i < users.size(); ++i) {
                    ClientHandler tc = users.get(i);
                    System.out.println(users.size());
                    try {
                        // Chiusura di DataStream
                        tc.getIn().close();
                        tc.getOut().close();
                        tc.socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeDeadUser(){
        System.out.println("Controllo utenti");
        for(int i = 0; i < users.size(); i++){
            if(!users.get(i).isAlive()){
                System.out.println("Utente " + users.get(i).getID() + " rimosso");
                users.remove(i);
                i--;
            }
        }

    }


    public static void main(String[] args)
    {
        int port = 1234;
        Server server = new Server(port);
        server.start();
    }

}
