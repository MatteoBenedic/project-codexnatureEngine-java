package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.View.VirtualView;
import java.util.LinkedHashMap;
import java.util.List;

public class GameEndedEvent implements Event{

    int winners;
    LinkedHashMap<String, Integer> classification;

    public GameEndedEvent(int winners, LinkedHashMap<String, Integer> classification) {
        this.winners = winners;
        this.classification = classification;
    }

    @Override
    public void executeCommand(GameModel model, List<VirtualView> views) {
        for(VirtualView view : views) {
            String message = "\n" + view.getNickname();
            List<String> namesOrder = classification.sequencedKeySet().stream().toList();
            message += "\nVincitori:";
            for(int i = 0; i<winners; i++) {
                message += "\n"+namesOrder.get(i)+" con "+classification.get(namesOrder.get(i))+" punti";
            }
            message += "\nNon vincitori:";
            for(int i = winners; i<classification.size(); i++) {
                message += "\n"+namesOrder.get(i)+" con "+classification.get(namesOrder.get(i))+" punti";
            }
            view.setMessage(message);
        }
    }
}
