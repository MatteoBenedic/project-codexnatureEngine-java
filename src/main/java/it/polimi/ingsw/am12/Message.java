package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.Exceptions.*;
import it.polimi.ingsw.am12.Exceptions.IllegalStateException;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * This class is used as an identifier of all the messages that travel through the network between client and server.
 */
public interface Message extends Serializable {

    /**
     * Invoke the corresponding function in remote server via RMI
     * @param client the client
     * @param server the remote Server
     * @param vv the remote VirtualView
     * @param nickname the nickname of the player;
     */
    void sendRMI(ClientStub client, ServerStub server, VVStub vv, String nickname) throws NoMatchException, RemoteException, WrongInformationException, InvalidSearchPositionException, AlreadyBoundException, NotYourTurnException, NoNicknameException, NotBoundException, DuplicateMatchException, WrongNumberOfPlayersException, EmptyDeckException, DuplicateNicknameException, InvalidPlacementException, IllegalStateException, InvalidParameterException;
}
