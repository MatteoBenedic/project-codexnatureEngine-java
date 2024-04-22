package it.polimi.ingsw.am12.View;

import it.polimi.ingsw.am12.Controller.EventListener;
import it.polimi.ingsw.am12.Controller.Events.*;
import it.polimi.ingsw.am12.Model.Logic.PlayerColour;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the server side component of the player view.
 * When the player requests an action, it creates an event to notify Controller of the game.
 */
public class VirtualView {
    private final List<EventListener> listeners = new ArrayList<>();
    private final String nickname;
    private String message;

    /**
     * Class constructor
     * @param nickname a String that identifies the player who owns this instance of VirtualView
     */
    public VirtualView(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Subscribe a listener to this VirtualView
     * @param listener the EventListener to add as a listener of this VirtualView
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
     * Get the nickname of the player who owns this instance of VirtualView
     * @return the nickname of the player who owns this instance of VirtualView
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Get the latest message of the VirtualView
     * @return the latest message of the VirtualView
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message of the VirtualView
     * @param message the String to set as the message of the VirtualView
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Join a match
     */
    public void joinMatch() {
        JoinMatchEvent e = new JoinMatchEvent(nickname);
        performEvent(e);
    }

    /**
     * Start a match
     */
    public void startMatch() {
        StartMatchEvent e = new StartMatchEvent();
        performEvent(e);
    }

    /**
     * Place the start card
     * @param selectedSide A Boolean that indicates the selected side
     *                     of the starter card: TRUE = front; FALSE = back
     */
    public void placeStartCard(Boolean selectedSide) {
        PlaceStartCardEvent e = new PlaceStartCardEvent(nickname, selectedSide);
        performEvent(e);
    }


    /**
     * Select a colour
     * @param selectedColour the chosen colour
     */
    public void selectColour(PlayerColour selectedColour) {
        SelectColourEvent e = new SelectColourEvent(nickname, selectedColour);
        performEvent(e);
    }

    /**
     * Distribute the cards in order to start the match
     */
    public void distributeCards(){
        DistributeCardsEvent e = new DistributeCardsEvent();
        performEvent(e);
    }

    /**
     * Select a secret objective
     * @param selectedObjective A Boolean that indicates the selected objective
     *                          TRUE = first objective; FALSE = second objective
     */
    public void selectObjective(Boolean selectedObjective) {
        SelectObjectiveEvent e = new SelectObjectiveEvent(nickname, selectedObjective);
        performEvent(e);
    }

    /**
     * Check which positions are available for placing, around the selected card
     * @param x An int that represents the row of the selected card.
     * @param y An int that represents the column of the selected card.
     */
    public void getPlaceablePositions(int x, int y){
        GetPlaceablePositionsEvent e = new GetPlaceablePositionsEvent(nickname, x, y);
        performEvent(e);
    }

    /**
     * Place a card in the selected position
     * @param index An int that indicates which card has to be placed
     *              (0 = first card in hand, 1 = second card in hand ...)
     * @param side  A boolean that indicates the selected side of the card:
     *              TRUE = front; FALSE = back;
     * @param x  An int that represents the row of the position where to place the card.
     * @param y  An int that represents the column of the position where to place the card.
     */
    public void placeCard(int index, Boolean side, int x, int y) {
        PlaceCardEvent e = new PlaceCardEvent(nickname, index, side, x, y);
        performEvent(e);
    }

    /**
     * Draw a card.
     * @param deckIndex An int that indicates which card has to be drawn.
     *              - index = 0  : public gold 1;
     *              - index = 1  : public gold 2;
     *              - index = 2  : public resource 1;
     *              - index = 3  : public resource 2;
     *              - index = 4  : hidden gold;
     *              - index = 5  : hidden resource;
     */
    public void drawCard(int deckIndex){
        DrawCardEvent e = new DrawCardEvent(nickname, deckIndex);
        performEvent(e);
    }

    /**
     * This function has to be called when there are no remaining rounds, to determine
     * the final result of the game
     */
    public void endGame() {
        EndGameEvent e = new EndGameEvent();
        performEvent(e);
    }
}
