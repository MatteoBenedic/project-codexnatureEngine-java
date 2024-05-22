package it.polimi.ingsw.am12.Network.Messages.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;

/**
 * End Game event
 */
public class EndGameEvent implements Event{

    /**
     * It calculates the endgame points of the match and defines the result of it. It creates a classification ordered
     * from the winner to the worst player
     * @throws IllegalStateException if the method has been invoked at an illegal or inappropriate time.
     */
    @Override
    public void executeCommand(GameModel model) throws IllegalStateException {
        model.endGame();
    }

}
