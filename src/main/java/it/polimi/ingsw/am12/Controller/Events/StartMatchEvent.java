package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Model.Logic.WrongNumberOfPlayersException;
import it.polimi.ingsw.am12.View.VirtualView;
import java.util.List;

public class StartMatchEvent implements Event{
    @Override
    public void executeCommand(GameModel model, List<VirtualView> views) {
        try {
            model.addPlayersToMatch();
        }
        catch (WrongNumberOfPlayersException e){
            for(VirtualView view : views) {
                view.setMessage(e.getMessage());
            }
        }
    }
}
