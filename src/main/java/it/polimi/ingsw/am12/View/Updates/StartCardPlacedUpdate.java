package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.Controller.Events.Event;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.View.VirtualView;
import java.util.List;

public class StartCardPlacedUpdate implements Update {

    String nickname;
    int startCard;
    boolean side;
    String turn;
    State state;

    public StartCardPlacedUpdate(String nickname, int startCard, boolean side, String turn, State state) {
        this.nickname = nickname;
        this.startCard = startCard;
        this.side = side;
        this.turn = turn;
        this.state = state;
    }

    public String getNickname() {
        return nickname;
    }

    public int getStartCard() {
        return startCard;
    }

    public boolean getSide() {
        return side;
    }

    public String getTurn() {
        return turn;
    }

    public State getState() {
        return state;
    }

    public String toString(String receiver) {
        if(receiver==null)
            return "";

        String message;
        if(receiver.equals(nickname)) {
            message = "\nYou have placed the Start Card " + startCard;
        }
        else
        {
            message= "\n" + nickname+ " has placed the Start Card " + startCard;
        }
        message += " on its ";
                if(side) {
                    message += "front";
                }
                else {
                    message += "back";
                }

        if(turn.equals(receiver)) {
            message += "\nIt's your turn now";
        }
        else {
            message += "\nIt's " + turn + "'s turn. Please wait.";
        }

        return message;
    }

}
