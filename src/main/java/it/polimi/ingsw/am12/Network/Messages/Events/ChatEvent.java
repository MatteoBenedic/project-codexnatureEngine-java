package it.polimi.ingsw.am12.Network.Messages.Events;

import it.polimi.ingsw.am12.Exceptions.*;
import it.polimi.ingsw.am12.Model.Logic.GameModel;

/**
 * Event to send a message in the chat to all the other players or to a specific one
 */
public class ChatEvent extends Event{
    private final String nickname;
    private final String addressee;
    private final boolean publicMess;
    private final String chatMessage;

    /**
     * Class constructor
     * @param nickname the nickname of the player who sent the message
     * @param addressee the nickname of the player to whom the message is addressed
     * @param publicMess a boolean: TRUE if the message is public, FALSE if the message is private
     * @param chatMessage the content of the message
     */
    public ChatEvent (String nickname, String addressee, boolean publicMess, String chatMessage){
        this.nickname = nickname;
        this.addressee = addressee;
        this.publicMess = publicMess;
        this.chatMessage = chatMessage;
    }

    /**
     * Send a message in the chat
     * @param model the GameModel that interacts with this event
     * @throws WrongInformationException  if the sender or the addressee is not part of this match
     * @throws InvalidParameterException if a parameter is null or empty
     */
    @Override
    public void executeCommand(GameModel model) throws WrongInformationException, InvalidParameterException {
        model.manageChat(nickname, addressee, publicMess, chatMessage);
    }
}
