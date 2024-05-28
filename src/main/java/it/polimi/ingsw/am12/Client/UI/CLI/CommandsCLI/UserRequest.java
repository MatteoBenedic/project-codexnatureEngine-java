package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;
import it.polimi.ingsw.am12.Client.UI.CLI.CLI;

/**
 * This interface represents a UserRequest. Its implementations are used to show the
 * information requested by the user
 */
public interface UserRequest {

    /**
     * Show the information requested
     * @param cli the cli;
     */
    void showRequest(CLI cli);

    /**
     * Set the nickname
     * @param nickname the nickname of the user who requested the action
     */
    void setNickname(String nickname);

    /**
     * Method that defines, if there is one, the integer parameter of the object
     * @param value the integer parameter to set
     */
    void setPossibleParameter(int value);

}

