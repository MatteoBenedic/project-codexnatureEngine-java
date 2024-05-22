package it.polimi.ingsw.am12.Network.Messages.Events;

import it.polimi.ingsw.am12.Exceptions.EmptyDeckException;
import it.polimi.ingsw.am12.Model.Logic.GameModel;

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
