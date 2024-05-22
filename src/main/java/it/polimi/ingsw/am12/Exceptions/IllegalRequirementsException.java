package it.polimi.ingsw.am12.Exceptions;

public class IllegalRequirementsException extends Exception{

    /**
     * Exception class:
     * Thrown if some illegal requirement is made
     * @param message an error message
     */

    public IllegalRequirementsException(String message) {
        super(message);
    }

}


