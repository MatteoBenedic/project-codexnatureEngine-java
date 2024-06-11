package it.polimi.ingsw.am12.Network.Messages.Updates;

import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.Utils.Coordinate;
import it.polimi.ingsw.am12.ViewModelUpdater;

/**
 * Update to handle when a Card is placed.
 */
public class CardPlacedUpdate extends Update {

    private final String nickname;
    private final int index;
    private final Boolean side;
    private final int points;
    private final State state;
    private final Coordinate coordinates;

    /**
     * Class constructor
     * @param nickname the nickname of player
     * @param index    the index of the card
     * @param side     the side, True=front; False=back
     * @param points   the points given by the action
     * @param state    the state of the game (DRAWING)
     */
    public CardPlacedUpdate(String nickname, int index, Boolean side, int points, State state, Coordinate coordinate) {
        this.nickname = nickname;
        this.index = index;
        this.side = side;
        this.points = points;
        this.coordinates = coordinate;
        this.state = state;
    }

    /**
     * Update the ViewModel
     * @param viewModelUpdater the ViewModel to update
     */
    @Override
    public void executeUpdate(ViewModelUpdater viewModelUpdater) {
        viewModelUpdater.cardPlacedUpdate(nickname, index, side, points, state, coordinates);
    }
}
