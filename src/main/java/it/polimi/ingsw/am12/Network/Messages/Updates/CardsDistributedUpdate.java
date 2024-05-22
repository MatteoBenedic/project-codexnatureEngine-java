package it.polimi.ingsw.am12.Network.Messages.Updates;

import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;
import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.ViewModelUpdater;

import java.util.List;
import java.util.Map;

/**
 * Update to handle the card distribution when the match starts
 */
public class CardsDistributedUpdate implements Update {
    String turn;
    Map<String, List<Integer>> cardsDistributed;
    CardColour newGoldDeckColour;
    CardColour newResDeckColour;
    Map<String, int[]> secretObjectives;
    int[] publicObjectives;
    State state;

    /**
     * Class constructor
     * @param cardsDistributed  the cards distributed to each payer
     * @param newGoldDeckColour the new colour of the gold deck
     * @param newResDeckColour the new colour of the gold deck
     * @param secretObjectives  the secret objectives from which the players have to choose
     * @param publicObjectives  the public objectives
     * @param turn              the nickname of the player of turn is now
     * @param state             the state of the game (OBJECTIVE)
     */
    public CardsDistributedUpdate(Map<String, List<Integer>> cardsDistributed, CardColour newGoldDeckColour, CardColour newResDeckColour, Map<String, int[]> secretObjectives, int[] publicObjectives, String turn, State state) {
        this.cardsDistributed=cardsDistributed;
        this.newGoldDeckColour = newGoldDeckColour;
        this.newResDeckColour = newResDeckColour;
        this.secretObjectives=secretObjectives;
        this.publicObjectives=publicObjectives;
        this.turn=turn;
        this.state = state;
    }

    /**
     * Update the ViewModel
     * @param viewModelUpdater the ViewModel to update
     */
    @Override
    public void executeUpdate(ViewModelUpdater viewModelUpdater) {
        viewModelUpdater.cardsDistributedUpdate(cardsDistributed, newGoldDeckColour, newResDeckColour, secretObjectives, publicObjectives, turn, state);
    }
}
