package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.MemoryUpdater;
import it.polimi.ingsw.am12.Model.Logic.State;

import java.util.List;

/**
 * Update to handle when a Card is placed.
 */
public class CardPlacedUpdate implements Update {

    String nickname;

    int index;

    Boolean side;

    int points;

    String turn;

    State state;

    /**
     * Instantiates a new Card placed update.
     *
     * @param nickname the nickname of player
     * @param index    the index of the card
     * @param side     the side, True=front; False = back
     * @param points   the points given by the action
     * @param turn     the turn
     * @param state    the state
     */
    public CardPlacedUpdate(String nickname, int index, Boolean side, int points, String turn, State state) {
        this.nickname = nickname;
        this.index = index;
        this.side = side;
        this.points = points;
        this.turn = turn;
        this.state = state;
    }

    /**
     * Gets nickname.
     *
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Gets index.
     *
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Gets side.
     *
     * @return the side
     */
    public Boolean getSide() {
        return side;
    }

    /**
     * Gets points.
     *
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    public String getTurn() {
        return turn;
    }

    @Override
    public void executeUpdate(MemoryUpdater memoryUpdater) {

    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public State getState() {
        return state;
    }

    public String toString(String receiver) {
        return receiver;
    }
}
