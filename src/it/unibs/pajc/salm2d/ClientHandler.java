package it.unibs.pajc.salm2d;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// ClientHandler class
class ClientHandler implements Runnable {
    private final Socket clientSocket;

    // Constructor
    public ClientHandler(Socket socket)
    {
        this.clientSocket = socket;
    }

    public void run()
    {
        PrintWriter out = null;
        BufferedReader in = null;
        try{

            // get the outputstream of client
            out = new PrintWriter(clientSocket.getOutputStream(), true);

              // get the inputstream of client
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {

                // writing the received message from
                // client
                System.out.printf(" Sent from the client: %s\n", line);
                out.println(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                    clientSocket.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
