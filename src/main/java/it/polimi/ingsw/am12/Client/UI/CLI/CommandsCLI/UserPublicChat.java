package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

import it.polimi.ingsw.am12.Message;
import it.polimi.ingsw.am12.Network.Messages.Events.ChatEvent;

/**
 * A user sends a public message in the chat
 */
public class UserPublicChat implements UserAction{

    private String nickname;

    /**
     * Create a Message to send a public message in the chat
     * @param params the string command
     * @return a ChatEvent
     */
    @Override
    public Message createMessage(String params) {

        String[] parameters = params.split("\\s+");

        String message = "";
        for(int i = 0; i < parameters.length; i++)
        {
            message += parameters[i] + " ";
        }

        commandParametersSent();
        return new ChatEvent(nickname, "all", true, message);
    }

    /**
     * Set the nickname
     * @param nickname the nickname of the user who requested the action
     */
    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Print a summary of the requested action
     */
    private static void commandParametersSent() {
        System.out.println("Asked to send a public message");
    }
}
