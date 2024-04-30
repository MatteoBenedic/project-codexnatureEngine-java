package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.Model.Logic.PlayerColour;
import it.polimi.ingsw.am12.Model.Logic.State;

import java.util.List;

public class ColourSelectedUpdate implements Update{
    String nickname;
    PlayerColour colour;
    String turn;
    State state;

    public ColourSelectedUpdate(String nickname, PlayerColour colour, String turn, State state) {
        this.nickname = nickname;
        this.colour = colour;
        this.turn = turn;
        this.state = state;
    }

    public String getNickname(){
        return nickname;
    }

    public PlayerColour getPlayerColour(){
        return colour;
    }

    public String getTurn(){
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
           message = "\nYou have set your colour to " + colour + "\n";
        }
        else
        {
            message= "\n"+ nickname+ " has selected the colour: "+colour;
        }

        if(turn.equals(receiver)) {
            message += "\nIt's your turn";
        }
        else {
            message += "\nIt's " + turn + "'s turn. Please wait.";
        }

        return message;
    }

}
