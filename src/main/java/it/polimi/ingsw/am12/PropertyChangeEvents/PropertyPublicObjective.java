package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.GUI;

/**
 * There's a new public objective
 */
public class PropertyPublicObjective implements PropertyChange{

    int index;

    /**
     * Class constructor
     * @param index the index of the new public objective
     */
    public PropertyPublicObjective(int index) {
        this.index = index;
    }

    /**
     * Update the CLI with the new public objective
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        System.out.println("There is a new public objective: "+ index);
    }

    /**
     * Update the GUI with the new public objective
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
