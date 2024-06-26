package it.polimi.ingsw.am12.Model.CardDesign.GameCard;

import java.util.List;

/**
 * This class defines all the common features and methods of the side of a card
 */
public abstract class Side {
    protected Corner[] corners;
    protected int points;


    /**
     * Gets the corner on the searched position of the array (see meaning of position in Class Corner)
     * @param i the position of the chosen corner on the array
     * @return the object corner chosen
     */
    public Corner getCorner(int i) throws IndexOutOfBoundsException{
        return corners[i];
    }

    /**
     * @return the points of the side
     */
    public int getPoints() {
        return points;
    }

    /**
     * @return list of elements required on the playing grid to place this card
     */
    public List<Element> getRequirements(){
        return null;
    }

    /**
     * @return the condition (type and in case object) that can multiply the points
     */
    public Condition getCondition(){
        return null;
    }

    /**
     * @return list of elements in the center of the side
     */
    public abstract List<Element> getCenter();
}
