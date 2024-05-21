package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;
import it.polimi.ingsw.am12.Model.Logic.PlayerColour;
import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.PropertyChangeEvents.*;
import it.polimi.ingsw.am12.Utils.Coordinate;
import java.util.*;


/**
 * This class contains the client side state of the application, to be rendered by the user interface
 */
public class ViewModel implements ViewModelUpdater {

    static final int START_CARD_COL = 40;
    static final int START_CARD_ROW = 40;
    static final int MAX_NUM_MESS = 10;
    static final int CARDS_ON_TABLE = 4;
    static final int N_OBJECTIVES = 2;

    private String match;
    private Map<String,Integer> lobbies;
    private List<String> nicknames;
    private String myNickname;
    private State state;
    private String turn;
    private Map<String, List<ClientCard>> playingGrids;
    private Map<String, PlayerColour> colours;
    private int[] publicCards = new int[CARDS_ON_TABLE];
    private CardColour goldDeckColour;
    private CardColour resDeckColour;
    private Map<String, Integer> points;
    private int[] objectives = new int[N_OBJECTIVES];
    private List<Integer> cardsInHand;
    private int[] objectivesToChoose = new int[N_OBJECTIVES];
    private int secretObjective;
    private Integer remainingRounds;
    private List<Coordinate> availablePositions;
    private List<ClientMessage> messages;
    private int numWinners;
    private LinkedHashMap<String, Integer> classification;
    private String errorMessage;
    private UserInterface ui;

    /**
     * Class constructor
     */
    public ViewModel() {
        this.messages = new ArrayList<>();
        this.cardsInHand = new ArrayList<>();
        this.playingGrids = new HashMap<>();
        this.nicknames = new ArrayList<>();
        this.points = new HashMap<>();
        this.colours = new HashMap<>();
        this.availablePositions = new ArrayList<>();
        this.classification = new LinkedHashMap<>();
        this.lobbies = new HashMap<>();
    }

    /**
     * Ad a UserInterface as a listener of the ViewModel
     * @param ui the UserInterface
     */
    public void addListener(UserInterface ui) {
        this.ui = ui;
    }

    /**
     * Notify the UserInterface that a property has changed
     * @param p the PropertyChange that specifies which property has changed
     */
    private void notifyPropertyChange(PropertyChange p) {
        ui.propertyChange(p);
    }

    /**
     * Update the ViewModel when the nickname is registered
     * @param nickname the registered nickname
     */
    public void nicknameEstablishedUpdate(String nickname){
        this.myNickname = nickname;
        notifyPropertyChange(new PropertyNicknameSet(nickname));
    }

    /**
     * Update the ViewModel when a list of available lobbies is received
     * @param lobbies the list of available lobbies
     */
    public void lobbiesNonCompletedUpdate(Map<String, Integer> lobbies){
        this.lobbies.putAll(lobbies);
        notifyPropertyChange(new PropertyLobbiesNonCompleted(lobbies));
    }

    /**
     * Update the ViewModel when some players joined the lobby
     * @param nicknames the nicknames of the players who joined the lobby
     * @param state the new state of the game
     */
    public void playersAddedUpdate(List<String> nicknames, State state) {
        this.nicknames = nicknames;
        this.state = state;

        notifyPropertyChange(new PropertyPlayersAdded(nicknames));
        notifyPropertyChange(new PropertyStateChange(state));
    }

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
    public void matchStartedUpdate(List<String> nicknames, String turn, State state, Map<String, Integer> startCards, CardColour goldDeckColour, CardColour resDeckColour, int[] publicCards) {
        this.turn = turn;
        this.state = state;
        this.nicknames = nicknames;
        for(String nickname : nicknames) {
            playingGrids.put(nickname, new ArrayList<>());
            points.put(nickname, 0);
        }
        notifyPropertyChange(new PropertyPlayersInMatch(nicknames));
        this.goldDeckColour = goldDeckColour;
        this.resDeckColour = resDeckColour;
        this.cardsInHand.add(startCards.get(myNickname));
        for(int i = 0; i<CARDS_ON_TABLE; i++) {
            this.publicCards[i] = publicCards[i];
            notifyPropertyChange(new PropertyPublicCard(i, publicCards[i]));
        }

        notifyPropertyChange(new PropertyDeckColour(goldDeckColour, 4, false));
        notifyPropertyChange(new PropertyDeckColour(resDeckColour, 5, true));

        notifyPropertyChange(new PropertyCardInHand(cardsInHand));

        notifyPropertyChange(new PropertyStateChange(state));
        if(turn.equals(myNickname)) {
            notifyPropertyChange(new PropertyYourTurn());
        }
        else {
            notifyPropertyChange(new PropertyTurnChange(turn));
        }
    }

