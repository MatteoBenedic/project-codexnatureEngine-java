package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.View.Updates.Update;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface that defines the method used by the server or the communication handler of the client to update the client
 * about the new state of the match or lobby
 */
public interface ClientStub extends Remote, Serializable {

    void catchMessage(Update u) throws RemoteException;
}
