package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.Controller.Events.Event;
import it.polimi.ingsw.am12.Model.Logic.*;
import it.polimi.ingsw.am12.View.VirtualView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.InvalidParameterException;

/**
 * This class handles a socket connection on server side
 */
public class ServerSideSocketHandler implements Runnable {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Server server;
    private VirtualView view;

    /**
     * Constructor of a socket connection handler
     * @param socket the client Socket
     * @param server the server
     */
    public ServerSideSocketHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * Start the socket connection
     * It waits for messages from the client, executes the corresponding operations
     * and sends and update message to the client.
     */
    public void run() {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            while(true){
                System.out.println("Server listening...");
                Object inObject = input.readObject();
                handleMessage(inObject);
            }


        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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
            System.out.println("Ping from client received. Sending pong...");
            try {
                output.writeObject("pong");
            } catch(IOException e) {
                throw new RuntimeException();
            }

        }
        if(inObj instanceof CreateMatchMessage) {
            CreateMatchMessage message = (CreateMatchMessage) inObj;
            try {
                server.createMatch(message.getMatchName(), message.getNumPlayers(), message.getNickname(), null, this);
                this.view = server.getVirtuaView(message.getNickname());
            } catch (AlreadyBoundException | DuplicateNicknameException | WrongNumberOfPlayersException |
                     DuplicateMatchException | NotBoundException | IOException | WrongInformationException |
                     InvalidSearchPositionException | NotYourTurnException | EmptyDeckException |
                     InvalidPlacementException e) {
                sendMessage(e);
            }
        }
        if(inObj instanceof JoinMatchMessage) {
            JoinMatchMessage message = (JoinMatchMessage) inObj;
            try {
                server.joinMatch(message.getMatchName(), message.getNickname(), null, this);
                this.view = server.getVirtuaView(message.getNickname());
            } catch (AlreadyBoundException | DuplicateNicknameException | WrongNumberOfPlayersException |
                     NotBoundException | IOException | WrongInformationException |
                     InvalidSearchPositionException | NotYourTurnException | EmptyDeckException |
                     InvalidPlacementException | NoMatchException e) {
                sendMessage(e);
            }
        }
        if(inObj instanceof Event){
            Event event = (Event) inObj;
            try {
                view.performEvent(event);
            } catch (WrongInformationException | InvalidSearchPositionException | NotYourTurnException |
                     WrongNumberOfPlayersException | EmptyDeckException | DuplicateNicknameException |
                     InvalidPlacementException | IllegalStateException | InvalidParameterException e) {
                sendMessage(e);
            }
        }
    }

    /**
     * Send an object to the client
     * @param inObj the Object to send
     */
    public void sendMessage(Object inObj){
        try {
            output.reset();
            output.writeObject(inObj);
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Remove the player and close the Socket connection
     * @throws NoMatchException if the player is not part of a match
     * @throws NotBoundException if an attempt is made to look for a name that is not currently
     *                           bound in the RMI registry
     * @throws RemoteException if remote communication with the RMI registry failed
     */
    public void close() throws NoMatchException, NotBoundException, RemoteException {
        server.closeMatchForPlayer(view.getNickname());
        closeConnection();
    }

    /**
     * Close the Socket connection
     */
    private void closeConnection(){
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error in closing connection");
        }
    }
}
