package it.polimi.ingsw.am12.Model.Logic;

import it.polimi.ingsw.am12.Controller.EventListener;
import it.polimi.ingsw.am12.Controller.Events.*;
import it.polimi.ingsw.am12.Utils.Coordinate;
import java.security.InvalidParameterException;
import java.util.*;

/**
 * This class is the Model of the game. It contains the
 * state of the game and provides some methods to update this
 * state, according to the game rules.
 * When the state of the game changes, an Event is created and notified to the controller.
 */
public class GameModel{
    private final Match match;
    private final List<String> lobby;
    private final List<PlayerColour> availableColours;
    private final List<EventListener> listeners = new ArrayList<>();

    private static final int MAX_NUMBER_OF_ROW= 81;
    private static final int MAX_NUMBER_OF_COL= 81;
    private static final int MAX_NUMBER_OF_PLAYER= 4;
    private static final int MIN_NUMBER_OF_PLAYER= 2;
    private static final int MAX_NUMBER_OF_CARDS_IN_HAND = 3;

    /**
     * Class constructor specifying numPlayers
     * @param numPlayers  An int that represents the number of players that
     *                    is required in order to start the match (min 2, max 4)
     * @throws WrongNumberOfPlayersException If numPlayers < 2 or numPlayers > 4
     */
    public GameModel(int numPlayers) throws WrongNumberOfPlayersException {
        if(numPlayers >= MIN_NUMBER_OF_PLAYER && numPlayers <=MAX_NUMBER_OF_PLAYER)
        {
            this.match = new Match(numPlayers);
        } else if (numPlayers < MIN_NUMBER_OF_PLAYER) {
            throw new WrongNumberOfPlayersException("The minimum number of players is "+MIN_NUMBER_OF_PLAYER);
        }
        else {
            throw new WrongNumberOfPlayersException("The maximum number of players is "+MAX_NUMBER_OF_PLAYER);
        }

        lobby = new ArrayList<>();
        availableColours = new ArrayList<>();
        availableColours.add(PlayerColour.GREEN);
        availableColours.add(PlayerColour.RED);
        availableColours.add(PlayerColour.YELLOW);
        availableColours.add(PlayerColour.BLUE);
    }

    /**
     * Subscribe a listener to this GameModel
     * @param listener the EventListener to add as a listener of this GameModel
     */
    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    /**
     * Perform an Event, that will be listened by the subscribed listeners
     * @param e the Event to perform
     */
    private void performEvent(Event e) {
        for(EventListener listener : listeners) {
            listener.actionPerformed(e);
        }
    }

    /**
     * Add a user to the match lobby.
     * @param nickname A String to identify the new user.
     * @throws InvalidParameterException if the nickname is null
     * @throws DuplicateNicknameException if the nickname is already in use
     * @throws WrongNumberOfPlayersException if there is the maximum number of players in the lobby already.
     */
    public void addPlayerToLobby(String nickname) throws WrongNumberOfPlayersException, DuplicateNicknameException {
        if(nickname==null) {
            throw new InvalidParameterException("The nickname is null");
        }
        if(lobby.contains(nickname)) {
            throw new DuplicateNicknameException();
        }
        if(lobby.size() == match.getNumPlayers()) {
            throw new WrongNumberOfPlayersException("The lobby is already full");
        }

        lobby.add(nickname);

        if(lobby.size() == match.getNumPlayers()) {
            PlayersAddedEvent e = new PlayersAddedEvent(lobby);
            performEvent(e);
        }
    }

    /**
     * Add the users in the lobby as players of the match
     * @throws WrongNumberOfPlayersException    if the number of nicknames in the lobby differs from the
     *                                          number of players of the match. In this case no player is added.
     */
    public void addPlayersToMatch() throws WrongNumberOfPlayersException {
        if(lobby.size() != match.getNumPlayers())
        {
            throw new WrongNumberOfPlayersException("The number of players in the lobby does not correspond to the number of players of the match");
        }
        for (String nickname : lobby) {
            this.match.addPlayer(nickname);
        }
        match.randomizePlayerOrder();
        match.createDecks();
        match.assignStartCards();

        Map<String, Integer> startCards = new HashMap<>();
        for(String nickname : match.getPlayerNames()) {
            startCards.put(nickname, match.getCardsInHand(nickname).getFirst());
        }
        MatchStartedEvent e = new MatchStartedEvent(
                match.getPlayerNames(),
                startCards,
                match.getDeckColours()[0],
                match.getDeckColours()[1],
                match.getPublicCards(),
                match.getPlayerTurn());
        performEvent(e);
    }

