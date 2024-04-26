package it.polimi.ingsw.am12;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Launch the server
 */
public class ServerLauncher {

    public static void main(String[] args) throws IOException, AlreadyBoundException {
        int port = 7474;

        Registry registry = LocateRegistry.getRegistry();
        Server server = new Server(port, registry);
        registry.bind("CodexServer", server);

        server.start();
    }
}
