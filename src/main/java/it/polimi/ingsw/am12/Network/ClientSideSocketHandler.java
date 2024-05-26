package it.polimi.ingsw.am12.Network;

import it.polimi.ingsw.am12.Client.ClientController.*;
import it.polimi.ingsw.am12.Network.Messages.Updates.Update;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

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
    private volatile boolean connected;
    private Timer pingTimer = new Timer();
    private Timer pongTimeoutTimer;
    private static final int PING_INTERVAL = 2500;
    private static final int PONG_TIMEOUT = 10000;

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
            connected = true;

        } catch (IOException e) {
            connected = false;
            out.println("Server not connected");
            throw new RuntimeException(e);
        }

        //startPingPong();
        new Thread(this::startPingPong).start();
        new Thread(this::waitForUpdate).start();

    }


    /**
     * Send a "ping" to the server
     */
    private void pingServer() {
        try {
            //System.out.println("Sending a ping to server...");
            output.reset();
            output.writeObject("ping");
        } catch (IOException e) {
            System.err.println("Error in sending ping... " + e.getMessage());
        }
    }

    /**
     * Starts the ping-pong mechanism: schedules a task to send ping at fixed intervals,
     * then calls a method to schedule another task that checks if a pong is received withing the pong timeout
     */
    private void startPingPong() {
        pingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (connected) {
                    pingServer();
                }
            }
        }, 0, PING_INTERVAL);

        schedulePongTimeout();
    }

    /**
     * Schedule a task that initilizes a new timer to check if pong is received within the timeout
     */
    private void schedulePongTimeout() {
        if (pongTimeoutTimer != null)
            pongTimeoutTimer.cancel();

        pongTimeoutTimer = new Timer();
        pongTimeoutTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                connectionLostHandler();
            }
        }, PONG_TIMEOUT);
    }

    /**
     * Reset the pong timeout timer
     */
    private void resetPongTimeoutTimer() {
        if (pongTimeoutTimer != null)
            pongTimeoutTimer.cancel();
        schedulePongTimeout();
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
                    resetPongTimeoutTimer();
                }
            } catch (IOException | ClassNotFoundException e) {
                connectionLostHandler();
            }

        }
    }

    /**
     * Close the socket connection
     */
    public void closeConnection(){
        try {
            connected = false;
            if(input != null)
                input.close();
            if(output != null)
                output.close();
            if(socket != null)
                socket.close();
            pingTimer.cancel();
            if(pongTimeoutTimer != null)
                pongTimeoutTimer.cancel();

        } catch (IOException e) {
            System.err.println("Error in closing connection"+ e.getMessage());
        }
    }

    private void connectionLostHandler() {
        if(connected) {
            System.out.println("connection to server lost...");
            closeConnection();
        }
    }

}






/*
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
 */