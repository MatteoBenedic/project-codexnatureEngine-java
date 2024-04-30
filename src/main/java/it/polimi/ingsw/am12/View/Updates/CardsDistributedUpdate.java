package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.Model.Logic.State;
import java.util.List;
import java.util.Map;

public class CardsDistributedUpdate implements Update {

    String turn;
    Map<String, List<Integer>> cardsDistributed;
    Map<String, int[]> secretObjectives;
    int[] publicObjectives;
    State state;

    public CardsDistributedUpdate(Map<String, List<Integer>> cardsDistributed, Map<String, int[]> secretObjectives, int[] publicObjectives, String turn, State state) {
        this.cardsDistributed=cardsDistributed;
        this.secretObjectives=secretObjectives;
        this.publicObjectives=publicObjectives;
        this.turn=turn;
        this.state = state;
    }

    public String getTurn() {
        return turn;
    }

    public Map<String, List<Integer>> getCardsDistributed() {
        return cardsDistributed;
    }

    public Map<String, int[]> getSecretObjectives() {
        return secretObjectives;
    }

    public int[] getPublicObjectives() {
        return publicObjectives;
    }

    public State getState() {
        return state;
    }

    public String toString(String receiver) {
        if(receiver==null)
            return "";

        String message = "\nCards have been distributed.";
        message += "\nYour cards are: ";
        for(Integer card : cardsDistributed.get(receiver)) {
            message += " "+card;
        }
        message += "\nYour secret objectives are: ";
        int[] secretObj = secretObjectives.get(receiver);
        for(int i=0; i<secretObj.length; i++) {
            message += " "+secretObj[i];
        }
        message += "\nThe public objectives are: ";
        for(int j=0; j<publicObjectives.length; j++) {
            message += " " + publicObjectives[j];
        }
        if(turn.equals(receiver)) {
            message += "\nIt's your turn";
        }
        else {
            message += "\nIt's " + turn + "'s turn. Please wait.";
        }

        return message;
    }

}
