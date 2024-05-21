package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.CLI.CLI;
import java.io.IOException;
import static java.lang.System.exit;
import static javafx.application.Application.launch;


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

        if(interfaceType.equals("cli")) {
            ClientController controller = null;
            if(connectionType.equals("socket")) {
                controller = new ClientControllerSocket(ip, port);
            }
            if(connectionType.equals("rmi")) {
                controller = new ClientControllerRMI(ip, port);
            }
            new CLI(controller);
        }
        if(interfaceType.equals("gui")) {
            launch(LaunchGUI.class, args);
        }
    }
}

