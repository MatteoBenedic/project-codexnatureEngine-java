package it.polimi.ingsw.am12.Network.Messages.Updates;

import java.util.List;
import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.ViewModelUpdater;

/**
 * Update to handle when some players have joined the lobby
 */
public class PlayersAddedUpdate implements Update{

    List<String> nicknames;
    State state;

    /**
     * Class constructor
     * @param nicknames the list of nicknames of the players who have joined the lobby
     * @param state     the state of the game (LOBBY or INITIALIZATION)
     */
    public PlayersAddedUpdate(List<String> nicknames, State state) {
        this.nicknames = nicknames;
        this.state = state;
    }

    /**
     * Update the ViewModel
     * @param viewModelUpdater the ViewModel to update
     */
    @Override
    public void executeUpdate(ViewModelUpdater viewModelUpdater) {
        viewModelUpdater.playersAddedUpdate(nicknames, state);
    }
}
