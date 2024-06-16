package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.Exceptions.IllegalStateException;
import it.polimi.ingsw.am12.Network.Messages.Events.Event;
import it.polimi.ingsw.am12.Exceptions.*;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VVStub extends Remote, Serializable {
    /**
     * Gets an event and calls the controller to execute it
     * @param e the event to perform
     */
    void performEvent(Event e) throws WrongNumberOfPlayersException, DuplicateNicknameException,
            IllegalStateException, InvalidPlacementException, WrongInformationException, NotYourTurnException,
            InvalidParameterException, EmptyDeckException, InvalidSearchPositionException, RemoteException;

    void invokePingRMI() throws RemoteException;
}
