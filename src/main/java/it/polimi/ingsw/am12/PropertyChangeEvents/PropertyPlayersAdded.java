package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.GUI;
import java.util.List;

/**
 * Some players joined the lobby
 */
public class PropertyPlayersAdded implements PropertyChange{

    List<String> nicknames;

    /**
     * Class constructor
     * @param nicknames the list of the nicknames of the players who joined the lobby
     */
    public PropertyPlayersAdded(List<String> nicknames) {
        this.nicknames = nicknames;
    }

    /**
     * Update the CLI with the nicknames of the players who joined the lobby
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        System.out.println("The following players joined the lobby:");
        for(String nickname : nicknames) {
            System.out.println(" "+nickname);
        }
    }

    /**
     * Update the CLI with the nicknames of the players who joined the lobby
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
