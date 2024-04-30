package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.View.Updates.*;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The client of a player (RMI)
 */
public class Client extends UnicastRemoteObject implements ClientStub {
    RMISimulator sim;

    public Client(RMISimulator sim) throws RemoteException {
        this.sim = sim;
    }

    public void catchMessage(Update update){
        sim.setUpdate(update);
    }



}
