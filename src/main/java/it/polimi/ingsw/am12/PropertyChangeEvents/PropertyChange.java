package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.GUI;

/**
 * The purpose of this interface is to notify the user interface that a change happened
 * in the viewModel. Its implementations specify which property has changed.
 */
public interface PropertyChange {

    /**
     * Update the CLI with the changes in the viewModel
     * @param cli the CLI
     */
    void updateCLI(CLI cli);

    /**
     * Update the GUI with the changes in the viewModel
     * @param gui the GUI
     */
    void updateGUI(GUI gui);
}
