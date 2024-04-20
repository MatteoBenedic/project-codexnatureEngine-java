package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.*;
import it.polimi.ingsw.am12.View.VirtualView;
import java.util.List;

public class PlaceStartCardEvent implements Event {

    String nickname;
    boolean selectedSide;

    public PlaceStartCardEvent(String nickname, boolean selectedSide) {
        this.nickname = nickname;
        this.selectedSide = selectedSide;
    }

    @Override
    public void executeCommand(GameModel model, List<VirtualView> views) {
        try {
            model.placeStartCard(nickname, selectedSide);
        }
        catch (WrongInformationException | InvalidPlacementException | NotYourTurnException e) {
            for(VirtualView view : views) {
                view.setMessage(e.getMessage());
            }
        }
    }
}
