package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Model.Logic.InvalidSearchPositionException;
import it.polimi.ingsw.am12.Model.Logic.NotYourTurnException;
import it.polimi.ingsw.am12.Model.Logic.WrongInformationException;
import it.polimi.ingsw.am12.View.VirtualView;

import java.security.InvalidParameterException;
import java.util.List;

public class GetPlaceablePositionsEvent implements Event{

    String nickname;
    int x;
    int y;

    public GetPlaceablePositionsEvent(String nickname, int x, int y){
        this.nickname=nickname;
        this.x=x;
        this.y=y;
    }

    @Override
    public void executeCommand(GameModel model) throws InvalidSearchPositionException, NotYourTurnException ,
            WrongInformationException, InvalidParameterException, IllegalStateException{

            model.getPlaceablePositions(nickname, x, y);

    }
}
