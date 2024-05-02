package it.polimi.ingsw.am12;

import java.io.Serializable;

public class CreateMatchMessage implements Serializable {

    String nickname;
    int numPlayers;
    String matchName;


    public CreateMatchMessage(String nickname, int numPlayers, String matchName) {
        this.nickname = nickname;
        this.numPlayers = numPlayers;
        this.matchName = matchName;
    }


    public String getNickname() {
        return nickname;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public String getMatchName() {
        return matchName;
    }
}
