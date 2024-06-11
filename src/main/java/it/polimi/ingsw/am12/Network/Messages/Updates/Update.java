package it.polimi.ingsw.am12.Network.Messages.Updates;

import it.polimi.ingsw.am12.*;
import it.polimi.ingsw.am12.Exceptions.*;

import java.rmi.RemoteException;

/**
 * Abstract class that defines and Update of the model state to update the client
 */
public abstract class Update implements Message{

    /**
     * Update the ViewModel
     * * Each subclass of Update overrides this method
     * @param viewModelUpdater the ViewModel to update
     */
    public void executeUpdate(ViewModelUpdater viewModelUpdater) {

    }

    public void sendRMI(ClientStub client, ServerStub server, VVStub vv, String nickname) {};
}
