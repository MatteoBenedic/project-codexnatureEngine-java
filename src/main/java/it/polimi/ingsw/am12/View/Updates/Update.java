package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.Message;
import it.polimi.ingsw.am12.ViewModelUpdater;

/**
 * Interface that defines and Update of the model state to update the client
 */
public interface Update extends Message {

    /**
     * Update the ViewModel
     * @param viewModelUpdater the ViewModel to update
     */
    void executeUpdate(ViewModelUpdater viewModelUpdater);
}