    /**
     * Update the ViewModel when a start card is placed
     * @param nickname the player who placed his start card
     * @param startCard the index of the start card placed
     * @param side the side on which it was placed (TRUE = front, FALSE = back)
     * @param turn the nickname of the player whose turn is now
     * @param state the new state of the game
     */
    public void startCardPlacedUpdate(String nickname, int startCard, boolean side, String turn, State state){

        Coordinate c1 = new Coordinate(START_CARD_ROW, START_CARD_COL);
        ClientCard c = new ClientCard(startCard,side,c1);
        
        if(myNickname.equals(nickname))
            for(int i = 0; i<cardsInHand.size();i++) {
                if (cardsInHand.get(i).equals(startCard)) {
                    cardsInHand.remove(i);
                    i--;
                }
            }

        playingGrids.get(nickname).add(c);
        notifyPropertyChange(new PropertyCardPlaced(nickname, startCard, side, c1 ));

        if(this.state!=state) {
            this.state = state;
            notifyPropertyChange(new PropertyStateChange(state));
        }

        this.turn = turn;
        if(turn.equals(myNickname)) {
            notifyPropertyChange(new PropertyYourTurn());
        }
        else {
            notifyPropertyChange(new PropertyTurnChange(turn));
        }
    }

    /**
     * Update the ViewModel when a player selected his colour
     * @param nickname the player who selected a colour
     * @param colour the selected colour
     * @param turn the nickname of the player whose turn is now
     * @param state the new state of the game
     */
    public void colourSelectedUpdate(String nickname, PlayerColour colour, String turn, State state){
        colours.put(nickname, colour);
        notifyPropertyChange(new PropertyPlayerColour(nickname, colour, nickname.equals(myNickname)));

        if(this.state!=state) {
            this.state = state;
            notifyPropertyChange(new PropertyStateChange(state));
        }

        this.turn = turn;
        if(turn.equals(myNickname)) {
            notifyPropertyChange(new PropertyYourTurn());
        }
        else {
            notifyPropertyChange(new PropertyTurnChange(turn));
        }
    }

    /**
     * Update the ViewModel when the cards have been distributed
     * @param cardsDistributed the cards that have been distributed to each player
     * @param secretObjectives the two secret objectives for each player
     * @param publicObjectives the two public objectives
     * @param turn the nickname of the player whose turn is now
     * @param state the new state of the game
     */
    public void cardsDistributedUpdate(Map<String, List<Integer>> cardsDistributed, Map<String, int[]> secretObjectives, int[] publicObjectives, String turn, State state) {

        for (int j = 0; j < N_OBJECTIVES; j++) {
            this.objectives[j] = publicObjectives[j];
            notifyPropertyChange(new PropertyPublicObjective(this.objectives[j]));
            this.objectivesToChoose[j] = secretObjectives.get(myNickname)[j];
            notifyPropertyChange(new PropertySecretObjective(this.objectivesToChoose[j]));
        }
        for(int c : cardsDistributed.get(myNickname)) {
            cardsInHand.add(c);
        }
        notifyPropertyChange(new PropertyCardInHand(cardsInHand));

        this.state = state;
        notifyPropertyChange(new PropertyStateChange(state));

        this.turn = turn;
        if(turn.equals(myNickname)) {
            notifyPropertyChange(new PropertyYourTurn());
        }
        else {
            notifyPropertyChange(new PropertyTurnChange(turn));
        }
    }

    /**
     * Update the ViewModel when a player has selected his secret objective
     * @param nickname the player who selected his objective
     * @param secretObjective the index of the selected objective
     * @param turn the nickname of the player whose turn is now
     * @param state the new state of the game
     */
    public void objectiveSelectedUpdate(String nickname, int secretObjective, String turn, State state){
        if(myNickname.equals(nickname)) {
            this.secretObjective = secretObjective;
            notifyPropertyChange(new PropertySecretObjective(secretObjective));
        }

        if(this.state!=state) {
            this.state = state;
            notifyPropertyChange(new PropertyStateChange(state));
        }

        this.turn = turn;
        if(turn.equals(myNickname)) {
            notifyPropertyChange(new PropertyYourTurn());
        }
        else {
            notifyPropertyChange(new PropertyTurnChange(turn));
        }
    }

    /**
     * Update the ViewModel when a list of placeable positions is received
     * @param nickname the player who requested the placeable positions around a card
     * @param availablePositions the list of available positions
     */
    public void placeablePositionReturnedUpdate(String nickname, List<Coordinate> availablePositions){

        if(nickname.equals(myNickname)) {
            this.availablePositions.addAll(availablePositions);
            notifyPropertyChange(new PropertyPlaceablePositions(availablePositions));
        }
    }

