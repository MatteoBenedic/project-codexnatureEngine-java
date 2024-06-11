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
 * This class defines the messages with the client request to create a new match in the server.
 */
public class CreateMatchMessage implements Message {
    private final int numPlayers;
    private final String matchName;

    /**
     * Class Constructor
     * @param numPlayers the number of players defined to play the match
     * @param matchName the name, and identifier, of the match to be created
     */
    public CreateMatchMessage(int numPlayers, String matchName) {
        this.numPlayers = numPlayers;
        this.matchName = matchName;
    }

    /**
     * @return the number of players
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * @return the name of the match to be created
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
    public void sendRMI(ClientStub client, ServerStub server, VVStub vv, String nickname) throws RemoteException, WrongInformationException, InvalidSearchPositionException, AlreadyBoundException, NotYourTurnException, NoNicknameException, NotBoundException, DuplicateMatchException, WrongNumberOfPlayersException, EmptyDeckException, DuplicateNicknameException, InvalidPlacementException, IllegalStateException, InvalidParameterException {
        server.createMatch(matchName, numPlayers, nickname);
    }
}
