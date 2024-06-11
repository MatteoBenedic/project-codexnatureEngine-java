package it.polimi.ingsw.am12.Network.Messages.Events;

import it.polimi.ingsw.am12.Exceptions.InvalidParameterException;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Exceptions.NotYourTurnException;
import it.polimi.ingsw.am12.Exceptions.WrongInformationException;
import it.polimi.ingsw.am12.Exceptions.IllegalStateException;

/**
 * Event to select a secret objective
 */
public class SelectObjectiveEvent extends Event{

    private final String nickname;
    private final boolean selectedObjective;

    /**
     * Class constructor
     * @param nickname of the player
     * @param selectedObjective A boolean that indicates the selected objective
     *                        TRUE = first objective; FALSE = second objective
     */
    public SelectObjectiveEvent(String nickname, boolean selectedObjective) {
        this.nickname = nickname;
        this.selectedObjective = selectedObjective;
    }

    /**
     * Assign the secret objective to the player
     * @param model the GameModel that interacts with this event
     * @throws IllegalStateException if the method has been invoked at an illegal or inappropriate time.
     * @throws InvalidParameterException if any of the parameters is null
     * @throws WrongInformationException if the player is not part of this match
     * @throws NotYourTurnException  if it's not the turn of the player
     */
    @Override
    public void executeCommand(GameModel model) throws WrongInformationException, NotYourTurnException,
            InvalidParameterException, IllegalStateException {

            model.setPlayerObjective(nickname, selectedObjective);

    }
}
