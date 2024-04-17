package it.polimi.ingsw.am12.Model.CardDesign.GameCard;

import java.util.List;

/**
 * This class is used to define the front of initial cards and resource cards
 */
public class NotGoldFront extends Side {

    /**
     * Gets the list of elements in the center of the side
     * @return null, because the front doesn't have any elements in the center
     */
    @Override
    public List<Element> getCenter() {
        return null;
    }
}
