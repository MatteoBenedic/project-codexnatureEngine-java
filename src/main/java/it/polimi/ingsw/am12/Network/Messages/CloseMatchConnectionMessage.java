package it.polimi.ingsw.am12.Network.Messages;

import it.polimi.ingsw.am12.ClientStub;
import it.polimi.ingsw.am12.Exceptions.NoMatchException;
import it.polimi.ingsw.am12.Message;
import it.polimi.ingsw.am12.ServerStub;
import it.polimi.ingsw.am12.VVStub;

import java.rmi.RemoteException;

/**
 * This class defines the messages with the client request to close its match and server connection if it's connected
 * by sockets
 */
public class CloseMatchConnectionMessage implements Message {
    private final MatchCloseMode mode;

    /**
     * Constructor method for a CloseMatchMessage
     * @param mode a MatchCloseMode
     */
    public CloseMatchConnectionMessage(MatchCloseMode mode) {
        this.mode = mode;
    }

    /**
     * Getter method for the match closing mode
     * @return a MatchCloseMode
     */
    public MatchCloseMode getMode() {
        return mode;
    }

    /**
     * Invoke the corresponding function in remote server via RMI
     * @param client the client
     * @param server the remote Server
     * @param vv the remote VirtualView
     * @param nickname the nickname of the player;
     */
    public void sendRMI(ClientStub client, ServerStub server, VVStub vv, String nickname) throws NoMatchException, RemoteException {
        server.closeMatchForPlayer(nickname);
    }
}
