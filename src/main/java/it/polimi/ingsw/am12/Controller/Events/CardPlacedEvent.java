package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Utils.Coordinate;
import it.polimi.ingsw.am12.View.VirtualView;

import java.util.List;

public class CardPlacedEvent implements Event{

    String nickname;
    int index;
    Boolean side;
    int points;
    String turn;

    public CardPlacedEvent(String nickname, int index, Boolean side, int points, String turn) {
        this.nickname = nickname;
        this.index = index;
        this.side = side;
        this.points = points;
        this.turn = turn;
    }

    @Override
    public void executeCommand(GameModel model, List<VirtualView> views) {
        for(VirtualView view : views) {
            String message = "\n" + view.getNickname();
            if(view.getNickname().equals(nickname)) {
                message += "\nHai piazzato correttamente la carta "+ index +" sul ";
            }
            else {
                message += "\nIl giocatore " + nickname+ " ha piazzato la carta "+ index +" sul ";
            }
            if(side) {
                message += "fronte";
            }
            else {
                message += "retro";
            }
            message += " ottenendo "+ points + " punti.";
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
