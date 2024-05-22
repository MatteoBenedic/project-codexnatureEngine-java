package it.polimi.ingsw.am12.Network.Messages;

import it.polimi.ingsw.am12.Message;

/**
 * This class defines all the first messages accepted by the server from the client. The latter has to send the nickname
 * with whom wants to be identified in the whole server
 */
public class NicknameMessage implements Message {
    String nickname;

    /**
     * Class constructor
     * @param nickname the identifier name chosen by the player
     */
    public NicknameMessage(String nickname){
        this.nickname = nickname;
    }

    /**
     * @return the nickname of the player
     */
    public String getNickname() {
        return nickname;
    }
}
