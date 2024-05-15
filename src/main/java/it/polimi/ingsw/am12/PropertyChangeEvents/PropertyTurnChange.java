package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.GUI;

/**
 * The turn changed and it's another player's turn
 */
public class PropertyTurnChange implements PropertyChange{

    String nickname;

    /**
     * Class constructor
     * @param nickname the nickname of the player whose turn is now
     */
    public PropertyTurnChange(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Update the CLI with the new turn
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
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
