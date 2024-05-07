package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.MemoryUpdater;
import it.polimi.ingsw.am12.Model.Logic.State;

/**
 * Update to handle when the player chooses the objective
 */
public class ObjectiveSelectedUpdate implements Update {

    String nickname;

    int secretObjective;

    String turn;

    State state;

    /**
     * Instantiates a new Objective selected update.
     *
     * @param nickname        the nickname of player
     * @param secretObjective the secret objective chosen
     * @param turn            the turn
     * @param state           the state
     */
    public ObjectiveSelectedUpdate(String nickname, int secretObjective, String turn, State state) {
        this.nickname = nickname;
        this.secretObjective = secretObjective;
        this.turn = turn;
        this.state = state;
    }

    /**
     * Get nickname string.
     *
     * @return the string
     */
    public String getNickname(){
        return nickname;
    }

    /**
     * Get secret objective int.
     *
     * @return the int
     */
    public int getSecretObjective(){
        return secretObjective;
    }

    public String getTurn(){
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
