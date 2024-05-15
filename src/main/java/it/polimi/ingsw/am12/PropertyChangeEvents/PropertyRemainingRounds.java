package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.GUI;

/**
 * A number of remaining rounds is set
 */
public class PropertyRemainingRounds implements PropertyChange {

    int remainingRounds;

    /**
     * Class constructor
     * @param remainingRounds the number of remaining rounds
     */
    public PropertyRemainingRounds(int remainingRounds) {
        this.remainingRounds = remainingRounds;
    }

    /**
     * Update the CLI with the number of remaining rounds
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        System.out.println("Remaining rounds: "+ remainingRounds);
    }

    /**
     * Update the GUI with the number of remaining rounds
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
