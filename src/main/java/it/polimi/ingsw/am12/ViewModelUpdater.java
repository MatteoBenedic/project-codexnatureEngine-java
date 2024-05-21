package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;
import it.polimi.ingsw.am12.Model.Logic.PlayerColour;
import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.Utils.Coordinate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * An interface that defines the methods to update the ViewModel
 */
public interface ViewModelUpdater {

    /**
     * Update the ViewModel when the nickname is registered
     * @param nickname the registered nickname
     */
    void nicknameEstablishedUpdate(String nickname);

    /**
     * Update the ViewModel when a list of available lobbies is received
     * @param lobbies the list of available lobbies
     */
    void lobbiesNonCompletedUpdate(Map<String, Integer> lobbies);

    /**
     * Update the ViewModel when some players joined the lobby
     * @param nicknames the nicknames of the players who joined the lobby
     * @param state the new state of the game
     */
    void playersAddedUpdate(List<String> nicknames, State state);

    /**
     * Update the ViewModel when the match has started
     * @param nicknames the list of the players in the game
     * @param turn the nickname of the player whose turn is now
     * @param state the new state of the game
     * @param startCards the startCards assigned to the players
     * @param goldDeckColour the colour of the gold deck
     * @param resDeckColour the colour of the res deck
     * @param publicCards the four public cards
     *                    0 --> first gold
     *                    1 --> second gold
     *                    2 --> first resource
     *                    3 --> second resource
     */
    void matchStartedUpdate(List<String> nicknames, String turn, State state, Map<String, Integer> startCards, CardColour goldDeckColour, CardColour resDeckColour, int[] publicCards);

    /**
     * Update the ViewModel when a start card is placed
     * @param nickname the player who placed his start card
     * @param startCard the index of the start card placed
     * @param side the side on which it was placed (TRUE = front, FALSE = back)
     * @param turn the nickname of the player whose turn is now
     * @param state the new state of the game
     */
    void startCardPlacedUpdate(String nickname, int startCard, boolean side, String turn, State state);

    /**
     * Update the ViewModel when a player selected his colour
     * @param nickname the player who selected a colour
     * @param colour the selected colour
     * @param turn the nickname of the player whose turn is now
     * @param state the new state of the game
     */
    void colourSelectedUpdate(String nickname, PlayerColour colour, String turn, State state);

    /**
     * Update the ViewModel when the cards have been distributed
     * @param cardsDistributed the cards that have been distributed to each player
     * @param newGoldDeckColour the new colour of the gold deck
     * @param newResDeckColour the new colour of the res deck
     * @param secretObjectives the two secret objectives for each player
     * @param publicObjectives the two public objectives
     * @param turn the nickname of the player whose turn is now
     * @param state the new state of the game
     */
    public void cardsDistributedUpdate(Map<String, List<Integer>> cardsDistributed, CardColour newGoldDeckColour, CardColour newResDeckColour, Map<String, int[]> secretObjectives, int[] publicObjectives, String turn, State state);

    /**
     * Update the ViewModel when a player has selected his secret objective
     * @param nickname the player who selected his objective
     * @param secretObjective the index of the selected objective
     * @param turn the nickname of the player whose turn is now
     * @param state the new state of the game
     */
    void objectiveSelectedUpdate(String nickname, int secretObjective, String turn, State state);

    /**
     * Update the ViewModel when a list of placeable positions is received
     * @param nickname the player who requested the placeable positions around a card
     * @param availablePositions the list of available positions
     */
    void placeablePositionReturnedUpdate(String nickname, List<Coordinate> availablePositions);

    /**
     * Update the ViewModel after a player placed a card
     * @param nickname the player who placed a card
     * @param index the index of the placed card
     * @param side the side on which it was placed (TRUE = front, FALSE = back)
     * @param points the number of gained points
     * @param state the new state of the game
     * @param coordinates the position of the placed card on the playing grid
     */
    void cardPlacedUpdate(String nickname, int index, Boolean side, int points, State state, Coordinate coordinates);

    /**
     * Update the ViewModel after a player has drawn a card
     * @param nickname the player who has drawn a card
     * @param indexDrawnCard the index of the drawn card
     * @param deckIndex the index of the deck from which the card was drawn
     *                  index = 0  : public gold 1;
     *                  index = 1  : public gold 2;
     *                  index = 2  : public resource 1;
     *                  index = 3  : public resource 2;
     *                  index = 4  : hidden gold;
     *                  index = 5  : hidden resource;
     * @param newPublicCard the index of the new public card on the table
     * @param newGoldDeckColour the new color of the gold deck
     * @param newResDeckColour the new color of the resource deck
     * @param turn the nickname of the player whose turn is now
     * @param remaningRounds the number of remaining rounds
     * @param state the new state of the game
     */
    void cardDrawnUpdate(String nickname, int indexDrawnCard, int deckIndex, int newPublicCard, CardColour newGoldDeckColour, CardColour newResDeckColour, String turn, int remaningRounds, State state);

    /**
     * Update the ViewModel when a message in the chat is received
     * @param sender the nickname of the user who sent the message
     * @param publicMess a boolean that specifies if the message is public (TRUE) or private (FALSE)
     * @param chatMessage the content of the message
     */
    void chatUpdate(String sender, boolean publicMess, String chatMessage);

    /**
     * Update the ViewModel when the game has ended and the results are available
     * @param winners the number of winners
     * @param classification the classification of the match, with the final points of each player
     * @param state the new state of the game
     */
    void gameEndedUpdate(int winners, LinkedHashMap<String, Integer> classification, State state);

    /**
     * Update the ViewModel when an error message is received
     * @param errorMessage the error message
     */
    void setErrorMessage(String errorMessage);
}