    /**
     * Place the starter card on the grid of each the player.
     * @param nickname A String that identifies the player.
     * @param selectedSide A boolean that indicates the selected side
     *                     of the starter card: TRUE = front; FALSE = back
     * @throws InvalidParameterException if any of the parameters is null
     * @throws WrongInformationException if the player is not part of the match
     * @throws NotYourTurnException  if it's not the turn of the player
     * @throws InvalidPlacementException  if a start card has already been placed for the player
     */
    public void placeStartCard(String nickname, boolean selectedSide)
            throws InvalidPlacementException, WrongInformationException, NotYourTurnException, InvalidParameterException {
        if(nickname==null) {
            throw new InvalidParameterException("The nickname is null");
        }
        if(!match.isTurn(nickname))
        {
            throw new NotYourTurnException("It's not your turn");
        }
        match.placeStartCard(nickname, selectedSide);
        nextTurn();

        StartCardPlacedEvent e = new StartCardPlacedEvent(
                nickname,
                match.getLastPlacedCard(nickname),
                match.getLastPlacedCardSide(nickname),
                match.getPlayerTurn());
        performEvent(e);
    }

    /**
     * Assign a colour to a player.
     * @param nickname A String that identifies the player.
     * @param selectedColour the colour chosen by the player
     * @throws InvalidParameterException if any of the parameters is null
     * @throws WrongInformationException if the player is not part of the match
     * @throws NotYourTurnException  if it's not the turn of the player
     * @throws WrongInformationException  if the colour is not available, or some other player selected it
     */
    public void setPlayerColour(String nickname, PlayerColour selectedColour) throws WrongInformationException, NotYourTurnException {
        if(nickname==null) {
            throw new InvalidParameterException("The nickname is null");
        }
        if(selectedColour==null) {
            throw new InvalidParameterException("The selected colour is null");
        }
        if(!match.isTurn(nickname))
        {
            throw new NotYourTurnException("It's not your turn");
        }
        if(!availableColours.contains(selectedColour)) {
            throw new WrongInformationException("This colour is not available");
        }
        match.setPlayerColour(nickname, selectedColour);
        availableColours.remove(selectedColour);
        nextTurn();

        ColourSelectedEvent e = new ColourSelectedEvent(
                nickname,
                match.getPlayerColour(nickname),
                match.getPlayerTurn());
        performEvent(e);
    }

    /**
     * Distribute cards to the players, in order to start the match.
     * @throws EmptyDeckException if the objective deck is empty
     */
    public void distributeCards() throws EmptyDeckException {
        match.distributeCards();

        Map<String, List<Integer>> cardsDistributed = new HashMap<>();
        Map<String, int[]> secretObjectives = new HashMap<>();

        for(String nickname: match.getPlayerNames()){
            cardsDistributed.put(nickname, match.getCardsInHand(nickname));
            secretObjectives.put(nickname, match.getObjectivesToChoose(nickname));
        }
        CardsDistributedEvent e = new CardsDistributedEvent(
                cardsDistributed,
                secretObjectives,
                match.getPublicObjectives(),
                match.getPlayerTurn());
        performEvent(e);
    }

    /**
     * Assign the selected secret objective to a player.
     * @param nickname A String that identifies the player.
     * @param selectedObjective A boolean that indicates the selected objective
     *                          TRUE = first objective; FALSE = second objective
     * @throws InvalidParameterException if any of the parameters is null
     * @throws WrongInformationException if the player is not part of the match
     * @throws NotYourTurnException  if it's not the turn of the player
     */
    public void setPlayerObjective(String nickname, boolean selectedObjective)
            throws WrongInformationException, NotYourTurnException, InvalidParameterException {
        if(nickname==null) {
            throw new InvalidParameterException("The nickname is null");
        }
        if(!match.isTurn(nickname))
        {
            throw new NotYourTurnException("It's not your turn");
        }
        match.setPlayerObjective(nickname, selectedObjective);
        nextTurn();

        ObjectiveSelectedEvent e = new ObjectiveSelectedEvent(
                nickname,
                match.getSecretObjective(nickname),
                match.getPlayerTurn()
        );
        performEvent(e);
    }

    /**
     * Check which positions are available for placing, around a selected card.
     * @param nickname A String that identifies the player
     * @param xpos An int that represents the row of the selected card.
     * @param ypos An int that represents the column of the selected card.
     * @throws InvalidParameterException if the nickname is null
     * @throws WrongInformationException if the player is not part of the match
     * @throws NotYourTurnException if it's not the turn of the player
     * @throws InvalidSearchPositionException if the given position is invalid.
     */
    public void getPlaceablePositions(String nickname, int xpos, int ypos)
            throws InvalidSearchPositionException, NotYourTurnException, WrongInformationException, InvalidParameterException {
        if(nickname == null) {
            throw new InvalidParameterException("The nickname is null");
        }
        if(!match.isTurn(nickname)) {
            throw new NotYourTurnException("It's not your turn");
        }
        if(xpos<0 || xpos> MAX_NUMBER_OF_ROW-1 || ypos<0 || ypos>MAX_NUMBER_OF_COL-1) {
            throw new InvalidSearchPositionException("The given position is not within the playing area.");
        }

        List<Coordinate> availablePositions = match.getPlaceablePositions(xpos, ypos);
        PlaceablePositionsReturnedEvent e = new PlaceablePositionsReturnedEvent(
                nickname,
                availablePositions,
                match.getPlayerTurn());
        performEvent(e);
    }

