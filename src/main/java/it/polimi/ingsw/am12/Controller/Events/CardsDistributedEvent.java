package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.View.VirtualView;
import java.util.List;
import java.util.Map;

public class CardsDistributedEvent implements Event{

    String turn;
    Map<String, List<Integer>> cardsDistributed;
    Map<String, int[]> secretObjectives;
    int[] publicObjectives;

    public CardsDistributedEvent(Map<String, List<Integer>> cardsDistributed, Map<String, int[]> secretObjectives, int[] publicObjectives, String turn) {
        this.cardsDistributed=cardsDistributed;
        this.secretObjectives=secretObjectives;
        this.publicObjectives=publicObjectives;
        this.turn=turn;
    }

    @Override
    public void executeCommand(GameModel model, List<VirtualView> views) {
        for(VirtualView view : views){
            String message = "\n" + view.getNickname();
            message += "\nLe tue carte in mano sono:";
            for(Integer card : cardsDistributed.get(view.getNickname())){
                message += " " + card;
            }
            message += "\nI tuoi obiettivi segreti sono:";
            for(Integer card : secretObjectives.get(view.getNickname())){
                message += " " + card;
            }
            message += "\nGli obiettivi pubblici sono:";
            for(Integer card : publicObjectives){
                message += " " + card;
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
