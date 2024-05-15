package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.ViewModelUpdater;

/**
 * Update to handle a message in the chat
 */
public class ChatUpdate implements Update{
    String sender;
    boolean publicMess;
    String chatMessage;

    /**
     * Class constructor
     * @param sender tha nickname of the user who sent the message
     * @param publicMess a boolean that specifies if the message is public (TRUE) or private (FALSE)
     * @param chatMessage the content of the message
     */
    public ChatUpdate(String sender, boolean publicMess, String chatMessage) {
        this.sender = sender;
        this.chatMessage = chatMessage;
        this.publicMess = publicMess;
    }

    /**
     * Update the ViewModel
     * @param viewModelUpdater the ViewModel to update
     */
    @Override
    public void executeUpdate(ViewModelUpdater viewModelUpdater) {
        viewModelUpdater.chatUpdate(sender, publicMess, chatMessage);
    }
}
