package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.MemoryUpdater;
import it.polimi.ingsw.am12.Utils.Coordinate;
import java.util.List;

/**
 * Update to handle when the player asks for placeable position around a card .
 */
public class PlaceablePositionsReturnedUpdate implements Update {

    List<Coordinate> availablePositions;

    String nickname;

    String turn;

    /**
     * Instantiates a new Placeable positions returned update.
     *
     * @param nickname           the nickname of player
     * @param availablePositions the available positions around the card
     * @param turn               the turn
     */
    public PlaceablePositionsReturnedUpdate(String nickname, List<Coordinate> availablePositions, String turn){
        this.nickname=nickname;
        this.availablePositions = availablePositions;
        this.turn=turn;
    }

    public String getTurn() {
        return turn;
    }

    @Override
    public void executeUpdate(MemoryUpdater memoryUpdater) {

    }

    /**
     * Gets available positions.
     *
     * @return the available positions
     */
    public List<Coordinate> getAvailablePositions() {
        return availablePositions;
    }

    /**
     * Gets nickname.
     *
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    public String toString(String receiver) {
        return receiver;
    }
}
