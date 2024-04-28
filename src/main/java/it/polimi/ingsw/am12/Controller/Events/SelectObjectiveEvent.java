package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Model.Logic.NotYourTurnException;
import it.polimi.ingsw.am12.Model.Logic.WrongInformationException;
import it.polimi.ingsw.am12.View.VirtualView;

import java.security.InvalidParameterException;
import java.util.List;

public class SelectObjectiveEvent implements Event{

    String nickname;
    boolean selectedObjective;

    /**
     * Instantiates a new SetObjective event.
     *
     * @param nickname of the player
     * @param selectedObjective A boolean that indicates the selected objective
     *                        TRUE = first objective; FALSE = second objective
     */
    public SelectObjectiveEvent(String nickname, boolean selectedObjective) {
        this.nickname = nickname;
        this.selectedObjective = selectedObjective;
    }

    @Override

    /**
     * assigne objective to player
     *
     * @throws IllegalStateException if the method has been invoked at an illegal or inappropriate time.
     * @throws InvalidParameterException if any of the parameters is null
     * @throws WrongInformationException if the player is not part of this match
     * @throws NotYourTurnException  if it's not the turn of the player
     */
    public void executeCommand(GameModel model) throws WrongInformationException, NotYourTurnException,
            InvalidParameterException, IllegalStateException {

            model.setPlayerObjective(nickname, selectedObjective);

    }
}
