package it.polimi.ingsw.am12.CommandsCLI;

import it.polimi.ingsw.am12.JoinMatchMessage;
import it.polimi.ingsw.am12.Message;

/**
 * A user wants to join a match
 */
public class UserJoinMatch implements UserAction{

    /**
     * Create a Message to join a match
     * @param params the string command
     * @return a JoinMatchMessage
     */
    @Override
    public Message createMessage(String params) {
        //String[] parameters = params.split(" ");
        String[] parameters = params.split("\\s+");
        String matchName = parameters[0];
        commandParametersSent(matchName);
        return new JoinMatchMessage(matchName);
    }

    /**
     * Print a summary of the requested action
     * @param matchName the name of the joined match
     */
    private static void commandParametersSent(String matchName) {
        System.out.println("Asked to join match: " + matchName);
    }

    /**
     * Set the nickname
     * @param nickname the nickname of the user who requested the action
     */
    @Override
    public void setNickname(String nickname) {}
}
