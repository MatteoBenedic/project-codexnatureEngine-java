package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Model.Logic.NotYourTurnException;
import it.polimi.ingsw.am12.Model.Logic.WrongInformationException;
import it.polimi.ingsw.am12.View.VirtualView;
import java.util.List;

public class SelectObjectiveEvent implements Event{

    String nickname;
    boolean selectedObjective;

    public SelectObjectiveEvent(String nickname, boolean selectedObjective) {
        this.nickname = nickname;
        this.selectedObjective = selectedObjective;
    }

    @Override
    public void executeCommand(GameModel model, List<VirtualView> views) {
        try {
            model.setPlayerObjective(nickname, selectedObjective);
        } catch(WrongInformationException | NotYourTurnException e){
            for(VirtualView view : views) {
                view.setMessage(e.getMessage());
            }
        }
    }
}
