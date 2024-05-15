package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.View.Updates.Update;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static java.lang.System.out;

/**
 * This class handles a socket connection on client side
 */
public class ClientSideSocketHandler implements Runnable{
    ClientControllerSocket controller;
    private Socket socket;
    private String ip;
    private int port;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String idNickname;

    /**
     * Constructor for the ClientSideSocketHandler
     * @param ip ip address of the server
     * @param port server port to connect to
     * @param controller the view controller of Socket type
     */
    public ClientSideSocketHandler(String ip, int port, ClientControllerSocket controller) {
        this.ip = ip;
        this.port = port;
        this.controller = controller;
    }

    /**
     * Send an object to the server
     * @param inObj the Object to send
     */
    public void send(Object inObj){
        try {
            output.reset();
            output.writeObject(inObj);
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Start the socket connection
     * It sends a "ping" to the server and waits for a "pong" to be received.
     * Then waits for messages from the server in a new thread
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

        pingServer();

        new Thread(this::waitForUpdate).start();

    }

    /**
     * Send a "ping" to the server and wait for a "pong" to be received.
     */
    private void pingServer() {
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
    }

    /**
     * Wait for updates from the server
     */
    private void waitForUpdate() {
        while(true){
            //Rimani in attesa di messaggi dalla rete
            Object inObject = null;
            try {
                inObject = input.readObject();
                if(inObject instanceof Update) {
                    controller.catchMessage((Update) inObject);
                }
                if(inObject instanceof Exception) {
                    controller.catchException((Exception) inObject);
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * Close the socket connection
     */
    public void closeConnection(){
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error in closing connection");
        }
    }
}
