package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.*;

import java.security.InvalidParameterException;

/**
 * Event to send a message in the chat to all the other players or to a specific one
 */
public class ChatEvent implements Event{
    String nickname;
    String addressee;
    boolean publicMess;
    String chatMessage;

    public ChatEvent (String nickname, String addressee, boolean publicMess, String chatMessage){
        this.nickname = nickname;
        this.addressee = addressee;
        this.publicMess = publicMess;
        this.chatMessage = chatMessage;
    }

    @Override
    public void executeCommand(GameModel model) throws WrongNumberOfPlayersException, DuplicateNicknameException, IllegalStateException, InvalidPlacementException, WrongInformationException, NotYourTurnException, InvalidParameterException, EmptyDeckException, InvalidSearchPositionException {
        model.manageChat(nickname, addressee, publicMess, chatMessage);
    }
}
