package it.polimi.ingsw.am12.CommandsCLI;

import it.polimi.ingsw.am12.Message;

/**
 * This interface represents a UserAction. Its implementations are used to create
 * a Message when a user requests an action (as a string command)
 */
public interface UserAction {

    /**
     * Create a message from a command
     * @param params the string command
     * @return an object the implements Message
     */
    Message createMessage(String params);

    /**
     * Set the nickname
     * @param nickname the nickname of the user who requested the action
     */
    void setNickname(String nickname);

}
