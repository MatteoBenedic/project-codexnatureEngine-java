package it.polimi.ingsw.am12.Model.CardDesign.GameCard;

import java.util.List;

/**
 * This class is used to define the front side of gold cards
 */
public class GoldFront extends Side {
    private List<Element> requirements;
    private Condition condition;

    /**
     * @return list of elements needed to place the card on this side
     */
    @Override
    public List<Element> getRequirements() {
        return requirements;
    }

    /**
     * @return Condition (type and object needed in case)
     * to reach to multiply the points given from this side
     */
    @Override
    public Condition getCondition() {
        return condition;
    }

    /**
     * Gets the list of elements in the center of the side
     * @return null, because there's no elements in the center of a front side
     */
    @Override
    public List<Element> getCenter() {
        return null;
    }
}
