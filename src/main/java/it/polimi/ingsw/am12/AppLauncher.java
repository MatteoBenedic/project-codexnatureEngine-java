package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.Client.ClientController.ClientController;
import it.polimi.ingsw.am12.Client.ClientController.ClientControllerRMI;
import it.polimi.ingsw.am12.Client.ClientController.ClientControllerSocket;
import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.LaunchGUI;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import static java.lang.System.exit;
import static javafx.application.Application.launch;

/**
 * Launch the application
 */
public class AppLauncher {

    private static int PORT_SOCKET;
    private static int PORT_REGISTRY;
    private static String HOST_ID;

    public static void main(String[] args) throws IOException, AlreadyBoundException {
        try {
            if (args.length < 1) {
                System.err.println("Please define if you're the server or a client");
                exit(1);
            }
            String side = args[0];
            if(side.equals("server")) {
                if (args.length != 4) {
                    System.err.println("Wrong number of arguments");
                    exit(1);
                }
                PORT_SOCKET = Integer.parseInt(args[1]);
                PORT_REGISTRY = Integer.parseInt(args[2]);
                HOST_ID = args[3];
                Registry registry = LocateRegistry.createRegistry(PORT_REGISTRY);
                System.setProperty("java.rmi.server.hostname", HOST_ID);
                Server server = new Server(PORT_SOCKET, registry);

                registry.bind("CodexServer", server);

                server.start();
            }
            else if(side.equals("client")){
                if (args.length != 5) {
                    System.err.println("Wrong number of arguments");
                    exit(1);
                }

                //Extract args
                String ip = args[1];
                int port = Integer.parseInt(args[2]);
                String interfaceType = args[3];
                String connectionType = args[4];

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
            } else{
                System.err.println("You did not defined server/client side");
                exit(1);
            }
        } catch (Exception e) {
            System.err.println("An unexpected error occurred");
            exit(1);
        }
    }
}
