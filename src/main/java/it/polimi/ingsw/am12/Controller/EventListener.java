package it.polimi.ingsw.am12.Controller;

import it.polimi.ingsw.am12.Network.Messages.Events.Event;
import it.polimi.ingsw.am12.Exceptions.*;

import java.security.InvalidParameterException;

/**
 * Interface that defines an event listener:
 * requires method actionPerformed
 */
public interface EventListener {
    /**
     * Listen to an Event and execute the corresponding command
     * @param e the listened Event
     */
    void actionPerformed(Event e) throws WrongNumberOfPlayersException, DuplicateNicknameException,
            IllegalStateException, InvalidPlacementException, WrongInformationException, NotYourTurnException,
            InvalidParameterException, EmptyDeckException, InvalidSearchPositionException;
}
