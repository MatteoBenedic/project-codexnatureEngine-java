package it.polimi.ingsw.am12.Network.Messages.Events;

import it.polimi.ingsw.am12.Exceptions.InvalidPlacementException;
import it.polimi.ingsw.am12.Exceptions.NotYourTurnException;
import it.polimi.ingsw.am12.Exceptions.WrongInformationException;
import it.polimi.ingsw.am12.Model.Logic.GameModel;

import java.security.InvalidParameterException;

public class PlaceStartCardEvent implements Event {

    String nickname;
    boolean selectedSide;

/**
 * Instantiates a new PlaceCard  event.
 *
 * @param nickname of the player
 * @param selectedSide TRUE = front side, FALSE = back side
 *
 */
    public PlaceStartCardEvent(String nickname, boolean selectedSide) {
        this.nickname = nickname;
        this.selectedSide = selectedSide;
    }

    @Override

    /**
     *
     * place the starcard for the player
     *
     * @throws IllegalStateException if the method has been invoked at an illegal or inappropriate time.
     * @throws InvalidParameterException if the nickname is null
     * @throws WrongInformationException if the player is not part of this match
     * @throws NotYourTurnException  if it's not the turn of the player
     * @throws InvalidPlacementException  if a start card has already been placed for the player
     */
    public void executeCommand(GameModel model) throws WrongInformationException, NotYourTurnException,
            IllegalStateException, InvalidParameterException, InvalidPlacementException {

            model.placeStartCard(nickname, selectedSide);
    }
}
