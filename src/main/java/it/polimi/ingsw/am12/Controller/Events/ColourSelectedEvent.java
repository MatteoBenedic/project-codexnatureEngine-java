package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Model.Logic.PlayerColour;
import it.polimi.ingsw.am12.View.VirtualView;

import java.util.List;

public class ColourSelectedEvent implements Event{
    String nickname;
    PlayerColour colour;
    String turn;

    public ColourSelectedEvent(String nickname, PlayerColour colour, String turn) {

        this.nickname = nickname;

        this.colour = colour;

        this.turn = turn;

    }

    @Override
    public void executeCommand(GameModel model, List<VirtualView> views) {
        for(VirtualView view : views) {
            String message = "\n" + view.getNickname();
            if(view.getNickname().equals(nickname)) {
                message +="\nHai scelto correttamente il colore "+ colour;
            }
            else {
                message += "\nIl giocatore " + nickname + " ha scelto il colore "+ colour;
            }
            if(view.getNickname().equals(turn)) {
                message += "\nÈ il tuo turno";
            }
            else {
                message += "\nÈ il turno di " + turn;
            }
            view.setMessage(message);
        }
    }
}
