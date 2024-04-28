package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.EmptyDeckException;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.View.VirtualView;

import java.util.List;

/**
 * Event DistributeCardsEvent
 */
public class DistributeCardsEvent implements Event{

    //public DistributeCardsEvent() {}

    /**
     * Distribute cards to the players, in order to start the match.
     * @param model the GameModel that interacts with this event
     * @throws EmptyDeckException if Deck is empty
     * @throws IllegalStateException if it's not state DRAW
     */
    @Override
    public void executeCommand(GameModel model) throws EmptyDeckException, IllegalStateException {

            model.distributeCards();

    }
}
