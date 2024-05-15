package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.PropertyChangeEvents.PropertyChange;
import java.util.List;

/**
 * Graphical User Interface
 */
public class GUI implements UserInterface{

    /**
     * Notify the GUI that a property in the ViewModel changed
     * @param p an instance of PropertyChange that describes with property has changed
     */
    @Override
    public void propertyChange(PropertyChange p) {
        p.updateGUI(this);
    }

    /**
     * Show the current playing grid of the player with all cards already placed
     * @param playingGrid the playing grid of the player
     */
    public void printPlayingGrid(List<ClientCard> playingGrid){};
}
