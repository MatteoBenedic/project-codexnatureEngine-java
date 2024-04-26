package it.polimi.ingsw.am12;

/**
 * This exception is thrown when an attempt is made to look for a match that does not exist
 */
public class NoMatchException extends Exception{
    /**
     * Class constructor, specifying message
     * @param message A String message that describes the exception
     */
    public NoMatchException(String message) {
        super(message);
    }
}