    /**
     * Update the ViewModel after a player placed a card
     * @param nickname the player who placed a card
     * @param index the index of the placed card
     * @param side the side on which it was placed (TRUE = front, FALSE = back)
     * @param points the number of gained points
     * @param state the new state of the game
     * @param coordinates the position of the placed card on the playing grid
     */
    public void cardPlacedUpdate(String nickname, int index, Boolean side, int points, State state, Coordinate coordinates){
        ClientCard c = new ClientCard(index, side, coordinates);
        if(myNickname.equals(nickname))
            for(int i = 0; i<cardsInHand.size();i++) {
                if (cardsInHand.get(i).equals(index)) {
                    cardsInHand.remove(i);
                    i--;
                }
            }
        playingGrids.get(nickname).add(c);
        notifyPropertyChange(new PropertyCardPlaced(nickname, index, side, coordinates));

        int oldPoints = this.points.get(nickname);
        this.points.put(nickname, oldPoints+points);
        if(points>0) {
            notifyPropertyChange(new PropertyPoints(nickname, oldPoints+points));
        }

        if(nickname.equals(myNickname))
            notifyPropertyChange(new PropertyCardInHand(cardsInHand));

        this.state = state;
        notifyPropertyChange(new PropertyStateChange(state));

    }

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
    public void cardDrawnUpdate(String nickname, int indexDrawnCard, int deckIndex, int newPublicCard, CardColour newGoldDeckColour, CardColour newResDeckColour, String turn, int remaningRounds, State state){

        if(nickname.equals(myNickname)) {
            cardsInHand.add(indexDrawnCard);
            notifyPropertyChange(new PropertyCardInHand(cardsInHand));
        }

        this.remainingRounds = remaningRounds;
        if(remainingRounds <= 2) {
            notifyPropertyChange(new PropertyRemainingRounds(remainingRounds));
        }

        if(deckIndex == 0 || deckIndex == 1) {
            this.publicCards[deckIndex] = newPublicCard;
            notifyPropertyChange(new PropertyPublicCard(deckIndex, newPublicCard));
            this.goldDeckColour = newGoldDeckColour;
            notifyPropertyChange(new PropertyDeckColour(goldDeckColour, 4, true));
        }

        if(deckIndex == 2 || deckIndex == 3){
            this.publicCards[deckIndex] = newPublicCard;
            notifyPropertyChange(new PropertyPublicCard(deckIndex, newPublicCard));
            this.resDeckColour = newResDeckColour;
            notifyPropertyChange(new PropertyDeckColour(resDeckColour, 5, true));
        }

        if(deckIndex == 4) {
            this.goldDeckColour = newGoldDeckColour;
            notifyPropertyChange(new PropertyDeckColour(goldDeckColour, deckIndex, true));
        }

        if(deckIndex == 5){
            this.resDeckColour = newResDeckColour;
            notifyPropertyChange(new PropertyDeckColour(resDeckColour, deckIndex, true));
        }

        this.state = state;
        notifyPropertyChange(new PropertyStateChange(state));

        this.turn = turn;
        if(turn.equals(myNickname)) {
            ui.printPlayingGrid(playingGrids.get(myNickname));
            notifyPropertyChange(new PropertyYourTurn());
        }
        else {
            notifyPropertyChange(new PropertyTurnChange(turn));
        }
    }

    /**
     * Update the ViewModel when a message in the chat is received
     * Only the last 10 messages are memorized
     * @param sender the nickname of the user who sent the message
     * @param publicMess a boolean that specifies if the message is public (TRUE) or private (FALSE)
     * @param chatMessage the content of the message
     */
    public void chatUpdate(String sender, boolean publicMess, String chatMessage){
        ClientMessage cm = new ClientMessage(sender, publicMess, chatMessage);

        if(messages.size() == MAX_NUM_MESS)
            messages.removeFirst();
        messages.add(cm);
        notifyPropertyChange(new PropertyChatMessage(sender, publicMess, (sender.equals(myNickname)), chatMessage));
    }

    /**
     * Update the ViewModel when the game has ended and the results are available
     * @param winners the number of winners
     * @param classification the classification of the match, with the final points of each player
     * @param state the new state of the game
     */
    public void gameEndedUpdate(int winners, LinkedHashMap<String, Integer> classification, State state){
        this.numWinners = winners;
        this.classification.putAll(classification);
        notifyPropertyChange(new PropertyClassification(winners, classification));

        this.state = state;
        notifyPropertyChange(new PropertyStateChange(state));
    }

    /**
     * Update the ViewModel when an error message is received
     * @param errorMessage the error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        ui.propertyChange(new PropertyErrorMessage(this.errorMessage));
    }

    /**
     * Get the nickname of the player
     * @return the nickname of the player
     */
    public String getNickname() {
        return myNickname;
    }
}
