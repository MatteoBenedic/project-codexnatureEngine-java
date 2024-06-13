package it.polimi.ingsw.am12.Network.Messages.Events;

import it.polimi.ingsw.am12.Exceptions.EmptyDeckException;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.am12.Exceptions.IllegalStateException;

/**
 * Event to start the match
 */
public class StartMatchEvent extends Event{

    /**
     * Add the users in the lobby as players of the matc
     * @param model the GameModel that interacts with this event
     * @throws WrongNumberOfPlayersException if the number of nicknames in the lobby differs from the
     *                                       number of players of the match. In this case no player is added.
     * @throws IllegalStateException if the method has been invoked at an illegal or inappropriate time.
     */
    @Override
    public void executeCommand(GameModel model) throws WrongNumberOfPlayersException, IllegalStateException, EmptyDeckException {
            model.addPlayersToMatch();
    }
}
