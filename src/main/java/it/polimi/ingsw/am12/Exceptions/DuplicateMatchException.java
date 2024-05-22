package it.polimi.ingsw.am12.Exceptions;

/**
 * This exception is thrown when an attempt is made to create a match, but
 * there's already a match with the same name.
 */
public class DuplicateMatchException extends Exception{
    /**
     * Class constructor, specifying message
     * @param message A String message that describes the exception
     */
    public DuplicateMatchException(String message) {
        super(message);
    }
}
