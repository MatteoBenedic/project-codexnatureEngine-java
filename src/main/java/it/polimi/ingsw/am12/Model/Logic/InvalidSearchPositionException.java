package it.polimi.ingsw.am12.Model.Logic;

/**
 * This exception is thrown when trying to search for a position on the grid in an unexpected way.
 */
public class InvalidSearchPositionException extends Exception{
    /**
     * Class constructor, specifying message
     * @param message A String message that describes the exception
     */
    public InvalidSearchPositionException(String message) {
        super(message);
    }
}

