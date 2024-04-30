package it.polimi.ingsw.am12;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import static java.lang.System.exit;
import static java.lang.Thread.sleep;

/**
 * Launch the server
 */
public class ServerLauncher {

    public static void main(String[] args) throws IOException, AlreadyBoundException, InterruptedException {

        if (args.length != 1) {
            System.err.println("Server cannot start without specifying a port!");
            exit(1); //Status 1: port number not provided
        }
        int portSocket = Integer.parseInt(args[0]);
        Registry registry = LocateRegistry.createRegistry(1600);
        Server server = new Server(portSocket, registry);
        registry.bind("CodexServer", server);

        server.start();
    }
}
