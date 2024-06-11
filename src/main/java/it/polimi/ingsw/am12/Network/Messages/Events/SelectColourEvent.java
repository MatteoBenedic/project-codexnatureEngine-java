package it.polimi.ingsw.am12.Network.Messages.Events;

import it.polimi.ingsw.am12.Exceptions.InvalidParameterException;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Exceptions.NotYourTurnException;
import it.polimi.ingsw.am12.Model.Logic.PlayerColour;
import it.polimi.ingsw.am12.Exceptions.WrongInformationException;
import it.polimi.ingsw.am12.Exceptions.IllegalStateException;

/**
 * Event to select a colour for a player
 */
public class SelectColourEvent extends Event{

    private final String nickname;
    private final PlayerColour selectedColour;

    /**
     * Class constructor
     * @param nickname the nickname of the player
     * @param selectedColour the colour chosen by the player
     */
    public SelectColourEvent(String nickname, PlayerColour selectedColour) {
        this.nickname = nickname;
        this.selectedColour = selectedColour;
    }

    /**
     * Assign a colour to the player
     * @param model the GameModel that interacts with this event
     * @throws IllegalStateException if the method has been invoked at an illegal or inappropriate time.
     * @throws InvalidParameterException if any of the parameters is null
     * @throws WrongInformationException if the player is not part of this match,
     *                                   or the selected colour is not available
     * @throws NotYourTurnException  if it's not the turn of the player
     */
    @Override
    public void executeCommand(GameModel model) throws WrongInformationException, NotYourTurnException,
            IllegalStateException, InvalidParameterException {

            model.setPlayerColour(nickname, selectedColour);

    }
}