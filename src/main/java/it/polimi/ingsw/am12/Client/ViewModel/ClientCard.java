package it.polimi.ingsw.am12.Client.ViewModel;

import it.polimi.ingsw.am12.Utils.Coordinate;

/**
 * This class represents a game card on the playing grid, from the perspective client (index, position and side)
 */
public class ClientCard {

    private Coordinate coordinates;
    private boolean side;
    private int index;

    /**
     * Class constructor
     *
     * @param index       the index of the card
     * @param side        the side on which the card has been played (front = TRUE, back = FALSE)
     * @param coordinates the position on the playing grid
     */
    public ClientCard(int index, boolean side, Coordinate coordinates) {
        this.index = index;
        this.side = side;
        this.coordinates = coordinates;
    }

    /**
     * Get the coordinates of the card on the playing grid
     * @return the coordinates of the card on the playing grid
     */
    public Coordinate getCoordinates(){
        return coordinates;
    }

    /**
     * Get the side on which tha card was placed
     * @return TRUE if the card was placed on the front; FALSE if it was placed on the back
     *
     */
    public boolean getSide() {
        return side;
    }

    /**
     * Get the index of the card
     * @return the index of the card
     */
    public int getIndex(){
        return index;
    }

}
