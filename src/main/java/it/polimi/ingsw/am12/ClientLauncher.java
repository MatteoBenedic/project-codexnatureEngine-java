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
        //Extract connection type from args

        //If RMI
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
