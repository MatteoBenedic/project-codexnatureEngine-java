package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.MemoryUpdater;
import it.polimi.ingsw.am12.Model.Logic.State;


/**
 * Update to handle when a card is drawn
 */
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

    /**
     * Instantiates a new Card drawn update.
     *
     * @param nickname          the nickname of player
     * @param indexDrawnCard    the index drawn card from json
     * @param deckIndex         the deck idx index = 0  : public gold 1;
     *               - index = 1  : public gold 2;
     *               - index = 2  : public resource 1;
     *               - index = 3  : public resource 2;
     *               - index = 4  : hidden gold;
     *               - index = 5  : hidden resource;
     * @param newPublicCard     the new public card
     * @param newGoldDeckColour the new gold deck colour on the grid
     * @param newResDeckColour  the new res deck colour on the grid
     * @param turn              the turn
     * @param remaningRounds    the remaning rounds
     * @param state             the state
     */
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

    /**
     * Get nickname string.
     *
     * @return the string
     */
    public String getNickname(){
        return nickname;
    }

    /**
     * Get index drawn card int.
     *
     * @return the int
     */
    public int getIndexDrawnCard(){
        return indexDrawnCard;
    }

    /**
     * Get deck index int.
     *
     * @return the int
     */
    public int getDeckIndex(){
        return deckIndex;
    }

    /**
     * Get new public card int.
     *
     * @return the int
     */
    public int getNewPublicCard(){
        return newPublicCard;
    }

    /**
     * Get new gold deck colour string.
     *
     * @return the string
     */
    public String getNewGoldDeckColour(){
        return newGoldDeckColour;
    }

    /**
     * Get new res deck colour string.
     *
     * @return the string
     */
    public String getNewResDeckColour(){
        return newResDeckColour;
    }

    public String getTurn(){
        return turn;
    }

    @Override
    public void executeUpdate(MemoryUpdater memoryUpdater) {

    }

    /**
     * Get remaning rounds int.
     *
     * @return the int
     */
    public int getRemaningRounds(){
        return remaningRounds;
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
        return receiver;
    }
}
