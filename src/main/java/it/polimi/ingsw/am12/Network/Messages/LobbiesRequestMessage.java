package it.polimi.ingsw.am12.Network.Messages;

import it.polimi.ingsw.am12.ClientStub;
import it.polimi.ingsw.am12.Exceptions.*;
import it.polimi.ingsw.am12.Message;
import it.polimi.ingsw.am12.ServerStub;
import it.polimi.ingsw.am12.VVStub;
import java.rmi.RemoteException;

/**
 * This class defines the messages with the client request of the incomplete lobbies in the server that it could join
 */
public class LobbiesRequestMessage implements Message {

    /**
     * Invoke the corresponding function in remote server via RMI
     * @param client the client
     * @param server the remote Server
     * @param vv the remote VirtualView
     * @param nickname the nickname of the player;
     */
    public void sendRMI(ClientStub client, ServerStub server, VVStub vv, String nickname) throws NoNicknameException, RemoteException {
        server.getIncompleteLobbies(nickname);
    }
}
