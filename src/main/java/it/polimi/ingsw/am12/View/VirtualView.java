package it.polimi.ingsw.am12.View;

import it.polimi.ingsw.am12.ConnectionType;
import it.polimi.ingsw.am12.Controller.EventListener;
import it.polimi.ingsw.am12.Controller.Events.*;
import it.polimi.ingsw.am12.Model.Logic.*;
import it.polimi.ingsw.am12.Client;
import it.polimi.ingsw.am12.View.Updates.Update;
import java.io.Serializable;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidParameterException;

/**
 * This class is the server side component of the player view.
 * When the player requests an action, it creates an event to notify the Controller of the game.
 */
public class VirtualView extends UnicastRemoteObject implements Remote, UpdateListener, Serializable {
    private EventListener listener;
    private final ConnectionType connectionType;
    private final String nickname;
    private Client client;
    private final Registry registry;

    /**
     * Class constructor
     * @param nickname a String that identifies the player who owns this instance of VirtualView
     * @param connectionType the ConnectionType the client chose (RMI or SOCKET)
     * @param registry the RMI registry
     * @throws RemoteException if remote communication with the RMI registry failed
     * @throws NotBoundException if an attempt is made to look for a name that is not currently
     *                           bound in the RMI registry
     */
    public VirtualView(String nickname, ConnectionType connectionType, Registry registry) throws RemoteException, NotBoundException{
        this.nickname = nickname;
        this.connectionType = connectionType;
        this.registry = registry;
        if(connectionType.equals(ConnectionType.RMI)) {
            this.client = (Client) registry.lookup(nickname+"Client");
        }
    }



    /**
     * Subscribe a listener to this VirtualView
     * @param listener the EventListener to add as a listener of this VirtualView
     *                 Only one listener can be added: if there's already a listener, this method overwrites it
     */
    public void addListener(EventListener listener) {
        this.listener = listener;
    }

    /**
     * Remove the listener of this VirtualView
     */
    public void removeListener(){
        listener = null;
    }

    /**
     * Get the connection type
     * @return the ConnectionType (RMI or SOCKET)
     */
    public ConnectionType getConnectionType() {
        return connectionType;
    }

    /**
     * Perform an Event, that will be listened by the subscribed listener
     * @param e the Event to perform
     */
    public void performEvent(Event e) throws WrongNumberOfPlayersException, DuplicateNicknameException,
            IllegalStateException, InvalidPlacementException, WrongInformationException, NotYourTurnException,
            InvalidParameterException, EmptyDeckException, InvalidSearchPositionException{
        listener.actionPerformed(e);
    }

    /**
     * Listen to an Update and send it to the client
     * @param u the listened Update
     */
    public void sendUpdate(Update u){
        if(connectionType.equals(ConnectionType.RMI)) {
            client.sendMessage(u);
        }
    }

    /**
     * Get the nickname of the player who owns this instance of VirtualView
     * @return the nickname of the player who owns this instance of VirtualView
     */
    public String getNickname() {
        return nickname;
    }

}
