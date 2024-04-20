package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.View.VirtualView;
import java.util.List;

public class ObjectiveSelectedEvent implements Event{

    String nickname;
    int secretObjective;
    String turn;

    public ObjectiveSelectedEvent(String nickname, int secretObjective, String turn) {
        this.nickname = nickname;
        this.secretObjective = secretObjective;
        this.turn = turn;
    }

    @Override
    public void executeCommand(GameModel model, List<VirtualView> views) {
        for(VirtualView view : views) {
            String message = "\n" + view.getNickname();
            if(view.getNickname().equals(nickname)) {
                message += "\nHai scelto correttamente l'obiettivo "+ secretObjective;
            }
            else {
                message += "\nIl giocatore " + nickname + " ha scelto il suo obiettivo";
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
