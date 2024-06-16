package it.polimi.ingsw.am12.VirtualView;

import it.polimi.ingsw.am12.ClientStub;
import it.polimi.ingsw.am12.Network.Messages.Updates.Update;
import it.polimi.ingsw.am12.Server;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static java.lang.System.exit;

/**
 * This class is the server side component of the player view in case of an RMI connection of the client.
 * When the player requests an action, it creates an event to notify the Controller of the game.
 */
public class VirtualViewRMI extends VirtualView{
    private ClientStub client;
    private Registry registry;
    private Server server;

    /**
     * Class constructor
     *
     * @param nickname       a String that identifies the player who owns this instance of VirtualView
     * @param client        an interface that represents the methods to call of the remote client player
     * @throws RemoteException   if remote communication with the RMI registry failed
     * @throws NotBoundException if an attempt is made to look for a name that is not currently
     *                           bound in the RMI registry
     */
    public VirtualViewRMI(String nickname, ClientStub client, Server server) throws RemoteException, NotBoundException {
        super(nickname);
        this.client = client;
        this.server = server;
        this.registry= server.getRegistry();
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
            System.err.println(e.getMessage());
            //throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * RMI ping equivalent: this method represents an invocation that is done to periodically
     * check if the connection with the server is still active
     */
    public void invokePingRMI() {
        System.out.println("Ping from RMI client " + client.toString() + " received");
        resetPingTimer();
        answerPongRMI();
    }

    /**
     * RMI method used to answer the RMI client once a ping invocation is received.
     * It is the RMI equivalent to a pong answer
     */
    @Override
    protected void answerPongRMI() {
        try {
            client.invokePongRMI();
        } catch (RemoteException e) {
            System.err.println("Error: cannot sent pong to client: " + e.getMessage());
        }
    }

    /**
     * Closes the connection with a client when the ping timeout is triggered
     */
    @Override
    protected void shutdown() {
        System.out.println("Closing connection for client " + getNickname() + "...");
        try {
            try {
                registry.unbind(getNickname()+"VirtualView");
            } catch (NotBoundException e) {
                System.err.println(getNickname() + " was not bound in the registry");
            } catch (RemoteException e) {
                System.err.println("Error: cannot unbind " + getNickname() + " from registry");
            }
            UnicastRemoteObject.unexportObject(this, true);
            server.playerDisconnectionHandler(getNickname());
            System.out.println("Connection closed for client " + getNickname());
        } catch (RemoteException e) {
            System.err.println("Error in closing connection: " + e.getMessage());
        }

    }

}
