package it.polimi.ingsw.am12.Model.Logic;

import it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards.*;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.*;
import it.polimi.ingsw.am12.Utils.Coordinate;
import it.polimi.ingsw.am12.Utils.JSONParser;

import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.shuffle;

/**
 * Represents an ongoing match with all its components.
 */
public class Match {
    private final int numPlayers;
    private int remainingRounds;
    private final List<Player> playerOrder;
    private int playerTurn;
    private final ObjectiveCard[] publicObj;
    private List<GameCard> startDeck;
    private List<ObjectiveCard> objDeck;
    private DrawTable drawTable;

    private Map<String, ObjectiveCard[]> objToChoose;

    /**
     * Class constructor specifying numPlayers
     * @param numPlayers  An int that represents the number of players that
     *                    is required in order to start the match.
     *                    If numPlayers < 0, creates a Match with 0 players.
     */
    public Match(int numPlayers) {
        this.numPlayers = Math.max(numPlayers, 0);
        this.remainingRounds = 1000;
        this.playerOrder = new ArrayList<>();
        this.playerTurn = 0;
        this.publicObj = new ObjectiveCard[2];
        this.startDeck = new ArrayList<>();
        this.objDeck = new ArrayList<>();
        this.objToChoose = new HashMap<>();
    }

    //testing

    public List<Player> getPlayerOrder(){
        return this.playerOrder;
    }



    /**
     * Get the number of players of the match
     * @return the number of the players of the match
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * Get the list of the nicknames of the players of the match
     * @return a List of the nicknames of the players of the match
     */
    public List<String> getPlayerNames() {
        return playerOrder.stream().map(Player::getNickname).collect(Collectors.toList());
    }

    /**
     * Get the indexes of the public drawable cards
     * @return an int[4] array, that are the indexes of the public drawable cards
     * 0 = gold card 1
     * 1 = gold card 2
     * 2 = resource card 1
     * 3 = resource card 2
     */
    public int[] getPublicCards() {
        GameCard[] publicCards = drawTable.getPublicCards();
        int [] indexes = new int[4];
        for(int i =0; i<publicCards.length; i++) {
            indexes[i] = publicCards[i].getIndex();

        }
        return indexes;
    }

    /**
     * Get the colour of the first card of each deck
     * @return a String[2] array, that are the colours of the first card of each deck
     * 0 = gold deck
     * 1 = resource deck
     */
    public String[] getDeckColours() {
        String [] colours = new String[2];
        colours[0] = drawTable.getColorTopGoldDeck();
        colours[1] = drawTable.getColorTopResDeck();
        return colours;
    }

    /**
     * Get the cards in the hand of a player.
     * @param nickname A String that identifies the player.
     * @return A List of Integers that are the indexes of the cards in the hand of the player.
     */
    public List<Integer> getCardsInHand(String nickname) {
        return playerOrder.stream()
                .filter(player -> Objects.equals(player.getNickname(), nickname))
                .flatMap(player -> player.getCardsInHand().stream())
                .map(GameCard::getIndex)
                .collect(Collectors.toList());
    }

    /**
     * Get the player whose turn is now
     * @return a String that is a nickname of the player whose turn is now
     */
    public String getPlayerTurn(){
        return playerOrder.get(playerTurn).getNickname();
    }

    /**
     * Get the last placed card of a player
     * @param nickname  A String that identifies the player.
     * @return An int that is the index of the last placed card.
     */
    public int getLastPlacedCard(String nickname) {
        return playerOrder.stream()
                .filter(player -> Objects.equals(player.getNickname(), nickname))
                .map(Player::getPlayingGrid)
                .map(PlayingGrid::getLastPlacedCard)
                .map(GameCard::getIndex)
                .findFirst().orElse(-1);
    }

    /**
     * Get the side of the last placed card of a player
     * @param nickname  A String that identifies the player.
     * @return true if the last card was placed on the front, otherwise false.
     */
    public Boolean getLastPlacedCardSide(String nickname) {
        return playerOrder.stream()
                .filter(player -> Objects.equals(player.getNickname(), nickname))
                .map(Player::getPlayingGrid)
                .map(PlayingGrid::getLastPlacedCard)
                .map(GameCard::getWhichSide)
                .findFirst().orElse(null);
    }

