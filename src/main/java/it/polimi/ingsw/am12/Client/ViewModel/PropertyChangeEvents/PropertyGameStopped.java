package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;

import java.io.IOException;

public class PropertyGameStopped implements PropertyChange{

    private static final String GAME_STOPPED_MSG = "The game has been stopped";

    /**
     * Updates the CLI that the game has been stopped
     * Disables the possibility to enter commands in the CLI
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        System.out.println(GAME_STOPPED_MSG);
        cli.disableCommand();
    }

    @Override
    public void updateGUI(GUI gui) throws IOException {
    }
}
