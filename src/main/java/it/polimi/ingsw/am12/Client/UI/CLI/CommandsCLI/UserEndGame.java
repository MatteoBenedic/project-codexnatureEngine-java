package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

import it.polimi.ingsw.am12.Network.Messages.Events.EndGameEvent;
import it.polimi.ingsw.am12.Message;

/**
 * A user wants to end the game
 */
public class UserEndGame implements UserAction{

    /**
     * Create a Message to end the game
     * @param params the string command
     * @return a EndGameEvent
     */
    @Override
    public Message createMessage(String params) {
        gameEndedEventSent();
        return new EndGameEvent();
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
    public void gameEndedEventSent(){
        System.out.println("Asked to end the game");
    }
}
