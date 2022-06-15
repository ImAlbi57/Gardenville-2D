package it.unibs.pajc.salm2d;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Consumer;

public class Protocol implements Runnable {
    private static HashMap<String, Consumer<ClientEvent>> commandMap;
    static {
        commandMap = new HashMap<>();

        commandMap.put("@LIST", e -> e.sender.listClient(e.sender));

        commandMap.put("@ALL", e -> e.sender.sendToAll(e.sender,
                e.getLastParameters()));

        commandMap.put("@TIME", e -> e.sender.sendMessage(e.sender,
                LocalDateTime.now().toString()));

        commandMap.put("@QUIT", e -> e.sender.close() );

        commandMap.put("@default@", e -> e.sender.sendMessage(e.sender,
                e.getLastParameters()));
    }

    public void close() {
        isrunning = false;
    }

    private boolean isrunning = true;
    private static ArrayList<Protocol> clientList = new ArrayList<>();
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private String clientName;

    public Protocol(Socket client) {
        this.client = client;
        clientList.add(this);
    }

    public void run() {
        try {
            in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);

            System.out.println("Client connesso: " + client.getPort());

            boolean convertToUpper = true;
            String request;
            clientName ="";

            do {
                sendMessage(this, "Ciao! dimmi il tuo nome: ");
                clientName = in.readLine();
                System.out.printf("[?]: %s\n", clientName);

            } while(clientName.length() < 3);

            out.printf("\nBenvenuto >>> %s <<<\n", clientName);

            while(isrunning && (request = in.readLine()) != null) {
                System.out.println("Processing request: " + request);

                ClientEvent e = ClientEvent.parse(this, request);
                Consumer<ClientEvent> commandExe =
                        e.command != null && commandMap.containsKey(e.command)?
                                commandMap.get(e.command) :
                                commandMap.get("@default@");

                commandExe.accept(e);

            }

            out.printf("Arrivederci %s\n", clientName);

        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            clientList.remove(this);
            try {
                this.client.close();
            } catch(Exception ex2) {
                ex2.printStackTrace();
            }
        }


    }

    protected void listClient(Protocol sender) {
        StringBuilder msg = new StringBuilder();
        clientList.forEach(c -> msg.append(String.format("%s, ", c.clientName)));

        this.sendMessage(sender, msg.toString());
    }

    protected void sendToAll(Protocol sender, String message) {
        clientList.forEach(c -> c.sendMessage(sender, message));
		/*for(Protocol c: clientList) {
			c.sendMessage(sender, message);
		}*/
    }

    protected void sendMessage(Protocol sender, String message) {
        this.out.printf("[%s]: %s\n", sender.clientName, message);
        this.out.flush();
    }

}