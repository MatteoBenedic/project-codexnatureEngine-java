package it.polimi.ingsw.am12.View;

import it.polimi.ingsw.am12.View.Updates.Update;


/**
 * Interface that defines an update listener:
 * requires method sendUpdate
 */
public interface UpdateListener {

    /**
     * Listen to an Update and send it to the client
     * @param u the listened Update
     */
    void sendUpdate(Update u);

    /**
     * Get the nickname that identifies this listener
     * @return the nickname
     */
    String getNickname();

}
