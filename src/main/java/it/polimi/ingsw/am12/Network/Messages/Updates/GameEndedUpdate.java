package it.polimi.ingsw.am12.Network.Messages.Updates;

import java.util.LinkedHashMap;

import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.ViewModelUpdater;

/**
 * Update to handle the end of the game, in order to show the results
 */
public class GameEndedUpdate implements Update {

    int winners;
    LinkedHashMap<String, Integer> classification;
    State state;

    /**
     * Class constructor
     * @param winners the number of winners
     * @param classification the classification of the match, with the final points of each player
     * @param state the state of the game (CLOSED)
     */
    public GameEndedUpdate(int winners, LinkedHashMap<String, Integer> classification, State state) {
        this.winners = winners;
        this.classification = classification;
        this.state = state;
    }

    /**
     * Update the ViewModel
     * @param viewModelUpdater the ViewModel to update
     */
    @Override
    public void executeUpdate(ViewModelUpdater viewModelUpdater) {
        viewModelUpdater.gameEndedUpdate(winners, classification, state);
    }
}
