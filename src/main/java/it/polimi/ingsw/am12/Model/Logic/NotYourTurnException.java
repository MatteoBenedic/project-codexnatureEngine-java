package it.polimi.ingsw.am12.Model.Logic;

/**
 * This exception is thrown when trying to invoke a method for a player
 * and it's not his turn
 */
public class NotYourTurnException extends Exception{

    /**
     * Class constructor, specifying message
     * @param message A String message that describes the exception
     */
    public NotYourTurnException(String message) {
        super(message);
    }
}
