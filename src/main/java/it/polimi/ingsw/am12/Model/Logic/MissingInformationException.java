package it.polimi.ingsw.am12.Model.Logic;

/**
 * This exception is thrown when a method of the Model is invoked,
 * but the parameter do not contain enough information to perform the actions.
 */
public class MissingInformationException extends Exception{

    /**
     * Class constructor, specifying message
     * @param message A String message that describes the exception
     */
    public MissingInformationException(String message) {
        super(message);
    }
}