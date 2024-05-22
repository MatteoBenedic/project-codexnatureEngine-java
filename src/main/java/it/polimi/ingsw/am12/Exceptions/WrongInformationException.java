package it.polimi.ingsw.am12.Exceptions;

/**
 * This exception is thrown when a method of the Model is invoked,
 * but the parameter contain wrong information.
 */
public class WrongInformationException extends Exception{

    /**
     * Class constructor, specifying message
     * @param message A String message that describes the exception
     */
    public WrongInformationException(String message) {
        super(message);
    }
}
