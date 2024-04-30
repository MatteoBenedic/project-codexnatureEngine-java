package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.Model.Logic.State;

public class ObjectiveSelectedUpdate implements Update {

    String nickname;
    int secretObjective;
    String turn;
    State state;

    public ObjectiveSelectedUpdate(String nickname, int secretObjective, String turn, State state) {
        this.nickname = nickname;
        this.secretObjective = secretObjective;
        this.turn = turn;
        this.state = state;
    }

    public String getNickname(){
        return nickname;
    }

    public int getSecretObjective(){
        return secretObjective;
    }

    public String getTurn(){
        return turn;
    }

    public State getState() {
        return state;
    }

    public String toString(String receiver) {
        return receiver;
    }
}
