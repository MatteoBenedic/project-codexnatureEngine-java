package it.polimi.ingsw.am12.Network.Messages.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Exceptions.InvalidSearchPositionException;
import it.polimi.ingsw.am12.Exceptions.NotYourTurnException;
import it.polimi.ingsw.am12.Exceptions.WrongInformationException;

import java.security.InvalidParameterException;

/**
 * Event for placeable position around a card
 */
public class GetPlaceablePositionsEvent implements Event{

    String nickname;
    int x;
    int y;

    /**
     * Instantiates a new GetPlaceablePos m event.
     *
     * @param nickname of the player
     * @param x row index of grid
     * @param y col index of grid
     */
    public GetPlaceablePositionsEvent(String nickname, int x, int y){
        this.nickname=nickname;
        this.x=x;
        this.y=y;
    }

    @Override
    /**
     * Check which positions are available for placing, around a selected card.
     *
     * @throws IllegalStateException if the method has been invoked at an illegal or inappropriate time.
     * @throws InvalidParameterException if the nickname is null
     * @throws WrongInformationException if the player is not part of this match
     * @throws NotYourTurnException if it's not the turn of the player
     * @throws InvalidSearchPositionException if the given position is invalid.
     *
     */

    public void executeCommand(GameModel model) throws InvalidSearchPositionException, NotYourTurnException ,
            WrongInformationException, InvalidParameterException, IllegalStateException{

            model.getPlaceablePositions(nickname, x, y);

    }
}
