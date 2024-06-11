package it.polimi.ingsw.am12.Network.Messages.Events;

import it.polimi.ingsw.am12.Exceptions.InvalidParameterException;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Exceptions.InvalidSearchPositionException;
import it.polimi.ingsw.am12.Exceptions.NotYourTurnException;
import it.polimi.ingsw.am12.Exceptions.WrongInformationException;
import it.polimi.ingsw.am12.Exceptions.IllegalStateException;

/**
 * Event for a request of placeable positions around a card
 */
public class GetPlaceablePositionsEvent extends Event{

    private final String nickname;
    private final int x;
    private final int y;

    /**
     * Class constructor
     * @param nickname the nickname of the player
     * @param x the row index of the selected card on the grid
     * @param y the col index of the selected card on the grid
     */
    public GetPlaceablePositionsEvent(String nickname, int x, int y){
        this.nickname=nickname;
        this.x=x;
        this.y=y;
    }

    /**
     * Check which positions are available for placing, around a selected card.
     * @param model the GameModel that interacts with this event
     * @throws InvalidSearchPositionException if the given position is invalid
     * @throws NotYourTurnException if it's not the turn of the player
     * @throws WrongInformationException if the player is not part of this match
     * @throws InvalidParameterException if the nickname is null
     * @throws IllegalStateException if the method has been invoked at an illegal or inappropriate time.
     */
    @Override
    public void executeCommand(GameModel model) throws InvalidSearchPositionException, NotYourTurnException ,
            WrongInformationException, InvalidParameterException, IllegalStateException{

            model.getPlaceablePositions(nickname, x, y);

    }
}
