package it.polimi.ingsw.am12.Exceptions;

/**
 * This exception is thrown when a user tries to subscribe with a nickname that is already in use.
 */
public class DuplicateNicknameException extends Exception{
    /**
     * Class constructor
     */
    public DuplicateNicknameException(String message) {
        super(message);
    }
}