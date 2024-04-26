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

    public List<String> getNicknames() {
        return nicknames;
    }

    public State getState() {
        return state;
    }
}
