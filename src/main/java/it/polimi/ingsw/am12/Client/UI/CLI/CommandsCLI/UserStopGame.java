package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

import it.polimi.ingsw.am12.Message;
import it.polimi.ingsw.am12.Network.Messages.Updates.GameStoppedUpdate;

public class UserStopGame implements UserAction{
    @Override
    public Message createMessage(String params) {
        return new GameStoppedUpdate();
    }

    @Override
    public void setNickname(String nickname) {

    }
}