    /**
     * Get the colour of a player
     * @param nickname  A String that identifies the player.
     * @return the colour of the player.
     */
    public PlayerColour getPlayerColour(String nickname) {
        return playerOrder.stream()
                .filter(player -> Objects.equals(player.getNickname(), nickname))
                .map(Player::getColour)
                .findFirst().orElse(null);
    }

    /**
     * Get a couple of objective cards, from which a player can choose his secret objective.
     * @param nickname  A String that identifies the player.
     * @return an array of 2 int, that are the indexes of 2 objective cards.
     */
    public int[] getObjectivesToChoose(String nickname) {
        int[] obj = new int[2];
        obj[0]=objToChoose.get(nickname)[0].getObjIndex();
        obj[1]=objToChoose.get(nickname)[1].getObjIndex();

        return obj;
    }

    /**
     * Get the secret objective of a player.
     * @param nickname A String that identifies the player.
     * @return An int that is the index of the player's secret objective.
     */
    public Integer getSecretObjective(String nickname) {
        return playerOrder.stream()
                .filter(player -> Objects.equals(player.getNickname(), nickname))
                .map(Player::getObjectiveCard)
                .map(ObjectiveCard::getObjIndex)
                .findFirst().orElse(null);
    }

    /**
     * Get the public objectives.
     * @return An array of 2 int, that are the indexes of public objectives.
     */
    public int[] getPublicObjectives() {
        int[] obj = new int[2];
        obj[0] = publicObj[0].getObjIndex();
        obj[1] = publicObj[1].getObjIndex();
        return obj;
    }

    /**
     * Check whether it's the turn of a specific player
     * @param nickname A String to identify the player
     * @return true if it's the turn of the player, otherwise false.
     * @throws WrongInformationException if the player is not part of the match.
     */
    public boolean isTurn(String nickname) throws WrongInformationException {
        if(!getPlayerNames().contains(nickname)) {
            throw new WrongInformationException("This player is not part of this match");
        }
        int p = -1;
        for(int i =0; i<playerOrder.size(); i++) {
            if(playerOrder.get(i).getNickname().equals(nickname)) {
                p = i;
            }
        }

        return p==playerTurn;
    }


    /**
     * Add a player to the match, only if the match has not reached the maximum number of players
     * @param nickname  A string that identifies the user
     */
    public void addPlayer(String nickname){
        if(playerOrder.size() < numPlayers) {
            playerOrder.add(new Player(nickname));
        }
    }

    /**
     * Set a random player order.
     */
    public void randomizePlayerOrder() {
        shuffle(playerOrder);
    }

    /**
     * Initialize the decks for the match
     */
    public void createDecks () {
        drawTable = new DrawTable();
        JSONParser parser = new JSONParser();
        this.objDeck = parser.parseObjectiveCards();
        for(ObjectiveCard card : objDeck) {
            card.calculateDeltas();
        }
        shuffle(objDeck);
        this.startDeck = parser.parseStartCards();
        shuffle(startDeck);
    }

    /**
     * Assign a starter card to each player
     */
    public void assignStartCards() {
        for(Player player: playerOrder) {
            GameCard c = startDeck.getFirst();
            startDeck.removeFirst();
            player.addCardInHand(c);
        }
    }

    /**
     * Place the starter card on the grid of the player.
     * @param nickname A String that identifies the player.
     * @param selectedSide A boolean that indicates the selected side
     *                     of the starter card: TRUE = front; FALSE = back
     * @throws InvalidPlacementException if a start card has already been placed.
     */
    public void placeStartCard(String nickname, Boolean selectedSide) throws InvalidPlacementException {
        for(Player player: playerOrder)
            if(player.getNickname().equals(nickname)) {
                GameCard card = player.getCardsInHand().getFirst();
                player.removeCardFromHand(card);
                card.setValidSide(selectedSide);
                player.placeStartCard(40, 40, card);
            }   
    }

    /**
     * Assign a color to the player.
     * @param nickname A String that identifies the player.
     * @param selectedColour the colour chosen by th player.
     */
    public void setPlayerColour(String nickname, PlayerColour selectedColour) {
        for(Player player: playerOrder) {
            if(player.getNickname().equals(nickname)) {
                player.setColour(selectedColour);
            }
        }
    }

    /**
     * Draw an objective card from the deck
     * @return the drawn card
     * @throws EmptyDeckException if the objective deck is empty
     */
    public ObjectiveCard drawObjective() throws EmptyDeckException {
        if(objDeck.isEmpty()) {
            throw new EmptyDeckException("Objective deck is empty");
        }
        ObjectiveCard obj = objDeck.getFirst();
        objDeck.removeFirst();
        return obj;
    }

