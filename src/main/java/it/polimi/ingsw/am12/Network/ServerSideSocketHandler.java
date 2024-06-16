package it.polimi.ingsw.am12.Network;

import it.polimi.ingsw.am12.Exceptions.IllegalStateException;
import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.Network.Messages.Events.Event;
import it.polimi.ingsw.am12.Exceptions.NoNicknameException;
import it.polimi.ingsw.am12.Exceptions.NoMatchException;
import it.polimi.ingsw.am12.Exceptions.DuplicateNicknameException;
import it.polimi.ingsw.am12.Exceptions.DuplicateMatchException;
import it.polimi.ingsw.am12.Exceptions.*;
import it.polimi.ingsw.am12.Network.Messages.*;
import it.polimi.ingsw.am12.Network.Messages.CreateMatchMessage;
import it.polimi.ingsw.am12.Network.Messages.JoinMatchMessage;
import it.polimi.ingsw.am12.Server;
import it.polimi.ingsw.am12.VirtualView.VirtualView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * This class handles a socket connection on server side
 */
public class ServerSideSocketHandler implements Runnable {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Server server;
    private VirtualView view;
    private String nickClient;
    private boolean connected;

    /**
     * Constructor of a socket connection handler
     * @param socket the client Socket
     * @param server the server
     */
    public ServerSideSocketHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        nickClient = null;
    }

    /**
     * Start the socket connection
     * It waits for messages from the client, executes the corresponding operations
     * and sends and update message to the client.
     */
    public void run() {
        try {
            connected = true;
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            while(connected){
                try {
                    System.out.println("Server listening...");
                    Object inObject = input.readObject();
                    //Stampa dell'evento creato nel server per verificare corretta ricezione
                    System.out.println(inObject.toString());
                    handleMessage(inObject);
                } catch (IOException e) {
                    //System.err.println("I/O input error 1: " + e.getMessage());
                    connected = false;
                } catch (ClassNotFoundException e){
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("I/O error in connection setup: " + e.getMessage());
        }
    }

    /**
     * Handles the Object message from the client
     * If client sends a ping message, answers with a pong.
     * Depending on the received message, executes the corresponding operations
     * and sends an update message to the client.
     * @param inObj the Object from the client
     */
    private void handleMessage(Object inObj){
        if(inObj instanceof String && inObj.equals("ping")) {
            if(nickClient != null)
                System.out.println("Ping from client " + nickClient + " " + socket.toString() + " received. Sending pong...");
            try {
                view.resetPingTimer();
                output.writeObject("pong");
            } catch(IOException e) {
                System.err.println("Pong was not sent: " + e.getMessage());
                connected = false;
            }
        }
        if(inObj instanceof NicknameMessage){
            NicknameMessage message = (NicknameMessage) inObj;
            if(nickClient != null)
                sendMessage(new IllegalStateException("You've already defined the nickname"));
            else{
                try {
                    server.setNickname(message.getNickname(), null, this);
                    nickClient = message.getNickname();
                    this.view = server.getVirtuaView(nickClient);
                } catch (RemoteException | NotBoundException | AlreadyBoundException | DuplicateNicknameException e) {
                    sendMessage(e);
                }
            }
            if(view != null)
                view.startPingTimer();
        }
        if(inObj instanceof LobbiesRequestMessage){
            if(nickClient == null)
                sendMessage(new NoNicknameException("You aren't logged"));
            else{
                try {
                    server.getIncompleteLobbies(nickClient);
                } catch (NoNicknameException | RemoteException e) {
                    sendMessage(e);
                }
            }
        }
        if(inObj instanceof CreateMatchMessage) {
            CreateMatchMessage message = (CreateMatchMessage) inObj;
            if(nickClient == null){
                sendMessage(new NoNicknameException("You aren't logged"));
            }else {
                try {
                    server.createMatch(message.getMatchName(), message.getNumPlayers(), nickClient);
                } catch (DuplicateNicknameException | WrongNumberOfPlayersException | DuplicateMatchException |
                         IOException | WrongInformationException | InvalidSearchPositionException |
                         NotYourTurnException | EmptyDeckException | InvalidPlacementException | NoNicknameException |
                         IllegalStateException | it.polimi.ingsw.am12.Exceptions.InvalidParameterException e) {
                    sendMessage(e);
                }
            }
        }
        if(inObj instanceof JoinMatchMessage) {
            JoinMatchMessage message = (JoinMatchMessage) inObj;
            if(nickClient == null) {
                sendMessage(new NoNicknameException("You aren't logged"));
            }else{
                try {
                    server.joinMatch(message.getMatchName(), nickClient);
                } catch (DuplicateNicknameException | WrongNumberOfPlayersException | IOException |
                         WrongInformationException | IllegalStateException | InvalidSearchPositionException |
                         NotYourTurnException | EmptyDeckException | InvalidPlacementException | NoMatchException |
                         NoNicknameException | it.polimi.ingsw.am12.Exceptions.InvalidParameterException e) {
                    sendMessage(e);
                }
            }
        }
        if(inObj instanceof Event){
            Event event = (Event) inObj;
            if(nickClient == null){
                sendMessage(new NoNicknameException("You aren't logged"));
            }else{
                try {
                    view.performEvent(event);
                } catch (WrongInformationException | InvalidSearchPositionException | NotYourTurnException |
                         WrongNumberOfPlayersException | EmptyDeckException | DuplicateNicknameException |
                         InvalidPlacementException | NullPointerException | InvalidParameterException |
                         IllegalStateException e) {
                    sendMessage(e);
                }
            }
        }
        if(inObj instanceof CloseMatchConnectionMessage){
            CloseMatchConnectionMessage closeMatchConnectionMessage = (CloseMatchConnectionMessage) inObj;
            if(closeMatchConnectionMessage.getMode()==MatchCloseMode.QUIT) {
                try {
                    shutdown();
                } catch (NoMatchException | NotBoundException | RemoteException e) {
                    sendMessage(e);
                }
            }
            else if(closeMatchConnectionMessage.getMode()==MatchCloseMode.ENDGAME){
                if(server.getGameStateFromNickname(nickClient)== State.END){
                    try {
                        shutdown();
                    } catch (NoMatchException | NotBoundException | RemoteException e) {
                        sendMessage(e);
                    }
                }
            }
        }
    }

    /**
     * Send an object to the client
     * @param inObj the Object to send
     */
    public synchronized void sendMessage(Object inObj){
        try {
            output.reset();
            output.writeObject(inObj);
        } catch(IOException e){
            System.err.println("I/O error 2: " + e.getMessage());
        }
    }

    /**
     * Close the Socket connection and notifies the server that a player has disconnected
     * @throws NoMatchException if the player is not part of a match
     * @throws NotBoundException if an attempt is made to look for a name that is not currently
     *                           bound in the RMI registry
     * @throws RemoteException if remote communication with the RMI registry failed
     */
    public void shutdown() throws NoMatchException, NotBoundException, RemoteException {
        System.out.println("Closing socket for player " + nickClient);
        connected = false;
        server.playerDisconnectionHandler(nickClient);
        closeConnection();
    }

    /**
     * Close the Socket connection
     */
    private void closeConnection(){
        try {
            System.out.println("Closing socket connection for player... " + nickClient);
            if(input != null)
                input.close();
            if(output != null)
                output.close();
            if(socket != null && !socket.isClosed())
                socket.close();
        } catch (IOException e) {
            System.err.println("(closeConnection) Error in closing connection " + e.getMessage());
        }
    }

    /*
    private void startPingTimer() {
        waitingTime = new Timer();
        waitingTime.schedule(new TimerTask() {
            @Override
            public void run() {
                //If a ping is not received within the PING_TIMEOUT, the client is inactive
                System.err.println("Ping timeout: the client " + nickClient + " " + socket.toString() + " is inactive. Closing socket connection...");
                connected = false;
                server.printNicknamesToMatch();
                try {
                    shutdown();
                } catch (NoMatchException | NotBoundException | RemoteException e) {
                    System.err.println("(startPingTimer) Error in closing connection" + e.getMessage());
                }
            }
        }, PING_TIMEOUT);
    }


    private void resetPingTimer() {
        if (waitingTime != null) {
            waitingTime.cancel();
        }
        System.out.println("reset ping timer...");
        startPingTimer();
    }
    */
}