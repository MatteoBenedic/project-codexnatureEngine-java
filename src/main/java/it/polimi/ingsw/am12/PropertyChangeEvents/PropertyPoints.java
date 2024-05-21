package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.Gui.GUI;

/**
 * A player has incremented his score
 */
public class PropertyPoints implements PropertyChange{

    String nickname;
    boolean isYourPoints;
    int points;

    /**
     * Class constructor
     * @param nickname the player who has incremented his score
     * @param isYourPoints a boolean:
     *                     TRUE if the points belong to the player to whom the update is displayed
     *                     FALSE if the points belong to another player
     * @param points the number of gained points
     */
    public PropertyPoints(String nickname, boolean isYourPoints, int points) {
        this.nickname = nickname;
        this.isYourPoints = isYourPoints;
        this.points = points;
    }

    /**
     * Update the CLI with the new score
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        if(isYourPoints)
            System.out.println("You have now " + points + " points");
        else
            System.out.println(nickname + " has now " + points + " points");
    }

    /**
     * Update the GUI with the new score
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
