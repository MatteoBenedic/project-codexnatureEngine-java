package it.polimi.ingsw.am12.Controller;

import it.polimi.ingsw.am12.Exceptions.IllegalStateException;
import it.polimi.ingsw.am12.Network.Messages.Events.Event;
import it.polimi.ingsw.am12.Exceptions.*;

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
            InvalidPlacementException, WrongInformationException, NotYourTurnException,
            EmptyDeckException, InvalidSearchPositionException, IllegalStateException, InvalidParameterException;
}
