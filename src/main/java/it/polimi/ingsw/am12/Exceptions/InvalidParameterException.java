package it.polimi.ingsw.am12.Exceptions;

/**
 * This exception is thrown if a method has been invoked with an unexpected parameter.
 */
public class InvalidParameterException extends Exception {

    /**
     * Class constructor
     */
    public InvalidParameterException(String message) {
        super(message);
    }
}
