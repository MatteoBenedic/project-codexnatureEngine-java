package it.polimi.ingsw.am12.Model.CardDesign.GameCard;

/**
 * This class defines a specific visible corner of a side of a card.
 * If in the array of corners, the pointer is null, this means in that specific position the corner is hidden.
 * If the element in the corner returns a null pointer, it means there's no element on that visible corner
 */
public class Corner {
    private Element elem;

    /**
     * @return the element located in this specific corner
     */
    public Element getElement() {
        return elem;
    }
}
