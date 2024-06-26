package it.polimi.ingsw.am12.Network.Messages.Events;

import it.polimi.ingsw.am12.Exceptions.InvalidParameterException;
import it.polimi.ingsw.am12.Exceptions.InvalidPlacementException;
import it.polimi.ingsw.am12.Exceptions.NotYourTurnException;
import it.polimi.ingsw.am12.Exceptions.WrongInformationException;
import it.polimi.ingsw.am12.Exceptions.IllegalStateException;
import it.polimi.ingsw.am12.Model.Logic.GameModel;

/**
 * Event to place a card
 */
public class PlaceCardEvent extends Event{

    private final String nickname;
    private final int index;
    private final boolean side;
    private final int xpos;
    private final int ypos;

    /**
     * Class constructor
     * @param nickname the nickname of the player
     * @param side TRUE = front side, FALSE = back side
     * @param xpos the row index of the selected position on the grid
     * @param ypos the col index of the selected position on the grid
     */
    public PlaceCardEvent(String nickname, int index, boolean side, int xpos, int ypos) {
        this.nickname = nickname;
        this.index = index;
        this.side = side;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    /**
     * Place the card for the player
     * @param model the GameModel that interacts with this event
     * @throws IllegalStateException if the method has been invoked at an illegal or inappropriate time.
     * @throws InvalidParameterException if any of the parameters null or out of bounds
     * @throws WrongInformationException if the player is not part of this match
     * @throws NotYourTurnException  if it's not the turn of the player
     * @throws InvalidPlacementException  if there is already a card in that position, or the requirements are not satisfied
     */
    @Override
    public void executeCommand(GameModel model) throws InvalidParameterException, InvalidPlacementException,
            NotYourTurnException, WrongInformationException, IllegalStateException {

            model.placeCard(nickname, index, side, xpos, ypos);
    }
}
