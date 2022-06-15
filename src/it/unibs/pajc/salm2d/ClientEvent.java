package it.unibs.pajc.salm2d;

import java.util.ArrayList;

public class ClientEvent {

    Protocol sender;
    String command;
    ArrayList<String> parameters;

    private ClientEvent(Protocol sender, String command,
                        ArrayList<String> parameters) {

        this.sender = sender;
        this.command = command.toUpperCase();
        this.parameters = parameters;
    }


    public static ClientEvent parse(Protocol sender, String message) {
        String command = null;
        ArrayList<String> parameters = new ArrayList<>();

        if(message.startsWith("@")) {

            String[] tokens = message.split(":");

            command = tokens[0];

            for(int i=1; i<tokens.length; i++)
                parameters.add(tokens[i]);

        } else {
            parameters.add(message);
        }


        return new ClientEvent(sender, command, parameters);

    }

    public String getLastParameters() {
        return parameters.size() > 0 ? parameters.get(parameters.size()-1) : "";
    }
}