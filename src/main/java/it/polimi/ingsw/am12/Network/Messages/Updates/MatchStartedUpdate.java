package it.polimi.ingsw.am12.Network.Messages.Updates;

import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;
import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.ViewModelUpdater;
import java.util.Map;
import java.util.List;

public class MatchStartedUpdate extends Update {

    private final List<String> nicknames;
    private final Map<String, Integer> startCards;
    private final CardColour goldDeckColour;
    private final CardColour resDeckColour;
    private final int[] publicCards;
    private final String turn;
    private final State state;

    /**
     * Class constructor
     * @param nicknames the nicknames of the players in the game
     * @param startCards the start card assigned to each player
     * @param goldDeckColour the colour of the gold deck
     * @param resDeckColour the colour of the resource deck
     * @param publicCards the four public cards
     *                    0 --> first gold
     *                    1 --> second gold
     *                    2 --> first resource
     *                    3 --> second resource
     * @param turn the nickname of the player whose turn is now
     * @param state the state of the game (STARTCARD)
     */
    public MatchStartedUpdate(List<String> nicknames, Map<String, Integer> startCards, CardColour goldDeckColour, CardColour resDeckColour, int[] publicCards, String turn, State state) {
        this.nicknames = nicknames;
        this.startCards = startCards;
        this.goldDeckColour = goldDeckColour;
        this.resDeckColour = resDeckColour;
        this.publicCards = publicCards;
        this.turn = turn;
        this.state = state;
    }

    /**
     * Update the ViewModel
     * @param viewModelUpdater the ViewModel to update
     */
    @Override
    public void executeUpdate(ViewModelUpdater viewModelUpdater) {
        viewModelUpdater.matchStartedUpdate(nicknames, turn, state, startCards, goldDeckColour, resDeckColour, publicCards);
    }

}
