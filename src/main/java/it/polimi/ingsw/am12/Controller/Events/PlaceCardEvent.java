package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.*;
import it.polimi.ingsw.am12.View.VirtualView;
import java.security.InvalidParameterException;
import java.util.List;

public class PlaceCardEvent implements Event{

    String nickname;
    int index;
    boolean side;
    int xpos;
    int ypos;

    public PlaceCardEvent(String nickname, int index, boolean side, int xpos, int ypos) {
        this.nickname = nickname;
        this.index = index;
        this.side = side;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    @Override
    public void executeCommand(GameModel model) throws InvalidParameterException, InvalidPlacementException,
            NotYourTurnException, WrongInformationException, IllegalStateException{

            model.placeCard(nickname, index, side, xpos, ypos);
    }
}
