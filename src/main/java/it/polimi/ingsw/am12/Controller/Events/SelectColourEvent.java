package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Model.Logic.NotYourTurnException;
import it.polimi.ingsw.am12.Model.Logic.PlayerColour;
import it.polimi.ingsw.am12.Model.Logic.WrongInformationException;
import it.polimi.ingsw.am12.View.VirtualView;
import java.util.List;

public class SelectColourEvent implements Event{

    String nickname;
    PlayerColour selectedColour;

    public SelectColourEvent(String nickname, PlayerColour selectedColour) {
        this.nickname = nickname;
        this.selectedColour = selectedColour;
    }

    @Override
    public void executeCommand(GameModel model) throws WrongInformationException, NotYourTurnException,
            IllegalStateException{

            model.setPlayerColour(nickname, selectedColour);

    }
}
