package it.polimi.ingsw.am12.CommandsCLI;
import it.polimi.ingsw.am12.CLI.CLI;

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

}

