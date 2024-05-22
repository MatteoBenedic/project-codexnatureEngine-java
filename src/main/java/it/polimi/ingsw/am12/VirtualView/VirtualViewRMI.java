package it.polimi.ingsw.am12.VirtualView;

import it.polimi.ingsw.am12.ClientStub;
import it.polimi.ingsw.am12.Network.Messages.Updates.Update;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * This class is the server side component of the player view in case of a RMI connection of the client.
 * When the player requests an action, it creates an event to notify the Controller of the game.
 */
public class VirtualViewRMI extends VirtualView{
    private ClientStub client;
    /**
     * Class constructor
     *
     * @param nickname       a String that identifies the player who owns this instance of VirtualView
     * @param client        an interface that represents the methods to call of the remote client player
     * @throws RemoteException   if remote communication with the RMI registry failed
     * @throws NotBoundException if an attempt is made to look for a name that is not currently
     *                           bound in the RMI registry
     */
    public VirtualViewRMI(String nickname, ClientStub client) throws RemoteException, NotBoundException {
        super(nickname);
        this.client = client;
    }

    /**
     * Listen to an Update and send it to the client
     *
     * @param u the listened Update
     */
    @Override
    public void sendUpdate(Update u) {
        try {
            client.catchMessage(u);
        } catch (RemoteException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
