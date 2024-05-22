package it.polimi.ingsw.am12.Network.Messages;

import it.polimi.ingsw.am12.Message;

/**
 * This class defines the messages with the client request to create a new match in the server.
 */
public class CreateMatchMessage implements Message {
    int numPlayers;
    String matchName;

    /**
     * Class Constructor
     * @param numPlayers the number of players defined to play the match
     * @param matchName the name, and identifier, of the match to be created
     */
    public CreateMatchMessage(int numPlayers, String matchName) {
        this.numPlayers = numPlayers;
        this.matchName = matchName;
    }

    /**
     * @return the number of players
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * @return the name of the match to be created
     */
    public String getMatchName() {
        return matchName;
    }
}
