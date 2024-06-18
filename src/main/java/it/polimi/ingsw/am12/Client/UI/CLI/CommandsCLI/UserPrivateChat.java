package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

import it.polimi.ingsw.am12.Network.Messages.Events.ChatEvent;
import it.polimi.ingsw.am12.Message;

/**
 * A user sends a private message in the chat
 */
public class UserPrivateChat implements UserAction{

    private String nickname;


    /**
     * Create a message to send a private message in the chat
     * @param params the string command for chat
     * @return a ChatEvent
     */
    @Override
    public Message createMessage(String params) {
        String[] parameters = params.split("\\s+");

        String recipient = parameters[0];
        String message = "";
        for(int i = 1; i < parameters.length; i++)
        {
            message += parameters[i] + " ";
        }

        commandParametersSent(recipient);
        return new ChatEvent(nickname, recipient, false, message);

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
     * @param recipient of the message
     */
    private static void commandParametersSent(String recipient) {
        System.out.println("Asked to send a private message to: " + recipient);
    }
}
