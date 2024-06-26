package it.polimi.ingsw.am12.Network.Messages.Events;

import it.polimi.ingsw.am12.Exceptions.EmptyDeckException;
import it.polimi.ingsw.am12.Exceptions.InvalidParameterException;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Exceptions.NotYourTurnException;
import it.polimi.ingsw.am12.Exceptions.WrongInformationException;
import it.polimi.ingsw.am12.Exceptions.IllegalStateException;

/**
 * Event to draw a card
 */
public class DrawCardEvent extends Event{

    private final String nickname;
    private final int deckIndex;

    /**
     *
     * Class constructor
     * @param nickname the nickname of the player
     * @param deckIndex  an int
     *                  - index = 0  : public gold 1;
     *                  - index = 1  : public gold 2;
     *                   - index = 2  : public resource 1;
     *                   - index = 3  : public resource 2;
     *                   - index = 4  : hidden gold;
     *                   - index = 5  : hidden resource;
     */
    public DrawCardEvent(String nickname, int deckIndex){
        this.nickname=nickname;
        this.deckIndex=deckIndex;
    }

    /**
     * Draw a card for the player
     * @param model the GameModel that interacts with this event
     * @throws InvalidParameterException if the nickname is null, or index lower than 0 or higher than 5
     * @throws EmptyDeckException if the selected deck is empty.
     * @throws NotYourTurnException if it's not the turn of the player
     * @throws WrongInformationException if the player is not part of this match
     * @throws IllegalStateException if the method has been invoked at an illegal or inappropriate time.
     */
    @Override
    public void executeCommand(GameModel model) throws InvalidParameterException, EmptyDeckException,
            NotYourTurnException, WrongInformationException, IllegalStateException{

            model.drawCard(nickname, deckIndex);

    }
}
