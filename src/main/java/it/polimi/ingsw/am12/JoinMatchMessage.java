package it.polimi.ingsw.am12;

import java.io.Serializable;

public class JoinMatchMessage implements Serializable {

    String nickname;
    String matchName;

    public JoinMatchMessage(String nickname, String matchName) {
        this.nickname = nickname;
        this.matchName = matchName;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMatchName() {
        return matchName;
    }
}
