package it.polimi.ingsw.am12.Exceptions;

public class NoNicknameException extends Exception{
    /**
     * Class constructor, specifying message
     * @param message A String message that describes the exception
     */
    public NoNicknameException(String message) {
        super(message);
    }
}
