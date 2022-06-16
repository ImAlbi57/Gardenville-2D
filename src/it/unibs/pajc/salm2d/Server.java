package it.unibs.pajc.salm2d;

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
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public Server(int port){
        this.port = port;
        users = new ArrayList<ClientHandler>();
        System.out.println(users.size());
    }



    public void start(){
        connection = true;
        try {
            ServerSocket server = new ServerSocket(port);
            server.setReuseAddress(true);

            System.out.println(users.size());

            while (connection) {
                System.out.println("Server in ascolto sulla porta: " + port);

                if(!connection) break;

                Socket client = server.accept();
                if(users.size() < 2){
                    ClientHandler handler = new ClientHandler(client);
                    users.add(handler);
                    outputStream.writeObject("Benvenuto Client: " + users.size());

                    System.out.println(users.size());
                    handler.start();
                }
                else{
                    client.close();
                    server.close();
                }
            }

            try {
                server.close();
                for (int i = 0; i < users.size(); ++i) {
                    ClientHandler tc = users.get(i);
                    try {
                        // Chiusura di DataStream
                        tc.inputStream.close();
                        tc.inputStream.close();
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


    public static void main(String[] args)
    {
        int port = 1234;


        Server server = new Server(port);
        server.start();
    }

    // ClientHandler class
    public class ClientHandler extends Thread {
        // socket ricezione messaggio client
        Socket socket;
        ObjectInputStream inputStream;
        ObjectOutputStream outputStreamm;
        int ID;
        public String username;


        //Costruttore
        ClientHandler(Socket socket) {
            ID = ++ID;
            this.socket = socket;

            try {
                outputStreamm = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());
                // Legge il primo messaggio inviato
                username = (String) inputStream.readObject();
                System.out.println(users.size());

            } catch (IOException e) {
                e.printStackTrace();
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
