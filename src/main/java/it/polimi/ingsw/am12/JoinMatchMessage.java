package it.polimi.ingsw.am12;

import java.io.Serializable;

public class JoinMatchMessage implements Serializable {

    String nickname;
    String matchName;
    ConnectionType connectionType;

    public JoinMatchMessage(String nickname, String matchName, ConnectionType connectionType) {
        this.nickname = nickname;
        this.matchName = matchName;
        this.connectionType = connectionType;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMatchName() {
        return matchName;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }
}
