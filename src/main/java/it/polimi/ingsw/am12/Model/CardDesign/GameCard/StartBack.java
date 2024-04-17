package it.polimi.ingsw.am12.Model.CardDesign.GameCard;

import java.util.List;

/**
 * This class is used to define the back side of the initial cards
 */
public class StartBack extends Side {
    private List<Element> center;

    /**
     * @return the lists of elements in the center of the card
     */
    @Override
    public List<Element> getCenter() {
        return center;
    }
}
