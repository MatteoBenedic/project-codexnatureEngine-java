package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.MemoryUpdater;
import it.polimi.ingsw.am12.Model.Logic.PlayerColour;
import it.polimi.ingsw.am12.Model.Logic.State;

import java.util.List;

/**
 * Update to handle when a player chooses the colour at the beginning of the match.
 */
public class ColourSelectedUpdate implements Update{

    String nickname;

    PlayerColour colour;

    String turn;

    State state;

    /**
     * Instantiates a new Colour selected update.
     *
     * @param nickname the nickname of player
     * @param colour   the colour he has choosen
     * @param turn     the turn
     * @param state    the state
     */
    public ColourSelectedUpdate(String nickname, PlayerColour colour, String turn, State state) {
        this.nickname = nickname;
        this.colour = colour;
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
     * Get player colour player colour.
     *
     * @return the player colour
     */
    public PlayerColour getPlayerColour(){
        return colour;
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
