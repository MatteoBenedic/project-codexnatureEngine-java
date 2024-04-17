package it.polimi.ingsw.am12.Model.CardDesign.GameCard;


import java.util.Collections;
import java.util.List;

/**
 * This class is used to define the back of the gold and resource cards,
 * which have an element in the center defined by their colour
 */
public class ColouredBack extends Side {
    private Element center;

    /**
     * Gets the element in the center of the card
     * @return the element as an object in a list (standardized to the other classes)
     */
    @Override
    public List<Element> getCenter() {
        return Collections.singletonList(center);
    }
}
