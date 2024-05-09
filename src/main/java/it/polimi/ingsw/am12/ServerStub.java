package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.Model.Logic.*;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.InvalidParameterException;

public interface ServerStub extends Remote, Serializable {

    void setNickname(String nickName, ClientStub client, ServerSideSocketHandler socketHandler)
            throws RemoteException, NotBoundException, AlreadyBoundException, DuplicateNicknameException;

    void getIncompleteLobbies(String nickname) throws NoNicknameException;

    void createMatch(String matchName, int numPlayers, String nickname)
            throws NotBoundException, RemoteException, AlreadyBoundException, DuplicateNicknameException, WrongNumberOfPlayersException,
            DuplicateMatchException, IllegalStateException, InvalidPlacementException, WrongInformationException, NotYourTurnException,
            InvalidParameterException, EmptyDeckException, InvalidSearchPositionException, NoNicknameException;

    void joinMatch(String matchName, String nickname)
            throws NotBoundException, RemoteException, AlreadyBoundException, DuplicateNicknameException, WrongNumberOfPlayersException,
            NoMatchException, IllegalStateException, InvalidPlacementException, WrongInformationException, NotYourTurnException,
            InvalidParameterException, EmptyDeckException, InvalidSearchPositionException;
}
