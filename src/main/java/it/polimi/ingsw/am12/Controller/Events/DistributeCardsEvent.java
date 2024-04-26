package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.EmptyDeckException;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.View.VirtualView;

import java.util.List;

public class DistributeCardsEvent implements Event{

    public DistributeCardsEvent() {}

    @Override
    public void executeCommand(GameModel model) throws EmptyDeckException, IllegalStateException {

            model.distributeCards();

    }
}
