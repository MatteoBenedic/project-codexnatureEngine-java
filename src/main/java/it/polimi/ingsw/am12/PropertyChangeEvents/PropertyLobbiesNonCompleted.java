package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.GUI;
import java.util.Map;

/**
 * The list of non completed lobbies has been received
 */
public class PropertyLobbiesNonCompleted implements PropertyChange{

    Map<String, Integer> lobbies;

    /**
     * Class constructor
     * @param lobbies the list of non completed lobbies
     */
    public PropertyLobbiesNonCompleted(Map<String, Integer> lobbies) {
        this.lobbies = lobbies;
    }

    /**
     * Update the CLI with the list of non completed lobbies
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        for(String lobby : lobbies.keySet()) {
            System.out.println(lobby +" ("+lobbies.get(lobby)+" spots)");
        }
    }

    /**
     * Update the GUI with the list of non completed lobbies
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
