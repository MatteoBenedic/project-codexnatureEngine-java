package it.polimi.ingsw.am12.Network.Messages;

import it.polimi.ingsw.am12.Message;

/**
 * This class defines the messages with the client request to join an existing match in the server.
 */
public class JoinMatchMessage implements Message {
    String matchName;

    /**
     * Class Constructor
     * @param matchName the name of the match that the player wants to join
     */
    public JoinMatchMessage(String matchName) {
        this.matchName = matchName;
    }

    /**
     * @return the name of the match to be joined
     */
    public String getMatchName() {
        return matchName;
    }
}
