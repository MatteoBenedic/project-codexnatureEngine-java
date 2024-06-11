package it.polimi.ingsw.am12.Network.Messages;

import it.polimi.ingsw.am12.ClientStub;
import it.polimi.ingsw.am12.Exceptions.*;
import it.polimi.ingsw.am12.Exceptions.IllegalStateException;
import it.polimi.ingsw.am12.Message;
import it.polimi.ingsw.am12.ServerStub;
import it.polimi.ingsw.am12.VVStub;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * This class defines the messages with the client request to join an existing match in the server.
 */
public class JoinMatchMessage implements Message {
    private final String matchName;

    /**
     * Class Constructor
     * @param matchName the name of the match that the player wants to join
     */
    public JoinMatchMessage(String matchName) {
        this.matchName = matchName;
    }

    /**
     * @return the name of the match to be joined
     */
    public String getMatchName() {
        return matchName;
    }

    /**
     * Invoke the corresponding function in remote server via RMI
     * @param client the client
     * @param server the remote Server
     * @param vv the remote VirtualView
     * @param nickname the nickname of the player;
     */
    public void sendRMI(ClientStub client, ServerStub server, VVStub vv, String nickname) throws RemoteException, WrongInformationException, InvalidSearchPositionException, AlreadyBoundException, NotYourTurnException, NoNicknameException, NotBoundException, WrongNumberOfPlayersException, EmptyDeckException, DuplicateNicknameException, InvalidPlacementException, NoMatchException, IllegalStateException, InvalidParameterException {
        server.joinMatch(matchName, nickname);
    }
}
