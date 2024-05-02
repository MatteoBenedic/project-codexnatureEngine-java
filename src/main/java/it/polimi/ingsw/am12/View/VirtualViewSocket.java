package it.polimi.ingsw.am12.View;


import it.polimi.ingsw.am12.ServerSideSocketHandler;
import it.polimi.ingsw.am12.View.Updates.Update;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * This class is the server side component of the player view in case of a TCP/Socket connection.
 * When the player requests an action, it creates an event to notify the Controller of the game.
 */
public class VirtualViewSocket extends VirtualView {

    private final ServerSideSocketHandler socketHandler;
    /**
     * Class constructor
     *
     * @param nickname       a String that identifies the player who owns this instance of VirtualView
     * @param socketHandler  the server side socket handler associated to this player, to send update messages
     * @throws RemoteException   if remote communication with the RMI registry failed
     * @throws NotBoundException if an attempt is made to look for a name that is not currently
     *                           bound in the RMI registry
     */
    public VirtualViewSocket(String nickname, ServerSideSocketHandler socketHandler) throws RemoteException, NotBoundException {
        super(nickname);
        this.socketHandler = socketHandler;
    }

    /**
     * Listen to an Update and send it to the socketHandler
     *
     * @param u the listened Update
     */
    @Override
    public void sendUpdate(Update u) {
        socketHandler.sendMessage(u);
    }
}
