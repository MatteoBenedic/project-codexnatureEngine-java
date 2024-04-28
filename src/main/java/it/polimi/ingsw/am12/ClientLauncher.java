package it.polimi.ingsw.am12;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
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
            Registry registry = LocateRegistry.getRegistry();
            //Ask for nickname
            boolean userCreated = false;
            do {
                Scanner myObj = new Scanner(System.in);
                System.out.println("Enter username");
                String nickname = myObj.nextLine();
                Client client = new Client();
                try {
                    registry.bind(nickname+"Client", client);
                    userCreated = true;
                } catch (AlreadyBoundException ignored) {
                }
            } while(!userCreated);
        }
    }
}
