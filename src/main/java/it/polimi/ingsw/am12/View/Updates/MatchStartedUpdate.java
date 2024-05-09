package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.MemoryUpdater;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;
import it.polimi.ingsw.am12.Model.Logic.State;
import java.util.Map;
import java.util.List;

public class MatchStartedUpdate implements Update {

    List<String> nicknames;
    Map<String, Integer> startCards;
    CardColour goldDeckColour;
    CardColour resDeckColour;
    int[] publicCards;
    String turn;
    State state;


    public MatchStartedUpdate(List<String> nicknames, Map<String, Integer> startCards, CardColour goldDeckColour, CardColour resDeckColour, int[] publicCards, String turn, State state) {
        this.nicknames = nicknames;
        this.startCards = startCards;
        this.goldDeckColour = goldDeckColour;
        this.resDeckColour = resDeckColour;
        this.publicCards = publicCards;
        this.turn = turn;
        this.state = state;
    }

    public String toString(String receiver) {
        if(receiver==null || !nicknames.contains(receiver))
            return "";

        String message = "\nMatch has started.";
        message += "\nThe colour of the gold deck is "+goldDeckColour;
        message += "\nThe colour of the resource deck is "+resDeckColour;
        message += "\nThe public cards are:";
        for(int card: publicCards) {
            message += " "+card;
        }
        message += "\nYour start card is: " + startCards.get(receiver);
        if(turn.equals(receiver)) {
            message += "\nIt's your turn: place your start card";
        }
        else {
            message += "\nIt's " + turn + "'s turn. Please wait.";
        }

        return message;
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    public Map<String, Integer> getStartCards() {
        return startCards;
    }

    public CardColour getGoldDeckColour() {
        return goldDeckColour;
    }

    public CardColour getResDeckColour() {
        return resDeckColour;
    }

    public int[] getPublicCards() {
        return publicCards;
    }

    public String getTurn() {
        return turn;
    }

    @Override
    public void executeUpdate(MemoryUpdater memoryUpdater) {

    }

    public State getState() {
        return state;
    }
}
