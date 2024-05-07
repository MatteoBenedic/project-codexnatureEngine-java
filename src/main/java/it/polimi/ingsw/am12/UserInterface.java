package it.polimi.ingsw.am12;

/**
 * Interface implemented by the GUI and CLI. It's used by the memory to notify the user interface chosen by the client of
 * the new state of the match or the lobby
 */
public interface UserInterface {

    public void updateInterface();
}
