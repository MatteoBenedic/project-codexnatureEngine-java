package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.EmptyDeckException;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Model.Logic.NotYourTurnException;
import it.polimi.ingsw.am12.Model.Logic.WrongInformationException;
import it.polimi.ingsw.am12.View.VirtualView;

import java.security.InvalidParameterException;
import java.util.List;

public class DrawCardEvent implements Event{

    String nickname;
    int deckIndex;

    public DrawCardEvent(String nickname, int deckIndex){
        this.nickname=nickname;
        this.deckIndex=deckIndex;
    }

    @Override
    public void executeCommand(GameModel model) throws InvalidParameterException, EmptyDeckException,
            NotYourTurnException, WrongInformationException, IllegalStateException{

            model.drawCard(nickname, deckIndex);

    }
}
