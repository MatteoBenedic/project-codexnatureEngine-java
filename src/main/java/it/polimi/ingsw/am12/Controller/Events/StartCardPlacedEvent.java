package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.View.VirtualView;
import java.util.List;

public class StartCardPlacedEvent implements Event{

    String nickname;
    int startCard;
    boolean side;
    String turn;

    public StartCardPlacedEvent(String nickname, int startCard, boolean side, String turn) {
        this.nickname = nickname;
        this.startCard = startCard;
        this.side = side;
        this.turn = turn;
    }

    @Override
    public void executeCommand(GameModel model, List<VirtualView> views) {
        for(VirtualView view : views) {
            String message = "\n" + view.getNickname();
            if(view.getNickname().equals(nickname)) {
               message += "\nHai piazzato correttamente la carta " + startCard + " sul ";
            }
            else {
                message += "\nIl giocatore " + nickname + " ha piazzato la carta " + startCard + " sul ";
            }
            if(side) {
                message += "fronte";
            }
            else {
                message += "retro";
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
