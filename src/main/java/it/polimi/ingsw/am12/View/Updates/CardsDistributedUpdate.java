package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.MemoryUpdater;
import it.polimi.ingsw.am12.Model.Logic.State;
import java.util.List;
import java.util.Map;

/**
 * Update to handle the card distribution when the match starts
 */
public class CardsDistributedUpdate implements Update {


    String turn;

    Map<String, List<Integer>> cardsDistributed;

    Map<String, int[]> secretObjectives;

    int[] publicObjectives;

    State state;

    /**
     * Instantiates a new Cards distributed update.
     *
     * @param cardsDistributed the cards distributed
     * @param secretObjectives the secret objectives
     * @param publicObjectives the public objectives
     * @param turn             the turn
     * @param state            the state
     */
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

    @Override
    public void executeUpdate(MemoryUpdater memoryUpdater) {

    }

    /**
     * Gets cards distributed.
     *
     * @return the cards distributed
     */
    public Map<String, List<Integer>> getCardsDistributed() {
        return cardsDistributed;
    }

    /**
     * Gets secret objectives.
     *
     * @return the secret objectives
     */
    public Map<String, int[]> getSecretObjectives() {
        return secretObjectives;
    }

    /**
     * Get public objectives int [ ].
     *
     * @return the int [ ]
     */
    public int[] getPublicObjectives() {
        return publicObjectives;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
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
