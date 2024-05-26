package it.polimi.ingsw.am12.Network.Messages.Updates;

import it.polimi.ingsw.am12.ViewModelUpdater;

public class GameStoppedUpdate implements Update{
    /**
     * Update the ViewModel (notifies that the game has been stopped)
     * @param viewModelUpdater the ViewModel to update
     */
    @Override
    public void executeUpdate(ViewModelUpdater viewModelUpdater) {
        viewModelUpdater.gameStoppedUpdate();
    }
}
