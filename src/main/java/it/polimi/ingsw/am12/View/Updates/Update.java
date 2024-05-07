package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.MemoryUpdater;

import java.io.Serializable;

/**
 * Interface that defines and Update of the model state to update the client
 */
public interface Update extends Serializable {

    //TODO: da sostituire con update del ViewModel
    public String toString(String receiver);

    public String getTurn();

    public void executeUpdate(MemoryUpdater memoryUpdater);
}
