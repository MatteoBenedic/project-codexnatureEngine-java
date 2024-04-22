package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.DuplicateNicknameException;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Model.Logic.WrongNumberOfPlayersException;
import it.polimi.ingsw.am12.View.VirtualView;
import java.util.List;

public class JoinMatchEvent implements Event{

    private String nickname;
    public JoinMatchEvent(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void executeCommand(GameModel model, List<VirtualView> views) {
        try {
            model.addPlayerToLobby(nickname);
        }
        catch (WrongNumberOfPlayersException | DuplicateNicknameException e){
            for(VirtualView view : views) {
                if(view.getNickname().equals(nickname)) {
                    view.setMessage(e.getMessage());
                }
            }
        }
    }
}
