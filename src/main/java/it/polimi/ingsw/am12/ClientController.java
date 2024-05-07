package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.View.Updates.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The controller of the client. It manages the communication with the server or the communication layer of the client
 * and receives the updates to send to the memory
 */
public class ClientController extends UnicastRemoteObject implements ClientStub {
    RMISimulator sim;
    //MemoryUpdater memoryUpdater;

    public ClientController(RMISimulator sim) throws RemoteException {
        this.sim = sim;
    }

    public void catchMessage(Update update){
        sim.setUpdate(update);
        //update.executeUpdate(memoryUpdater);
    }
    
}
