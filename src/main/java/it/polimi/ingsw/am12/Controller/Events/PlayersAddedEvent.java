package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.View.VirtualView;
import java.util.List;

public class PlayersAddedEvent implements Event{

    List<String> nicknames;

    public PlayersAddedEvent(List<String> nicknames) {
        this.nicknames = nicknames;
    }

    @Override
    public void executeCommand(GameModel model, List<VirtualView> views) {

        for(VirtualView view : views) {
            String message = "Ciao " + view.getNickname();
            message += "\nI giocatori della partita sono tutti arrivati:";
            for(VirtualView joined : views){
                if(!joined.equals(view))
                {
                    message += " " + joined.getNickname();
                }
            }
            view.setMessage(message);
        }
    }
}
