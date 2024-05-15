package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.GUI;

/**
 * The secret objective is selected
 */
public class PropertySecretObjective implements PropertyChange{

    int index;

    /**
     * Class constructor
     * @param index the index of the secret objective
     */
    public PropertySecretObjective(int index) {
        this.index = index;
    }

    /**
     * Update the CLI with the new secret objective
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        System.out.println("Your secret objective is: " + index);
    }

    /**
     * Update the GUI with the new secret objective
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
