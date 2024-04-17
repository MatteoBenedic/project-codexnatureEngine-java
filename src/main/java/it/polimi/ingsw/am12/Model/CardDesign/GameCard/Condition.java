package it.polimi.ingsw.am12.Model.CardDesign.GameCard;

/**
 * This class is used to define the conditions that can multiply the points of the side of the card
 * where this condition is located. The points have to be multiplied the number of times this
 * condition is achieved.
 */
public class Condition {
    private String type;
    private Element object;

    /**
     * @return the object of the condition, if there's one, null if not
     */
    public Element getObject() {
        return object;
    }

    /**
     * @return the type of condition, "corner" or "object" type:
     *      corner condition multiplies the points how many times this card covers other cards when placed
     *      object or element condition multiplies the points how many times there's the specific element
     *          defined on the card
     */
    public String getType() {
        return type;
    }
}
