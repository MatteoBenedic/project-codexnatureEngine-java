package it.polimi.ingsw.am12.Network.Messages.Updates;

import it.polimi.ingsw.am12.Utils.Coordinate;
import it.polimi.ingsw.am12.ViewModelUpdater;
import java.util.List;

/**
 * Update to handle when the player asks for placeable position around a card
 */
public class PlaceablePositionsReturnedUpdate extends Update {

    private final List<Coordinate> availablePositions;
    private final String nickname;

    /**
     * Class constructor
     * @param nickname           the nickname of the player
     * @param availablePositions the available positions around the card
     */
    public PlaceablePositionsReturnedUpdate(String nickname, List<Coordinate> availablePositions) {
        this.nickname = nickname;
        this.availablePositions = availablePositions;
    }

    /**
     * Update the ViewModel
     * @param viewModelUpdater the ViewModel to update
     */
    @Override
    public void executeUpdate(ViewModelUpdater viewModelUpdater) {
        viewModelUpdater.placeablePositionReturnedUpdate(nickname, availablePositions);
    }
}
