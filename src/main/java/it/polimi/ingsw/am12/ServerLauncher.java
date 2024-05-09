package it.polimi.ingsw.am12;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import static java.lang.System.exit;
import static java.lang.Thread.sleep;

/**
 * Launch the server
 */
public class ServerLauncher {

    private static int PORT_REGISTRY;
    private static String HOST_ID;

    public static void main(String[] args) throws IOException, AlreadyBoundException, InterruptedException {

        if (args.length != 3) {
            System.err.println("Server cannot start without specifying a port!");
            exit(1); //Status 1: port number not provided
        }
        int portSocket = Integer.parseInt(args[0]);
        PORT_REGISTRY = Integer.parseInt(args[1]);
        HOST_ID = args[2];
        Registry registry = LocateRegistry.createRegistry(PORT_REGISTRY);
        System.setProperty("java.rmi.server.hostname", HOST_ID);
        Server server = new Server(portSocket, registry);
        registry.bind("CodexServer", server);

        server.start();
    }
}
