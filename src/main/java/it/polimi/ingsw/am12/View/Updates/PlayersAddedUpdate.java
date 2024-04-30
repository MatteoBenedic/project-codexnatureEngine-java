package it.polimi.ingsw.am12.View.Updates;

import java.util.List;
import it.polimi.ingsw.am12.Model.Logic.State;

public class PlayersAddedUpdate implements Update{

    List<String> nicknames;
    State state;

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

    public List<String> getNicknames() {
        return nicknames;
    }

    public State getState() {
        return state;
    }

    public String getTurn() {
        if(state == State.INITIALIZATION)
            return nicknames.getFirst();
        else return "";
    }
}
