package it.polimi.ingsw.am12.Network.Messages.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Exceptions.NotYourTurnException;
import it.polimi.ingsw.am12.Model.Logic.PlayerColour;
import it.polimi.ingsw.am12.Exceptions.WrongInformationException;

import java.security.InvalidParameterException;

public class SelectColourEvent implements Event{


    String nickname;
    PlayerColour selectedColour;


    /**
     * Instantiates a new PlaceCard  event.
     *
     * @param nickname of the player
     * @param selectedColour colour choosen
     */
    public SelectColourEvent(String nickname, PlayerColour selectedColour) {
        this.nickname = nickname;
        this.selectedColour = selectedColour;
    }

    @Override

    /**
     * Assign a colour to a player.
     * @param nickname A String that identifies the player.
     * @param selectedColour the colour chosen by the player
     * @throws IllegalStateException if the method has been invoked at an illegal or inappropriate time.
     * @throws InvalidParameterException if any of the parameters is null
     * @throws WrongInformationException if the player is not part of this match,
     *                                   or the selected colour is not available
     * @throws NotYourTurnException  if it's not the turn of the player
     */

    public void executeCommand(GameModel model) throws WrongInformationException, NotYourTurnException,
            IllegalStateException{

            model.setPlayerColour(nickname, selectedColour);

    }
}
