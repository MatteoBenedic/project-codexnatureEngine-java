package it.polimi.ingsw.am12.Network.Messages;

import it.polimi.ingsw.am12.Message;

/**
 * This class defines the messages with the client request to close its match and server connection if it's connected
 * by sockets
 */
public class CloseMatchConnectionMessage implements Message {

    private MatchCloseMode mode;

    /**
     * Constructor method for a CloseMatchMessage
     * @param mode a MatchCloseMode
     */
    public CloseMatchConnectionMessage(MatchCloseMode mode) {
        this.mode = mode;
    }

    /**
     * Getter method for the match closing mode
     * @return a MatchCloseMode
     */
    public MatchCloseMode getMode() {
        return mode;
    }
}
