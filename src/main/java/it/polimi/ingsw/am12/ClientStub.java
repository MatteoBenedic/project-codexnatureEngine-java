package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.View.Updates.Update;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientStub extends Remote, Serializable {

    void catchMessage(Update u) throws RemoteException;
}
