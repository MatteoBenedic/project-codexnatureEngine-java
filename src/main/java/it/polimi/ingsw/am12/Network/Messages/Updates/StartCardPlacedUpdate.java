package it.polimi.ingsw.am12.Network.Messages.Updates;

import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.ViewModelUpdater;

/**
 * Update to handle when a player places the start card
 */
public class StartCardPlacedUpdate extends Update {

    private final String nickname;
    private final int startCard;
    private final boolean side;
    private final String turn;
    private final State state;

    /**
     * Class constructor
     * @param nickname  the nickname of player
     * @param startCard the start card
     * @param side      the side (TRUE = front; FALSE = back)
     * @param turn      the nickname of the player whose turn is now
     * @param state     the state of the game (STARTCARD or COLOUR)
     */
    public StartCardPlacedUpdate(String nickname, int startCard, boolean side, String turn, State state) {
        this.nickname = nickname;
        this.startCard = startCard;
        this.side = side;
        this.turn = turn;
        this.state = state;
    }

    /**
     * Update the ViewModel
     * @param viewModelUpdater the ViewModel to update
     */
    @Override
    public void executeUpdate(ViewModelUpdater viewModelUpdater) {
        viewModelUpdater.startCardPlacedUpdate(nickname, startCard, side, turn, state);
    }

}
