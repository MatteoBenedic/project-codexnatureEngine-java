package it.polimi.ingsw.am12.Network.Messages.Updates;

import it.polimi.ingsw.am12.Model.Logic.PlayerColour;
import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.ViewModelUpdater;

/**
 * Update to handle when a player chooses the colour at the beginning of the match.
 */
public class ColourSelectedUpdate implements Update{

    String nickname;
    PlayerColour colour;
    String turn;
    State state;

    /**
     * Class constructor
     * @param nickname the nickname of player
     * @param colour   the colour he has chosen
     * @param turn     the nickname of the player whose turn is now
     * @param state    the state of the game (COLOUR or DISTRIBUTION)
     */
    public ColourSelectedUpdate(String nickname, PlayerColour colour, String turn, State state) {
        this.nickname = nickname;
        this.colour = colour;
        this.turn = turn;
        this.state = state;
    }

    /**
     * Update the ViewModel
     * @param viewModelUpdater the ViewModel to update
     */
    @Override
    public void executeUpdate(ViewModelUpdater viewModelUpdater) {
        viewModelUpdater.colourSelectedUpdate(nickname, colour, turn, state);
    }
}