    /**
     * Distribute cards to the players, in order to start the match.
     * @throws EmptyDeckException if there aren't enough cards in the decks
     */
    public void distributeCards() throws EmptyDeckException {
        publicObj[0] = drawObjective();
        publicObj[1] = drawObjective();
        for(Player player : playerOrder) {
            player.addCardInHand(drawTable.drawHiddenResource());
            player.addCardInHand(drawTable.drawHiddenResource());
            player.addCardInHand(drawTable.drawHiddenGold());

            ObjectiveCard[] obj = new ObjectiveCard[2];
            obj[0]= drawObjective();
            obj[1]= drawObjective();
            objToChoose.put(player.getNickname(), obj);
        }
    }

    /**
     * Assign the selected secret objective to each player.
     * @param nickname A String that identifies the player
     * @param selectedObjective A boolean that indicates the selected secret objective:
     *                          TRUE = first objective; FALSE = second objective.
     */
    public void setPlayerObjective(String nickname, boolean selectedObjective) {
        for(Player player: playerOrder) {
            if(player.getNickname().equals(nickname)) {
                if(selectedObjective)
                {
                    player.setObjectiveCards(objToChoose.get(player.getNickname())[0]);
                }
                else
                {
                    player.setObjectiveCards(objToChoose.get(player.getNickname())[1]);
                }
            }
        }
    }

    /**
     * Check which positions are available for placing, around a selected card,
     * for the player whose turn is now.
     * @param xpos An int that represents the row of the selected card.
     * @param ypos An int that represents the column of the selected card.
     * @return a List of Coordinates, that are the available positions.
     * @throws InvalidSearchPositionException if the selected position is invalid.
     */
    public List<Coordinate> getPlaceablePositions(int xpos, int ypos) throws InvalidSearchPositionException{
        Player currentPlayer = playerOrder.get(playerTurn);
        return currentPlayer.getPlaceablePositions(xpos, ypos);
    }

    /**
     * Place the selected card on the grid of the player whose turn is now.
     * @param index An int that indicates which card has to be placed
     * @param side  A boolean that indicates the selected side of the card:
     *              TRUE = front; FALSE = back;
     * @param xpos  An int that represents the row of the position where to place the card.
     * @param ypos  An int that represents the column of the position where to place the card.
     * @return the number of points obtained with the placement
     * @throws InvalidPlacementException if the placement fails.
     */
    public int placeCard(int index, boolean side, int xpos, int ypos) throws InvalidPlacementException{
        Player currentPlayer = playerOrder.get(playerTurn);
        GameCard card = currentPlayer.getCardsInHand().get(index);
        card.setValidSide(side);
        currentPlayer.removeCardFromHand(card);
        return currentPlayer.placePlayingGrid(xpos, ypos, card);
    }

    /**
     * Draw a card for the player whose turn is now
     * @param index An int that indicates which card has to be drawn.
     *              - index = 0  : public gold 1;
     *              - index = 1  : public gold 2;
     *              - index = 2  : public resource 1;
     *              - index = 3  : public resource 2;
     *              - index = 4  : hidden gold;
     *              - index = 5  : hidden resource;
     * @throws EmptyDeckException if the selected deck is empty.
     */
    public void drawCard(int index) throws EmptyDeckException {
        Player currentPlayer = playerOrder.get(playerTurn);
        currentPlayer.addCardInHand(drawTable.drawCard(index));
    }

    /**
     * Pass the turn to the next player.
     * @return an int:
     *  if the return value is <=2, it's the number of remaining rounds.
     *  if the return value is > 2, it means that the match has not reached its final stage yet.
     */
    public int nextTurn() {
        if(checkEndGameCondition()) {
            remainingRounds = 2;
        }
        if (playerTurn == numPlayers-1)
        {
            playerTurn = 0;
            remainingRounds = remainingRounds-1;
        }
        else
        {
            playerTurn = playerTurn + 1;
        }
        return remainingRounds;
    }

    /**
     * @return TRUE if the match is entering the final stage
     */
    public boolean checkEndGameCondition() {
        return remainingRounds > 2 &&
               (playerOrder.get(playerTurn).getPoints() >= 20 ||
                        (drawTable.getColorTopGoldDeck() == null && drawTable.getColorTopResDeck() == null));
    }
}