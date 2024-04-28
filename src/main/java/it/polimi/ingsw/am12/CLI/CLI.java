package it.polimi.ingsw.am12.CLI;

import it.polimi.ingsw.am12.ConnectionType;
import it.polimi.ingsw.am12.CreateMatchMessage;

/**
 * This class elaborates the commands from a CLI and created the corresponding message to send
 * to the server.
 */
public class CLI {

    public CLI() {
    }

    /**
     * Parses the command from the CLI and creates the correct message to send
     * through socket connection
     * @param cmd a String from the CLI
     * @return an Object that is the message to send
     */
    public Object parseCommandSocket(String cmd){
        switch(cmd){
            case "create match":
                return new CreateMatchMessage("player", 2, "match1", ConnectionType.SOCKET);
            case "join match":
                //joinMatch();
                return null;
            default:
                return null;
        }

    }

    /**
     * Creates a message to show to the user on CLI, describing a message received
     * from the server though socket connection
     * @param o the Object received from the server
     */
    public void showUpdateSocket(Object o) {
        if(o instanceof Exception) {
            System.out.println(((Exception) o).getMessage());
        }
        if(o instanceof String) {
            System.out.println(o);
        }
    }
}
