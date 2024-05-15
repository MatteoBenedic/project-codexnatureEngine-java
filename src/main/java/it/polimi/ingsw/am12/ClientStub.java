package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.View.Updates.Update;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface that defines the methods used by the server or the communication handler of the client to update the client
 * about the new state of the match or lobby
 */
public interface ClientStub extends Remote, Serializable {

    /**
     * Receive an update from the server and modify the ViewModel accordingly
     * @param u the received update
     */
    void catchMessage(Update u) throws RemoteException;
}
