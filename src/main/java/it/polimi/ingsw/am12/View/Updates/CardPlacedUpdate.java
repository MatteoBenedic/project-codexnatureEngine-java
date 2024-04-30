package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.Model.Logic.State;

import java.util.List;

public class CardPlacedUpdate implements Update {

    String nickname;
    int index;
    Boolean side;
    int points;
    String turn;
    State state;

    public CardPlacedUpdate(String nickname, int index, Boolean side, int points, String turn, State state) {
        this.nickname = nickname;
        this.index = index;
        this.side = side;
        this.points = points;
        this.turn = turn;
        this.state = state;
    }

    public String getNickname() {
        return nickname;
    }

    public int getIndex() {
        return index;
    }

    public Boolean getSide() {
        return side;
    }

    public int getPoints() {
        return points;
    }

    public String getTurn() {
        return turn;
    }

    public State getState() {
        return state;
    }

    public String toString(String receiver) {
        return receiver;
    }
}
