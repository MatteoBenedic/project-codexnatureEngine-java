package it.polimi.ingsw.am12.View.Updates;

import java.util.LinkedHashMap;
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

    public String toString(String receiver) {
        return receiver;
    }

    public String getTurn() {
        return "";
    }
}
