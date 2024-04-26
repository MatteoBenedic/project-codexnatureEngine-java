package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.DuplicateNicknameException;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Model.Logic.WrongNumberOfPlayersException;
import it.polimi.ingsw.am12.View.VirtualView;
import java.util.List;

public class JoinMatchEvent implements Event{

    private String nickname;
    private VirtualView view;
    public JoinMatchEvent(String nickname, VirtualView view) {
        this.nickname = nickname;
        this.view = view;
    }

    @Override
    public void executeCommand(GameModel model) throws WrongNumberOfPlayersException, DuplicateNicknameException,
            IllegalStateException{
            model.addPlayerToLobby(nickname, view);
    }
}
