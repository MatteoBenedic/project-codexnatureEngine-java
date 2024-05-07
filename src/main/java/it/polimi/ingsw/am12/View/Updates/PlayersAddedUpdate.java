package it.polimi.ingsw.am12.View.Updates;

import java.util.List;

import it.polimi.ingsw.am12.MemoryUpdater;
import it.polimi.ingsw.am12.Model.Logic.State;

/**
 * Update to switch from lobby to the match  .
 */
public class PlayersAddedUpdate implements Update{

    List<String> nicknames;

    State state;

    /**
     * Instantiates a new Players added update.
     *
     * @param nicknames the list of nicknames for all player in the game
     * @param state     the state
     */
    public PlayersAddedUpdate(List<String> nicknames, State state) {
        this.nicknames = nicknames;
        this.state = state;
    }

    public String toString(String receiver) {
        String message = "\nPlayers in the match: ";
        for(String nickname : nicknames) {
            message += " " + nickname;
        }
        if(state.equals(State.LOBBY)) {
            message += "\nWaiting for more players...";
        }
        if(state.equals(State.INITIALIZATION)) {
            message += "\nAll players have joined. Ready to start the match.";
        }
        return message;
    }

    /**
     * Gets nicknames.
     *
     * @return the nicknames
     */
    public List<String> getNicknames() {
        return nicknames;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public State getState() {
        return state;
    }

    public String getTurn() {
        if(state == State.INITIALIZATION)
            return nicknames.getFirst();
        else return "";
    }

    @Override
    public void executeUpdate(MemoryUpdater memoryUpdater) {

    }
}
