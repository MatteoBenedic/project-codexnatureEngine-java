package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;


/**
 * The secret objective is selected
 */
public class PropertyObjectivesToChoose implements PropertyChange{

    int[] objectives;

    /**
     * Class constructor
     * @param objectives an array with the indexes of the 2 secret objective. The user has to select one of them.
     */
    public PropertyObjectivesToChoose(int[] objectives) {
        this.objectives = objectives;
    }

    /**
     * Update the CLI with the new secret objective
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        System.out.println("You have to choose one of these secret objectives: " + objectives[0] + " or " + objectives[1]);
    }

    /**
     * Update the GUI with the new secret objective
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
