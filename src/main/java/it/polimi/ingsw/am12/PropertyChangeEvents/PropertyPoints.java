package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.GUI;

/**
 * A player has incremented his score
 */
public class PropertyPoints implements PropertyChange{

    String nickname;
    int points;

    /**
     * Class constructor
     * @param nickname the player who has incremented his score
     * @param points the number of gained points
     */
    public PropertyPoints(String nickname, int points) {
        this.nickname = nickname;
        this.points = points;
    }

    /**
     * Update the CLI with the new score
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
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
