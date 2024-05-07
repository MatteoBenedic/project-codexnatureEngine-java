package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.View.Updates.Update;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Launch the client
 */
public class ClientLauncher {


    public static void main(String[] args) throws IOException {
        //Extract args
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        ClientInterface clientInterface = null;
        if(args[2].equals("cli"))
            clientInterface = ClientInterface.CLI;
        if(args[2].equals("gui"))
            clientInterface = ClientInterface.GUI;

        String connectionType = args[3];

        if(connectionType.equals("socket")) {
            System.out.println("socket");
            ClientSideSocketHandler clientSocket = new ClientSideSocketHandler(ip, port, clientInterface);
            clientSocket.run();
        }

        if(connectionType.equals("rmi")) {
            RMISimulator sim = new RMISimulator(ip);
            new Thread(sim).start();
        }
    }
}
