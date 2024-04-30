package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.Model.Logic.*;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.InvalidParameterException;

public interface ServerStub extends Remote, Serializable {

    void createMatch(String matchName, int numPlayers, String nickname, ConnectionType connectionType, ServerSideSocketHandler socketHandler)
            throws NotBoundException, RemoteException, AlreadyBoundException, DuplicateNicknameException, WrongNumberOfPlayersException,
            DuplicateMatchException, IllegalStateException, InvalidPlacementException, WrongInformationException, NotYourTurnException,
            InvalidParameterException, EmptyDeckException, InvalidSearchPositionException;

    void joinMatch(String matchName, String nickname, ConnectionType connectionType, ServerSideSocketHandler socketHandler)
            throws NotBoundException, RemoteException, AlreadyBoundException, DuplicateNicknameException, WrongNumberOfPlayersException,
            NoMatchException, IllegalStateException, InvalidPlacementException, WrongInformationException, NotYourTurnException,
            InvalidParameterException, EmptyDeckException, InvalidSearchPositionException;
}
