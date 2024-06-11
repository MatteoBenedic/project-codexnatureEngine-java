package it.polimi.ingsw.am12.Exceptions;

/**
 * This exception thrown if a method has been invoked at an illegal or inappropriate time.
 */
public class IllegalStateException extends Exception {

    /**
     * Class constructor
     */
    public IllegalStateException(String message) {
        super(message);
    }
}
