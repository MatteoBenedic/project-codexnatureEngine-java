package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.Gui.GUI;

import java.util.List;

/**
 * All the players have been added to the match
 */
public class PropertyPlayersInMatch implements PropertyChange {

    List<String> nicknames;

    /**
     * Class constructor
     * @param nicknames the list of the players in the match
     */
    public PropertyPlayersInMatch(List<String> nicknames) {
        this.nicknames = nicknames;
    }

    /**
     * Update the CLI with the new card on the playing grid
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        for(String nickname : nicknames)
            cli.addPlayer(nickname);
    }

    /**
     * Update the GUI with the new card on the playing grid
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}

