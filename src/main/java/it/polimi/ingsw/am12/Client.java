package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.View.Updates.*;
import java.io.Serializable;
import java.rmi.Remote;

/**
 * The client of a player
 */
public class Client implements Remote, Serializable {

    public void sendMessage(Update update){
        // update.executeViewUpdate in override
    }
}
