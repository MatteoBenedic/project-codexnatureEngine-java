package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.Exceptions.*;
import it.polimi.ingsw.am12.Exceptions.IllegalStateException;
import it.polimi.ingsw.am12.Network.ServerSideSocketHandler;

import it.polimi.ingsw.am12.Exceptions.NoNicknameException;
import it.polimi.ingsw.am12.Exceptions.NoMatchException;
import it.polimi.ingsw.am12.Exceptions.DuplicateNicknameException;
import it.polimi.ingsw.am12.Exceptions.DuplicateMatchException;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface that defines the methods used by the client or the communication handler of the server make requests to the server
 */
public interface ServerStub extends Remote, Serializable {

    /**
     * Sets the nickname of the client that calls this remote method or by the socket handler connected to it
     * @param nickname the nickname with whom wants to be identified
     * @param client An interface that identifies the client in case of an RMI connection
     * @param socketHandler the server side socket handler of the player with a socket connection
     *                   who wants to create the match
     * @throws RemoteException if remote communication with the RMI registry failed
     * @throws NotBoundException if an attempt is made to look for a name that is not currently
     *                           bound in the RMI registry
     * @throws AlreadyBoundException if an attempt is made to bind an object to a name
     *                          that already has an associated binding in the RMI registry.
     * @throws DuplicateNicknameException if there's already a player with that nickname
     */
    void setNickname(String nickname, ClientStub client, ServerSideSocketHandler socketHandler)
            throws RemoteException, NotBoundException, AlreadyBoundException, DuplicateNicknameException;

    /**
     * Gives to the client the incomplete lobbies that he could join
     * @param nickname the nickname of the user
     * @throws NoNicknameException if there is no such user with that nickname
     */
    void getIncompleteLobbies(String nickname) throws NoNicknameException, RemoteException;

    /**
     * Create a match in the Server
     * @param matchName A String that identifies the match
     * @param numPlayers the number of the players of the match
     * @param nickname A String that identifies the player who wants to create the match
     * @throws RemoteException if remote communication with the RMI registry failed
     * @throws DuplicateNicknameException if there's already a player with that nickname
     * @throws WrongNumberOfPlayersException if numPlayers is less than 2 or more than 4
     * @throws DuplicateMatchException if there's already a match with that name
     * @throws IllegalStateException if the method has been invoked at an illegal or inappropriate time.
     * @throws InvalidParameterException if the nickname is null
     * @throws NoNicknameException if there is no such user with that nickname
     */
    void createMatch(String matchName, int numPlayers, String nickname)
            throws NotBoundException, RemoteException, AlreadyBoundException, DuplicateNicknameException, WrongNumberOfPlayersException,
            DuplicateMatchException, IllegalStateException, InvalidPlacementException, WrongInformationException, NotYourTurnException,
            InvalidParameterException, EmptyDeckException, InvalidSearchPositionException, NoNicknameException;

    /**
     * Join a match
     * @param matchName The name of the match to join
     * @param nickname A String that identifies the player who wants to join the match
     * @throws RemoteException if remote communication with the RMI registry failed
     * @throws DuplicateNicknameException if there's already a player with that nickname
     * @throws NoMatchException if there is not a match with that name
     * @throws IllegalStateException if the method has been invoked at an illegal or inappropriate time.
     * @throws InvalidParameterException if the nickname is null
     * @throws WrongNumberOfPlayersException if there is already the maximum number of players in the lobby.
     * @throws NoNicknameException if there is no such user with that nickname
     */
    void joinMatch(String matchName, String nickname)
            throws NotBoundException, RemoteException, AlreadyBoundException, DuplicateNicknameException, WrongNumberOfPlayersException,
            NoMatchException, IllegalStateException, InvalidPlacementException, WrongInformationException, NotYourTurnException,
            InvalidParameterException, EmptyDeckException, InvalidSearchPositionException, NoNicknameException;

    /**
     * Close a match for a player, and if the last players leaves, the pointer to that particular match will be
     * eliminated from the map containing that match, and all the links in the match itself are destroyed
     * @param nickName A String that identifies the player who wants to leave the match
     * @throws NoMatchException is the player is not part of a match
     * @throws RemoteException if remote communication with the RMI registry failed
     */
    void closeMatchForPlayer(String nickName) throws NoMatchException, RemoteException;

    /**
     * Handles the disconnection of a player: closes the match in which the player was
     * @param nickname a String with the nickname of the disconnected player
     * @throws RemoteException if remote communication with the RMI registry failed
     */
    void playerDisconnectionHandler(String nickname) throws RemoteException;
}
