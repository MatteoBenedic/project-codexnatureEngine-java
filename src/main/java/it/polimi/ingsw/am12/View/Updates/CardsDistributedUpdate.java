package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.Controller.Events.Event;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.View.VirtualView;
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
}
