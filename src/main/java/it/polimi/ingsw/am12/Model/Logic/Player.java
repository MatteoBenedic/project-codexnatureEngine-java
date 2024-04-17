package it.polimi.ingsw.am12.Model.Logic;

import it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards.*;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.*;
import it.polimi.ingsw.am12.Utils.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a player in the match.
 */
public class Player {

    private PlayerColour colour;
    private String nickname;
    private int points;
    private ObjectiveCard objectiveCard;
    private List<GameCard> cardsInHand;

    private PlayingGrid playingGrid;

    private int countCompletedObj;

    /**
     * Class constructor: instantiates a new Player.
     * @param nickname the nickname of the player
     */
    public Player(String nickname) {
        this.nickname = nickname;
        this.colour = null;
        this.objectiveCard = null;
        this.cardsInHand = new ArrayList<>();
        this.playingGrid = new PlayingGrid();
        this.points = 0;
    }


    /**
     * Sets the colour for the player.
     * @param colour the colour to assign to the player
     */
    public void setColour(PlayerColour colour) {
        this.colour = colour;
    }

    /**
     * Gets the colour of the player
     * @return the colour of the player
     */
    public PlayerColour getColour (){
        return this.colour;
    }

    /**
     * Gets the secret objective of the player.
     * @return the secret objective of the player.
     */
    public ObjectiveCard getObjectiveCard() {
        return objectiveCard;
    }


    /**
     * Sets the secret objective card for the player.
     * @param objectivecard the objective card to set as secret objective.
     */
    public void setObjectiveCards(ObjectiveCard objectivecard) {
        this.objectiveCard = objectivecard;
    }

    /**
     * Gets the cards in the hand of the player.
     * @return the cards in the hand of the player.
     */
    public List<GameCard> getCardsInHand() {
        return cardsInHand;
    }


    /**
     * Add a card in the hand of the player
     * @param c the card to add
     * @throws IllegalStateException if the hand is already full
     */
        public void addCardInHand(GameCard c) throws IllegalStateException{
            if (this.cardsInHand.size() >= 3) {
                throw new IllegalStateException("The hand is full. You cannot add more cards");
            }
            this.cardsInHand.add(c);
        }


    /**
     * Remove a card from the hand of the player
     * @param c the card to remove
     * @throws IllegalStateException if the given card is not in the hand of the player
     */
    public void removeCardFromHand(GameCard c) {
        if (cardsInHand.isEmpty()) {
            throw new IllegalStateException("The hand is empty");
        }
        if(!cardsInHand.remove(c)) {
            throw new IllegalStateException("There is no such card in the hand");
        }

    }


    /**
     * Gets the nickname of the player.
     * @return the nickname as a string.
     */
    public String getNickname(){
        return nickname;
    }

    /**
     * Gets the player's playingGrid.
     * @return the playingGrid
     */
    public PlayingGrid getPlayingGrid() {
        return playingGrid;
    }


    /**
     * Place a card on the PlayingGrid, in the given position
     * @param i the row of the position
     * @param j the column of the position
     * @param c the card to place
     * @throws InvalidPlacementException if the placement fails.
     */
    public void placePlayingGrid(int i, int j,GameCard c) throws InvalidPlacementException{
            points += this.playingGrid.place(i, j, c);
    }

    /**
     * Place a start card on the PlayingGrid, in the given position.
     * @param i the row of the position
     * @param j the column of the position
     * @param c the start card to place
     * @return true if the placement is successful, otherwise false
     */
    public boolean placeStartCard(int i, int j, GameCard c) {
        return this.playingGrid.placeStartCard(i, j, c);
    }

    /**
     * Check which positions are available for placing, around a selected position
     * @param i the row of the selected position
     * @param j the column of the selected position
     * @return a List of Coordinates, that are the available positions
     */
    public List<Coordinate> getPlaceablePositions(int i, int j) throws InvalidSearchPositionException{
            return this.playingGrid.getPlaceablePosition(i, j);
    }

    /**
     * Get points
     * @return the amount of points of the Player
     */
    public int getPoints(){
        return this.points;
    }
}

