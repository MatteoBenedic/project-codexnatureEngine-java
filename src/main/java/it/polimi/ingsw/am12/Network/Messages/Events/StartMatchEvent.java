package it.polimi.ingsw.am12.Network.Messages.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Exceptions.WrongNumberOfPlayersException;

public class StartMatchEvent implements Event{
    @Override
    /**
     * Add the users in the lobby as players of the match
     * @throws WrongNumberOfPlayersException    if the number of nicknames in the lobby differs from the
     *                                          number of players of the match. In this case no player is added.
     */
    public void executeCommand(GameModel model) throws WrongNumberOfPlayersException, IllegalStateException{
            model.addPlayersToMatch();
    }
}
