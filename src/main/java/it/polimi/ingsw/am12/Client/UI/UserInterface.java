package it.polimi.ingsw.am12.Client.UI;

import it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents.PropertyChange;

/**
 * Interface implemented by the GUI and CLI. It's used to notify the user interface chosen by the client of
 * the new state of the match or the lobby
 */
public interface UserInterface {

    /**
     * Notify the user interface that a property in the ViewModel changed
     * @param p an instance of PropertyChange that describes with property has changed
     */
    void propertyChange(PropertyChange p);

    /**
     * Set the nickname of the player
     * @param nickname the nickname of the player
     */
    void setNickname(String nickname);
}
