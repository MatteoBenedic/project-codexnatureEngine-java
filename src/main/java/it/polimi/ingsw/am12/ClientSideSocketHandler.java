package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.CLI.CLI;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import static java.lang.System.out;

/**
 * This class handles a socket connection on client side
 */
public class ClientSideSocketHandler implements Runnable{

    private Socket socket;
    private String ip;
    private int port;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String idNickname;
    private ClientInterface clientInterface;

    /**
     * Constructor for the ClientSideSocketHandler
     * @param ip ip address of the server
     * @param port server port to connect to
     */
    public ClientSideSocketHandler(String ip, int port, ClientInterface clientInterface) {
        this.ip = ip;
        this.port = port;
        this.clientInterface = clientInterface;
    }

    /**
     * Send an object to the server
     * @param inObj the Object to send
     */
    private void send(Object inObj){
        try {
            output.reset();
            output.writeObject(inObj);
            //output.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Start the socket connection
     * It sends a "ping" to the server and waits for a "pong" to be received.
     * Then waits for command from the user and sends the correct
     * message to the server in a new thread.
     * It also receives messages from the server and shows an update to the user.
     */
    @Override
    public void run() {
        try {
            socket = new Socket(ip, port);
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            out.println("Server not connected");
            throw new RuntimeException(e);
        }

        if(clientInterface.equals(ClientInterface.CLI)) {
            try {
                output.reset();
                out.println("Sending ping to server...");
                output.writeObject("ping");
                //Rimane in attesa finchè non riceve pong dal server
                Object inObj;
                do {
                    inObj = input.readObject();
                } while(!(inObj instanceof String) || !inObj.equals("pong"));
                out.println("Pong received!");
            } catch (IOException | ClassNotFoundException e) {
                out.println("Server not connected");
                throw new RuntimeException();
            }

            CLI cli = new CLI();
            while(true) {
                //Leggi comandi e lancia evento
                Scanner cliScanner = new Scanner(System.in);
                System.out.println("Enter command: ");
                String command = cliScanner.nextLine();
                Object message = cli.parseCommandSocket(command);
                send(message);

                //Rimani in attesa di update
                Object u;
                try {
                    u = input.readObject();
                    cli.showUpdateSocket(u);
                } catch (IOException | ClassNotFoundException e) {
                    out.println("Server not connected");
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
