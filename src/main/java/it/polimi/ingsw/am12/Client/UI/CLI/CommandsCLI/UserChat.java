package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

import it.polimi.ingsw.am12.Network.Messages.Events.ChatEvent;
import it.polimi.ingsw.am12.Message;

public class UserChat implements UserAction{

    String nickname;


    /**
     * Create a message to send a message in the chat
     * @param params the string command for chat
     * @return a ChatEvent
     */
    @Override
    public Message createMessage(String params) {
        String[] parameters = params.split("\\s+");
        String paramPublic;

        paramPublic = parameters[0];

        if(!paramPublic.equals("public") && !paramPublic.equals("private")) {
            System.out.println("Invalid paramater: the first parameter must be 'public' or 'private'");
        }

        boolean isPublic = true;
        if(paramPublic.equals("private")){
            isPublic = false;

        }

        String recipient = parameters[1];
        String message = "";
        for(int i = 2; i < parameters.length; i++)
        {
            message += parameters[i] + " ";
        }

        commandParametersSent(isPublic, recipient);
        return new ChatEvent(nickname, recipient, isPublic, message);

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
     * @param isPublic (true=message for all players; false = message for a single player)
     * @param recipient of the message
     */
    private static void commandParametersSent(boolean isPublic, String recipient) {
        if (isPublic)
            System.out.println("Asked to send a public message");
        else
            System.out.println("Asked to send a private message to: " + recipient);
    }
}
