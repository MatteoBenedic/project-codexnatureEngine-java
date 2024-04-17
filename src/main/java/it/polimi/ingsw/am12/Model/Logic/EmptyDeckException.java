package it.polimi.ingsw.am12.Model.Logic;

/**
 * This exception is thrown when someone tries to draw a card from a
 * deck, but there are no cards left.
 */
public class EmptyDeckException extends Exception{

    /**
     * Class constructor, specifying message
     * @param message A String message that describes the exception
     */
    public EmptyDeckException(String message) {
        super(message);
    }
}
