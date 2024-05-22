package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;

/**
 * The turn changed and it's another player's turn
 */
public class PropertyTurnChange implements PropertyChange{

    String nickname;
    State newState;

    /**
     * Class constructor
     * @param nickname the nickname of the player whose turn is now
     * @param newState the new state of the game
     */
    public PropertyTurnChange(String nickname, State newState) {
        this.nickname = nickname;
        this.newState = newState;
    }

    /**
     * Update the CLI with the new turn
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        if(!newState.equals(State.DISTRIBUTION))
            System.out.println("It's "+ nickname + "'s turn");
    }

    /**
     * Update the GUI with the new turn
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
