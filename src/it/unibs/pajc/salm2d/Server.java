package it.unibs.pajc.salm2d;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

// Server class
public class Server {

    private ArrayList<ClientHandler> users;
    private int port;
    private boolean connection;


    public Server(int port){
        this.port = port;
        users = new ArrayList<>();
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


    //Se multiplayer cambiare porta in 8080
    public static void main(String[] args)
    {
        int port = 1234;
        Server server = new Server(port);
        server.start();
    }

}
