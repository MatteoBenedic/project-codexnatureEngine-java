package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;

public class EndGameEvent implements Event{

    @Override
    public void executeCommand(GameModel model) throws IllegalStateException {
        model.endGame();
    }

}
