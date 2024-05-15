package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.GUI;

/**
 * The turn changed and it's your turn
 */
public class PropertyYourTurn implements PropertyChange{

    /**
     * Update the CLI with the new turn
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        System.out.println("It's your turn");
    }

    /**
     * Update the GUI with the new turn
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
