package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.Controller.Events.Event;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Model.Logic.PlayerColour;
import it.polimi.ingsw.am12.View.VirtualView;

import java.util.List;

public class ColourSelectedUpdate implements Update{
    String nickname;
    PlayerColour colour;
    String turn;

    public ColourSelectedUpdate(String nickname, PlayerColour colour, String turn) {

        this.nickname = nickname;

        this.colour = colour;

        this.turn = turn;

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
}
