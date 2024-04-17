package it.polimi.ingsw.am12.Model.Logic;

import it.polimi.ingsw.am12.Model.CardDesign.GameCard.*;
import it.polimi.ingsw.am12.Utils.JSONParser;

import java.util.List;

import static java.util.Collections.shuffle;

/**
 * Contains the cards that are ready to be picked by the players,
 * and provide the methods to draw them
 */
public class DrawTable {

    private GameCard gold1;
    private GameCard gold2;
    private GameCard res1;
    private GameCard res2;
    private List<GameCard> goldDeck;
    private List<GameCard> resDeck;

    /**
     * Class constructor
     */
    public DrawTable(){
        initializeDecks();

        try {
            gold1=drawHiddenGold();
            gold2=drawHiddenGold();
            res1=drawHiddenResource();
            res2=drawHiddenResource();
        }
        catch (EmptyDeckException e) {
            throw new RuntimeException("Error in decks initialization");
        }
    }

    /**
     * @return an array of the 4 public drawable cards:
     * 0 = gold card 1
     * 1 = gold card 2
     * 2 = resource card 1
     * 3 = resource card 2
     */
    public GameCard[] getPublicCards() {
        GameCard[] cards = new GameCard[4];
        cards[0] = gold1;
        cards[1] = gold2;
        cards[2] = res1;
        cards[3] = res2;
        return cards;
    }

    /**
     * Create 40 resource cards and put them into resDeck in random order.
     * Create 40 gold cards and put them into goldDeck in random order.
     */
    public void initializeDecks() {
        JSONParser jsonParser = new JSONParser();
        this.resDeck = jsonParser.parseResourceCards();
        shuffle(resDeck);
        this.goldDeck = jsonParser.parseGoldCards();
        shuffle(goldDeck);
    }

    /**
     * @return the color of the top card of the gold deck
     * (null if the deck is empty)
     */
    public String getColorTopGoldDeck(){
        if(goldDeck.isEmpty()) {
            return null;
        }
        return goldDeck.getFirst().getColour();
    }

    /**
     * @return the color of the top ca of the resource deck
     * (null if the deck is empty)
     */
    public String getColorTopResDeck(){
        if(resDeck.isEmpty()) {
            return null;
        }
        return resDeck.getFirst().getColour();
    }

    /**
     * Draw a gold card from the deck.
     * @return the drawn card
     * @throws EmptyDeckException if the gold deck is empty
     */
    public GameCard drawHiddenGold() throws EmptyDeckException {
        if(goldDeck.isEmpty()) {
            throw new EmptyDeckException("Gold deck is empty");
        }
        GameCard card = goldDeck.getFirst();
        goldDeck.removeFirst();
        return card;
    }

    /**
     * Draw a resource card from the deck.
     * @return the drawn card
     * @throws EmptyDeckException if the resource deck is empty
     */
    public GameCard drawHiddenResource() throws EmptyDeckException {
        if(resDeck.isEmpty()) {
            throw new EmptyDeckException("Resource deck is empty");
        }
        GameCard card = resDeck.getFirst();
        resDeck.removeFirst();
        return card;
    }

    /**
     * Draw the first public gold card.
     * @return the drawn card
     * @throws EmptyDeckException if there is not a card in that position
     */
    public GameCard drawFirstGold() throws EmptyDeckException {
        if(gold1 == null) {
            throw new EmptyDeckException("There is not a card in this position");
        }
        GameCard card = gold1;
        try {
            gold1 = drawHiddenGold();
        }
        catch (EmptyDeckException e) {
            try {
                gold1 = drawHiddenResource();
            }
            catch (EmptyDeckException e2) {
                gold1 = null;
            }
        }

        return card;
    }

    /**
     * Draw the second public gold card.
     * @return the drawn card
     * @throws EmptyDeckException if there is not a card in that position
     */
    public GameCard drawSecondGold() throws EmptyDeckException {
        if(gold2 == null) {
            throw new EmptyDeckException("There is not a card in this position");
        }
        GameCard card = gold2;
        try {
            gold2 = drawHiddenGold();
        }
        catch (EmptyDeckException e) {
            try {
                gold2 = drawHiddenResource();
            }
            catch (EmptyDeckException e2) {
                gold2 = null;
            }
        }

        return card;
    }

    /**
     * Draw the first public resource card.
     * @return the drawn card
     * @throws EmptyDeckException if there is not a card in that position
     */
    public GameCard drawFirstResource() throws EmptyDeckException {
        if(res1 == null) {
            throw new EmptyDeckException("There is not a card in this position");
        }
        GameCard card = res1;
        try {
            res1 = drawHiddenResource();
        }
        catch (EmptyDeckException e) {
            try {
                res1 = drawHiddenGold();
            }
            catch (EmptyDeckException e2) {
                res1 = null;
            }
        }

        return card;
    }

    /**
     * Draw the second public resource card.
     * @return the drawn card
     * @throws EmptyDeckException if there is not a card in that position
     */
    public GameCard drawSecondResource() throws EmptyDeckException {
        if(res2 == null) {
            throw new EmptyDeckException("There is not a card in this position");
        }
        GameCard card = res2;
        try {
            res2 = drawHiddenResource();
        }
        catch (EmptyDeckException e) {
            try {
                res2 = drawHiddenGold();
            }
            catch (EmptyDeckException e2) {
                res2 = null;
            }
        }

        return card;
    }

    /**
     * Draw a card from the drawTable
     * @param index An int that specifies which card has to be drawn:
     * - index <=0  : public gold 1;
     * - index = 1  : public gold 2;
     * - index = 2  : public resource 1;
     * - index = 3  : public resource 2;
     * - index = 4  : hidden gold;
     * - index >=5  : hidden resource;
     * @return the drawn card
     * @throws EmptyDeckException there is not a card in the selected position
     */
    public GameCard drawCard(int index) throws EmptyDeckException{
        if(index<=0) {
            return drawFirstGold();
        }
        if(index==1) {
            return drawSecondGold();
        }
        if(index==2) {
            return drawFirstResource();
        }
        if(index==3) {
            return drawSecondResource();
        }
        if(index==4) {
            return drawHiddenGold();
        }
        return drawHiddenResource();
    }
}