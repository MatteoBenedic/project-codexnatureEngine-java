package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.GUI;

/**
 * The error message changed
 */
public class PropertyErrorMessage implements PropertyChange{

    String message;

    /**
     * Class costructor
     * @param message the new error message
     */
    public PropertyErrorMessage(String message) {
        this.message = message;
    }

    /**
     * Update the CLI with the new error message
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        System.out.println(message);
        cli.enableCommand();
    }

    /**
     * Update the GUI with the new error message
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
