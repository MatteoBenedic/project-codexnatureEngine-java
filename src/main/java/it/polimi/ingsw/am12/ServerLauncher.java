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

    private static int PORT_SOCKET;
    private static int PORT_REGISTRY;
    private static String HOST_ID;

    public static void main(String[] args) throws IOException, AlreadyBoundException, InterruptedException {

        if (args.length != 3) {
            System.err.println("Wrong number of arguments");
            exit(1);
        }
        PORT_SOCKET = Integer.parseInt(args[0]);
        PORT_REGISTRY = Integer.parseInt(args[1]);
        HOST_ID = args[2];
        Registry registry = LocateRegistry.createRegistry(PORT_REGISTRY);
        System.setProperty("java.rmi.server.hostname", HOST_ID);
        Server server = new Server(PORT_SOCKET, registry);
        registry.bind("CodexServer", server);

        server.start();
    }
}
