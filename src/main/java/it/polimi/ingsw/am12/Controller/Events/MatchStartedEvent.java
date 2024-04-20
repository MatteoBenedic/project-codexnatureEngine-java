package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.View.VirtualView;
import java.util.Map;
import java.util.List;

public class MatchStartedEvent implements Event{

    List<String> nicknames;
    Map<String, Integer> startCards;
    String goldDeckColour;
    String resDeckColour;
    int[] publicCards;
    String turn;

    public MatchStartedEvent(List<String> nicknames, Map<String, Integer> startCards, String goldDeckColour, String resDeckColour, int[] publicCards, String turn) {
        this.nicknames = nicknames;
        this.startCards = startCards;
        this.goldDeckColour = goldDeckColour;
        this.resDeckColour = resDeckColour;
        this.publicCards = publicCards;
        this.turn = turn;
    }

    @Override
    public void executeCommand(GameModel model,List<VirtualView> views) {
        for(VirtualView view : views) {
            String message = "\n" + view.getNickname();
            message += "\nIl mazzo gold è " + goldDeckColour;
            message += "\nIl mazzo resources è " + resDeckColour;
            message += "\nLe carte pubbliche sono:";
            for(int i : publicCards) {
                message += " " + i;
            }
            message += "\nLa tua start card è " + startCards.get(view.getNickname());
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
