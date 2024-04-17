package it.polimi.ingsw.am12.Model.Logic;

/**
 * This exception is thrown when a method of the Model is invoked,
 * but the parameter contain information for a wrong number of players.
 */
public class WrongNumberOfPlayersException extends Exception{

    /**
     * Class constructor, specifying message
     * @param message A String message that describes the exception
     */
    public WrongNumberOfPlayersException(String message) {
        super(message);
    }
}
