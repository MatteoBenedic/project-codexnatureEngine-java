package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;

import java.io.IOException;

/**
 * The purpose of this interface is to notify the user interface that a change happened
 * in the viewModel. Its implementations specify which property has changed.
 */
public interface PropertyChange {

    int WINDOW_WIDTH = 1200;
    int WINDOW_HEIGHT = 700;

    /**
     * Update the CLI with the changes in the viewModel
     * @param cli the CLI
     */
    void updateCLI(CLI cli);

    /**
     * Update the GUI with the changes in the viewModel
     * @param gui the GUI
     */
    void updateGUI(GUI gui) throws IOException;
}
