package it.polimi.ingsw.am12.Network.Messages;

import it.polimi.ingsw.am12.ClientStub;
import it.polimi.ingsw.am12.Exceptions.DuplicateNicknameException;
import it.polimi.ingsw.am12.Message;
import it.polimi.ingsw.am12.ServerStub;
import it.polimi.ingsw.am12.VVStub;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * This class defines all the first messages accepted by the server from the client. The latter has to send the nickname
 * with whom wants to be identified in the whole server
 */
public class NicknameMessage implements Message {
    private final String nickname;

    private ClientStub client;

    /**
     * Class constructor
     * @param nickname the identifier name chosen by the player
     */
    public NicknameMessage(String nickname){
        this.nickname = nickname;
    }

    /**
     * @return the nickname of the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Set the reference to the client
     * @param client the reference to the client
     */
    public void setClient(ClientStub client) {
        this.client = client;
    }

    /**
     * Invoke the corresponding function in remote server via RMI
     * @param client the client
     * @param server the remote Server
     * @param vv the remote VirtualView
     * @param nickname the nickname of the player;
     */
    public void sendRMI(ClientStub client, ServerStub server, VVStub vv, String nickname) throws AlreadyBoundException, NotBoundException, RemoteException, DuplicateNicknameException {
        server.setNickname(this.nickname, client, null);
    }
}
