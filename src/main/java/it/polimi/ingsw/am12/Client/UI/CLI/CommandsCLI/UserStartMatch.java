package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

import it.polimi.ingsw.am12.Network.Messages.Events.StartMatchEvent;
import it.polimi.ingsw.am12.Message;

/**
 * A user wants to start the match
 */
public class UserStartMatch implements UserAction{

    /**
     * Create a Message to start the match
     * @param params the string command
     * @return a StartMatchEvent
     */
    @Override
    public Message createMessage(String params) {
            matchStartedEventSent();
            return new StartMatchEvent();
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
    private static void matchStartedEventSent() {
        System.out.println("Asked to start the match");
    }
}
