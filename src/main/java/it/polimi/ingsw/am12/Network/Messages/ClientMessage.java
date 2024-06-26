package it.polimi.ingsw.am12.Network.Messages;

/**
 * This class represents a message in the chat, from the perspective of the client
 */
public class ClientMessage {

    private final String message;
    private final boolean isPublic;
    private final String sender;
    private final String recipient;

    /**
     * Class constructor
     * @param sender the nickname of the user who sent the message
     * @param recipient the nickname of the recipient
     * @param isPublic a boolean that indicated if the message is public (TRUE) or private (FALSE)
     * @param message the content of the message
     */
    public ClientMessage(String sender, String recipient, boolean isPublic, String message ){
        this.sender = sender;
        this.recipient = recipient;
        this.isPublic = isPublic;
        this.message = message;
    }
}