    /**
     * Place the selected card on the grid of the player.
     * @param nickname A String that identifies the player
     * @param index An int that indicates which card has to be placed.
     *              (0 = first card in hand, 1 = second card in hand ...)
     * @param side  A boolean that indicates the selected side of the card:
     *              TRUE = front; FALSE = back;
     * @param xpos  An int that represents the row of the position where to place the card.
     * @param ypos  An int that represents the column of the position where to place the card.
     *
     * @throws WrongInformationException if the player is not part of the match
     * @throws NotYourTurnException if it's not the turn of the player
     * @throws InvalidParameterException if any of the parameters null or is invalid
     * @throws InvalidPlacementException if the placement fails
     */
    public void placeCard(String nickname, int index, boolean side, int xpos, int ypos)
            throws InvalidParameterException, InvalidPlacementException, NotYourTurnException, WrongInformationException {
        if(nickname == null) {
            throw new InvalidParameterException("The nickname is null");
        }
        if(!match.isTurn(nickname)) {
            throw new NotYourTurnException("It's not your turn");
        }
        if(index<0 || index>MAX_NUMBER_OF_CARDS_IN_HAND-1 || xpos<0 || xpos>MAX_NUMBER_OF_ROW-1 || ypos<0 || ypos>MAX_NUMBER_OF_COL-1) {
            throw new InvalidParameterException("Parameter out of bounds");
        }
        int points = match.placeCard(index, side, xpos, ypos);

        CardPlacedEvent e = new CardPlacedEvent(
                nickname,
                match.getLastPlacedCard(nickname),
                match.getLastPlacedCardSide(nickname),
                points,
                match.getPlayerTurn()
        );
        performEvent(e);
    }

    /**
     * Draw a card for the player.
     * @param nickname A String that identifies the player
     * @param index An int that indicates which card has to be drawn.
     *              - index = 0  : public gold 1;
     *              - index = 1  : public gold 2;
     *              - index = 2  : public resource 1;
     *              - index = 3  : public resource 2;
     *              - index = 4  : hidden gold;
     *              - index = 5  : hidden resource;
     * @throws InvalidParameterException if the nickname is null
     * @throws WrongInformationException if the player is not part of the match
     * @throws NotYourTurnException if it's not the turn of the player
     * @throws InvalidParameterException if index<0 or index>5
     * @throws EmptyDeckException if the selected deck is empty.
     */
    public void drawCard(String nickname, int index)
            throws InvalidParameterException, EmptyDeckException, NotYourTurnException, WrongInformationException {
        if(nickname == null) {
            throw new InvalidParameterException("The nickname is null");
        }
        if(!match.isTurn(nickname)) {
            throw new NotYourTurnException("It's not your turn");
        }
        if(index<0 || index >5) {
            throw new InvalidParameterException("Index out of bounds");
        }
        match.drawCard(index);
        int remainigRounds = nextTurn();

        int newPublicCard = -1;
        if(index<=3) {
            newPublicCard = match.getPublicCards()[index];
        }
        String newGoldDeckColour = null;
        if(index <= 1 || index ==4) {
            newGoldDeckColour = match.getDeckColours()[0];
        }
        String newResDeckColour = null;
        if(index ==2 || index ==3 || index == 5) {
            newResDeckColour = match.getDeckColours()[1];
        }

        CardDrawnEvent e = new CardDrawnEvent(
                nickname,
                match.getCardsInHand(nickname).getLast(),
                index,
                newPublicCard,
                newGoldDeckColour,
                newResDeckColour,
                match.getPlayerTurn(),
                remainigRounds
        );
        performEvent(e);
    }

    /**
     * Pass the turn to the next player
     * @return an int:
     *  if the return value is <=2, it's the number of remaining rounds.
     *  if the return value is > 2, it means that the match has not reached its final stage yet.
     */
    private int nextTurn() {
        return match.nextTurn();
    }

    /**
     * It calculates the endgame points of the match and defines the result of it. It creates a classification ordered
     * from the winner to the worst player
     */
    public void endGame() {
        Map<String, Integer> finalPoints = match.calculateEndgamePoints();

        int winners = match.orderByPoints(finalPoints);

        LinkedHashMap<String, Integer> classification = new LinkedHashMap<>();

        for (String nickname : match.getPlayerNames()){
            classification.put(nickname, match.getPlayerPoints(nickname));
        }

        GameEndedEvent e = new GameEndedEvent(winners, classification);
        performEvent(e);
    }
}