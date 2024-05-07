package it.polimi.ingsw.am12;

/**
 * Interface that the memory of the client implements. It's used by the client controller to update the client about the
 * new state of the match or lobby. It contains all the methods to update the memory
 */
public interface MemoryUpdater {


    public void playersAddedToUpdate();

    public void matchStartedToUpdate();

}
