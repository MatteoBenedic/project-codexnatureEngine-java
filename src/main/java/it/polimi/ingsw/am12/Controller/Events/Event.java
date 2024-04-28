package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.*;
import java.io.Serializable;
import java.security.InvalidParameterException;

/**
 * Interface that defines an event that allows the client to excute a model function
 * requires method executeCommand
 */
public interface Event extends Serializable {

    /**
     * Execute the command of a listened event
     * Each implementation of this interface Overrides this method
     * @param model the GameModel that interacts with this event
     */
    void executeCommand(GameModel model) throws WrongNumberOfPlayersException, DuplicateNicknameException,
            IllegalStateException, InvalidPlacementException, WrongInformationException, NotYourTurnException,
            InvalidParameterException, EmptyDeckException, InvalidSearchPositionException;
}
