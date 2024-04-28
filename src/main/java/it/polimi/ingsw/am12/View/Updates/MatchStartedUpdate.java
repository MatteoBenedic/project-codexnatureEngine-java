package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.Model.Logic.State;
import java.util.Map;
import java.util.List;

public class MatchStartedUpdate implements Update {

    List<String> nicknames;
    Map<String, Integer> startCards;
    String goldDeckColour;
    String resDeckColour;
    int[] publicCards;
    String turn;
    State state;


    public MatchStartedUpdate(List<String> nicknames, Map<String, Integer> startCards, String goldDeckColour, String resDeckColour, int[] publicCards, String turn, State state) {
        this.nicknames = nicknames;
        this.startCards = startCards;
        this.goldDeckColour = goldDeckColour;
        this.resDeckColour = resDeckColour;
        this.publicCards = publicCards;
        this.turn = turn;
        this.state = state;
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    public Map<String, Integer> getStartCards() {
        return startCards;
    }

    public String getGoldDeckColour() {
        return goldDeckColour;
    }

    public String getResDeckColour() {
        return resDeckColour;
    }

    public int[] getPublicCards() {
        return publicCards;
    }

    public String getTurn() {
        return turn;
    }

    public State getState() {
        return state;
    }
}
