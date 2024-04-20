package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.View.VirtualView;
import java.util.List;

public class CardDrawnEvent implements Event{

    String nickname;
    int indexDrawnCard;
    int deckIndex;
    int newPublicCard;
    String newGoldDeckColour;
    String newResDeckColour;
    String turn;

    public CardDrawnEvent(String nickname, int indexDrawnCard, int deckIndex, int newPublicCard, String newGoldDeckColour, String newResDeckColour, String turn){
        this.nickname = nickname;
        this.indexDrawnCard=indexDrawnCard;
        this.deckIndex=deckIndex;
        this.newPublicCard=newPublicCard;
        this.newGoldDeckColour=newGoldDeckColour;
        this.newResDeckColour=newResDeckColour;
        this.turn=turn;
    }

    @Override
    public void executeCommand(GameModel model, List<VirtualView> views) {
        for(VirtualView view : views) {
            String message = "\n" + view.getNickname();
            if(view.getNickname().equals(nickname)) {
                message += "\nHai pescato la carta "+ indexDrawnCard +" dal mazzo " + deckIndex;
            }
            else {
                message +="\nIl giocatore " + nickname+ " ha pescato dal mazzo " +deckIndex;
            }
            message += "\nIl nuovo colore in cima al gold deck è: " + newGoldDeckColour
                    + "\nIl nuovo colore in cima al resource deck è: " + newResDeckColour
                    + "\nLa nuova public card è: " + newPublicCard;
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
