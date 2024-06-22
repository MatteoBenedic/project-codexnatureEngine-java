package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

import it.polimi.ingsw.am12.Message;
import it.polimi.ingsw.am12.Network.Messages.Updates.GameStoppedUpdate;

/**
 * A user decides to stop the game
 */
public class UserStopGame implements UserAction{

    /**
     * Create a message to stop the game
     * @param params the string command
     * @return a GameStoppedUpdate
     */
    @Override
    public Message createMessage(String params) {
        return new GameStoppedUpdate();
    }

    /**
     * Set the nickname
     * @param nickname the nickname of the user who requested the action
     */
    @Override
    public void setNickname(String nickname) {

    }
}
