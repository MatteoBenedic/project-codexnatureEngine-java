package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.CLI.CLI;

import java.io.IOException;
import static java.lang.System.exit;


/**
 * Launch the client
 */
public class ClientLauncher {

    public static void main(String[] args) throws IOException {

        if (args.length != 4) {
            System.err.println("Wrong number of args");
            exit(1); //Status 1
        }

        //Extract args
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        String interfaceType = args[2];
        String connectionType = args[3];

        ClientController c = null;
        if(connectionType.equals("socket")) {
            c = new ClientControllerSocket(ip, port);
        }
        if(connectionType.equals("rmi")) {
            c = new ClientControllerRMI(ip, port);
        }

        UserInterface userInterface;
        if(interfaceType.equals("cli")) {
            userInterface = new CLI(c);
        }
        if(interfaceType.equals("gui"))
            userInterface = new GUI();
    }
}

