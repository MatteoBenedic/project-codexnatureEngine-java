package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;

/**
 * The secret objective is selected
 */
public class PropertySecretObjective implements PropertyChange{

    String nickname;
    int index;
    boolean isYourObjective;

    /**
     * Class constructor
     * @param nickname the player who selected his objective
     * @param index the index of the secret objective
     * @param isYourObjective a boolean:
     *                        TRUE if the colour belongs to the player to whom the update is displayed
     *                        FALSE if the colour belongs to another player
     */
    public PropertySecretObjective(String nickname, int index, boolean isYourObjective) {
        this.nickname = nickname;
        this.index = index;
        this.isYourObjective = isYourObjective;
    }

    /**
     * Update the CLI with the new secret objective
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        if(isYourObjective) {
            System.out.println("Your secret objective is ");
            cli.printObjectiveCard(index);
        }else
            System.out.println(nickname + " has selected his secret objective");
    }

    /**
     * Update the GUI with the new secret objective
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
