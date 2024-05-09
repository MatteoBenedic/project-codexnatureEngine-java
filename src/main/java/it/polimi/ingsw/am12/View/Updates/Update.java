package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.MemoryUpdater;
import it.polimi.ingsw.am12.Message;



/**
 * Interface that defines and Update of the model state to update the client
 */
public interface Update extends Message {

    //TODO: da sostituire con update del ViewModel
    public String toString(String receiver);

    public String getTurn();

    public void executeUpdate(MemoryUpdater memoryUpdater);
}
