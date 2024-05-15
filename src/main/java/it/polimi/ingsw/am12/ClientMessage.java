package it.polimi.ingsw.am12;

/**
 * This class represents a message in the chat, from the perspective of the client
 */
public class ClientMessage {

    String message;
    boolean isPublic;
    String sender;

    /**
     * Class constructor
     * @param sender the nickname of the user who sent the message
     * @param isPublic a boolean that indicated if the message is public (TRUE) or private (FALSE)
     * @param message the content of the message
     */
    public ClientMessage(String sender, boolean isPublic, String message ){
        this.sender = sender;
        this.isPublic = isPublic;
        this.message = message;
    }
}
