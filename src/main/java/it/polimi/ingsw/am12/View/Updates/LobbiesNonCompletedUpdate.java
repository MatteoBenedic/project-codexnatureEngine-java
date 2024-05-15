package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.ViewModelUpdater;
import java.util.Map;

/**
 * Update to handle the request of non completed lobbies
 */
public class LobbiesNonCompletedUpdate implements Update{
    Map<String, Integer> lobbies;

    /**
     * Class constructor
     * @param lobbies the list of non completed lobbies
     */
    public LobbiesNonCompletedUpdate(Map<String, Integer> lobbies){
        this.lobbies = lobbies;
    }

    /**
     * Update the ViewModel
     * @param viewModelUpdater the ViewModel to update
     */
    public void executeUpdate(ViewModelUpdater viewModelUpdater) {
        viewModelUpdater.lobbiesNonCompletedUpdate(lobbies);
    }
}
