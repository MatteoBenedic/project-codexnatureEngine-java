package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;
import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.ViewModelUpdater;

/**
 * Update to handle when a card is drawn
 */
public class CardDrawnUpdate implements Update {

    String nickname;
    int indexDrawnCard;
    int deckIndex;
    int newPublicCard;
    CardColour newGoldDeckColour;
    CardColour newResDeckColour;
    String turn;
    int remaningRounds;
    State state;

    /**
     * Class constructor
     * @param nickname          the nickname of player
     * @param indexDrawnCard    the index of the drawn card
     * @param deckIndex         the deck idx
     *                          index = 0  : public gold 1;
     *                          index = 1  : public gold 2;
     *                          index = 2  : public resource 1;
     *                          index = 3  : public resource 2;
     *                          index = 4  : hidden gold;
     *                          index = 5  : hidden resource;
     * @param newPublicCard     the index of the new public card
     * @param newGoldDeckColour the new gold deck colour
     * @param newResDeckColour  the new res deck colour
     * @param turn              the nickname of the player whose turn is now
     * @param remaningRounds    the remaining rounds
     * @param state             the state of the game (PLACING or END)
     */
    public CardDrawnUpdate(String nickname, int indexDrawnCard, int deckIndex, int newPublicCard, CardColour newGoldDeckColour, CardColour newResDeckColour, String turn, int remaningRounds, State state){
        this.nickname = nickname;
        this.indexDrawnCard=indexDrawnCard;
        this.deckIndex=deckIndex;
        this.newPublicCard=newPublicCard;
        this.newGoldDeckColour = newGoldDeckColour;
        this.newResDeckColour = newResDeckColour;
        this.turn=turn;
        this.remaningRounds = remaningRounds;
        this.state = state;
    }

    /**
     * Update the ViewModel
     * @param viewModelUpdater the ViewModel to update
     */
    @Override
    public void executeUpdate(ViewModelUpdater viewModelUpdater) {
        viewModelUpdater.cardDrawnUpdate(nickname, indexDrawnCard, deckIndex, newPublicCard, newGoldDeckColour, newResDeckColour, turn, remaningRounds, state);
    }
}
