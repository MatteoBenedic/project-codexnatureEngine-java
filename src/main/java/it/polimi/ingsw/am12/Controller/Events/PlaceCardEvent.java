package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.*;
import it.polimi.ingsw.am12.View.VirtualView;
import java.security.InvalidParameterException;
import java.util.List;

public class PlaceCardEvent implements Event{

    String nickname;
    int index;
    boolean side;
    int xpos;
    int ypos;


    /**
     * Instantiates a new PlaceCard  event.
     *
     * @param nickname of the player
     * @param side TRUE = front side, FALSE = back side
     * @param xpos row index of grid
     * @param ypos col index of grid
     */
    public PlaceCardEvent(String nickname, int index, boolean side, int xpos, int ypos) {
        this.nickname = nickname;
        this.index = index;
        this.side = side;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    @Override

    /**
     * place the card for player "nickname"

     * @throws IllegalStateException if the method has been invoked at an illegal or inappropriate time.
     * @throws InvalidParameterException if the nickname is null
     * @throws WrongInformationException if the player is not part of this match
     * @throws NotYourTurnException  if it's not the turn of the player
     * @throws InvalidPlacementException  if a start card has already been placed for the player
     *
     */
    public void executeCommand(GameModel model) throws InvalidParameterException, InvalidPlacementException,
            NotYourTurnException, WrongInformationException, IllegalStateException{

            model.placeCard(nickname, index, side, xpos, ypos);
    }
}
