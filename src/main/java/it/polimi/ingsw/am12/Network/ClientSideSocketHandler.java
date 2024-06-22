package it.polimi.ingsw.am12.Network;

import it.polimi.ingsw.am12.Client.ClientController.*;
import it.polimi.ingsw.am12.Network.Messages.Updates.Update;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import static java.lang.System.exit;

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
    public void send(Object inObj) {
        try {
            output.reset();
            output.writeObject(inObj);
        } catch(IOException e){
            System.err.println("I/O error! " + e.getMessage() + ". Disabling command interface...");
            exit(1);
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
            System.err.println("Server not connected");
            exit(1);
        }

        new Thread(this::waitForUpdate).start();
    }


    /**
     * Wait for updates from the server
     */
    private void waitForUpdate() {
        while(true){
            //Rimani in attesa di messaggi dalla rete
            Object inObject;
            try {
                inObject = input.readObject();
                if(inObject instanceof Update) {
                    controller.catchMessage((Update) inObject);
                }
                if(inObject instanceof Exception) {
                    controller.catchException((Exception) inObject);
                }
                if(inObject instanceof String && inObject.equals("pong")){
                    //System.out.println("pong received");
                    controller.resetPongTimeoutTimer();
                }
            } catch (IOException | ClassNotFoundException | ClassCastException e) {
                System.err.println("Server not connected");
                break;
            }

        }
    }

    /**
     * Close the socket connection
     */
    public void closeConnection(){
        try {
            if(input != null)
                input.close();
            if(output != null)
                output.close();
            if(socket != null)
                socket.close();
            Timer pingTimer = controller.getPingTimer();
            Timer pongTimeoutTimer = controller.getPongTimeoutTimer();
            pingTimer.cancel();
            if(pongTimeoutTimer != null)
                pongTimeoutTimer.cancel();

        } catch (IOException ignored) {
        }
    }

    /**
     * This method sends a ping to the server through socket
     */
    public void pingMessageToServer() {
        try {
            //System.out.println("Sending a ping to server...");
            output.reset();
            output.writeObject("ping");
        } catch (IOException e) {
            System.err.println("Error in sending ping... ");
        }
    }
}
