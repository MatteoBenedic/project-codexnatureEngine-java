package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Model.Logic.WrongNumberOfPlayersException;
import it.polimi.ingsw.am12.View.VirtualView;
import java.util.List;

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
