package it.polimi.ingsw.am12.Model.Logic;

/**
 * This exception is thrown when the attempt to place a card fails.
 */
public class InvalidPlacementException extends Exception{

    /**
     * Class constructor, specifying message
     * @param message A String message that describes the exception
     */
    public InvalidPlacementException(String message) {
        super(message);
    }
}
