package it.polimi.ingsw.am12.Network.Messages.Updates;

import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.ViewModelUpdater;

/**
 * Update to handle when the player chooses the objective
 */
public class ObjectiveSelectedUpdate implements Update {

    String nickname;
    int secretObjective;
    String turn;
    State state;

    /**
     * Class constructor
     * @param nickname        the nickname of player
     * @param secretObjective the secret objective chosen
     * @param turn            the nickname of the player whose turn is now
     * @param state           the state of the game (OBJECTIVE or PLACING)
     */
    public ObjectiveSelectedUpdate(String nickname, int secretObjective, String turn, State state) {
        this.nickname = nickname;
        this.secretObjective = secretObjective;
        this.turn = turn;
        this.state = state;
    }

    /**
     * Update the ViewModel
     * @param viewModelUpdater the ViewModel to update
     */
    @Override
    public void executeUpdate(ViewModelUpdater viewModelUpdater) {
        viewModelUpdater.objectiveSelectedUpdate(nickname, secretObjective, turn, state);
    }
}
