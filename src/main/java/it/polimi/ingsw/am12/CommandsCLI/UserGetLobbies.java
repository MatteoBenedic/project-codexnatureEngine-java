package it.polimi.ingsw.am12.CommandsCLI;

import it.polimi.ingsw.am12.LobbiesRequestMessage;
import it.polimi.ingsw.am12.Message;

/**
 * A user wants to get the available lobbies
 */
public class UserGetLobbies implements UserAction {

    /**
     * Create a Message to get the available lobbies
     * @param params the string command
     * @return a LobbiesRequestMessage
     */
    @Override
    public Message createMessage(String params) {
        getLobbiesEventSent();
        return new LobbiesRequestMessage();
    }

    /**
     * Set the nickname
     * @param nickname the nickname of the user who requested the action
     */
    @Override
    public void setNickname(String nickname) {}

    /**
     * Print a summary of the requested action
     */
    private static void getLobbiesEventSent() {
        System.out.println("Asked for a list of available lobbies");
    }
}

