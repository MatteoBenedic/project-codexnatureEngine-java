package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.Controller.Events.Event;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.View.VirtualView;
import java.util.LinkedHashMap;
import java.util.List;
import it.polimi.ingsw.am12.Model.Logic.State;

public class GameEndedUpdate implements Update {

    int winners;
    LinkedHashMap<String, Integer> classification;
    State state;

    public GameEndedUpdate(int winners, LinkedHashMap<String, Integer> classification, State state) {
        this.winners = winners;
        this.classification = classification;
        this.state = state;
    }

    public int getWinners() {
        return winners;
    }

    public LinkedHashMap<String, Integer> getClassification() {
        return classification;
    }

    public State getState(){
        return state;
    }
}
