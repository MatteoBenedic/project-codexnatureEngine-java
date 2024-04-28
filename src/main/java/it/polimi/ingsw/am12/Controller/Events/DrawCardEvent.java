package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.EmptyDeckException;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Model.Logic.NotYourTurnException;
import it.polimi.ingsw.am12.Model.Logic.WrongInformationException;
import it.polimi.ingsw.am12.View.VirtualView;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * Event to draw a card
 */
public class DrawCardEvent implements Event{

    String nickname;
    int deckIndex;

    /**
     *Instantiates a new DrawCard event.
     *
     * @param nickname A String that identifies the player
     * @param deckIndex           - index = 0  : public gold 1;
     *      *      *              - index = 1  : public gold 2;
     *      *      *              - index = 2  : public resource 1;
     *      *      *              - index = 3  : public resource 2;
     *      *      *              - index = 4  : hidden gold;
     *      *      *              - index = 5  : hidden resource;
     */
    public DrawCardEvent(String nickname, int deckIndex){
        this.nickname=nickname;
        this.deckIndex=deckIndex;
    }

    /**
     * Draw a card for the player.

     * @throws IllegalStateException if the method has been invoked at an illegal or inappropriate time.
     * @throws InvalidParameterException if the nickname is null, or index<0 or index>5
     * @throws WrongInformationException if the player is not part of this match
     * @throws NotYourTurnException if it's not the turn of the player
     * @throws EmptyDeckException if the selected deck is empty.
     */
    @Override
    public void executeCommand(GameModel model) throws InvalidParameterException, EmptyDeckException,
            NotYourTurnException, WrongInformationException, IllegalStateException{

            model.drawCard(nickname, deckIndex);

    }
}
