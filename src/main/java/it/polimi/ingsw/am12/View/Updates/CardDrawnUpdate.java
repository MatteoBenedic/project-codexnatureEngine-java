package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.Model.Logic.State;

public class CardDrawnUpdate implements Update {

    String nickname;
    int indexDrawnCard;
    int deckIndex;
    int newPublicCard;
    String newGoldDeckColour;
    String newResDeckColour;
    String turn;
    int remaningRounds;
    State state;

    public CardDrawnUpdate(String nickname, int indexDrawnCard, int deckIndex, int newPublicCard, String newGoldDeckColour, String newResDeckColour, String turn, int remaningRounds, State state){
        this.nickname = nickname;
        this.indexDrawnCard=indexDrawnCard;
        this.deckIndex=deckIndex;
        this.newPublicCard=newPublicCard;
        this.newGoldDeckColour=newGoldDeckColour;
        this.newResDeckColour=newResDeckColour;
        this.turn=turn;
        this.remaningRounds = remaningRounds;
        this.state = state;
    }

    public String getNickname(){
        return nickname;
    }

    public int getIndexDrawnCard(){
        return indexDrawnCard;
    }

    public int getDeckIndex(){
        return deckIndex;
    }

    public int getNewPublicCard(){
        return newPublicCard;
    }

    public String getNewGoldDeckColour(){
        return newGoldDeckColour;
    }

    public String getNewResDeckColour(){
        return newResDeckColour;
    }

    public String getTurn(){
        return turn;
    }

    public int getRemaningRounds(){
        return remaningRounds;
    }

    public State getState() {
        return state;
    }

    public String toString(String receiver) {
        return receiver;
    }
}
