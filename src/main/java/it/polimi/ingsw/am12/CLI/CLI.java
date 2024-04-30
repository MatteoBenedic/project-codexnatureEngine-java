package it.polimi.ingsw.am12.CLI;

import it.polimi.ingsw.am12.ConnectionType;
import it.polimi.ingsw.am12.Controller.Events.DistributeCardsEvent;
import it.polimi.ingsw.am12.Controller.Events.PlaceStartCardEvent;
import it.polimi.ingsw.am12.Controller.Events.SelectColourEvent;
import it.polimi.ingsw.am12.Controller.Events.StartMatchEvent;
import it.polimi.ingsw.am12.CreateMatchMessage;
import it.polimi.ingsw.am12.JoinMatchMessage;
import it.polimi.ingsw.am12.Model.Logic.PlayerColour;
import it.polimi.ingsw.am12.View.Updates.Update;

import java.util.Objects;

/**
 * This class elaborates the commands from a CLI and created the corresponding message to send
 * to the server.
 */
public class CLI {

    String nickname;

    public CLI(String nickname) {
        this.nickname = nickname;
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
                return new CreateMatchMessage(nickname, 2, "match1", ConnectionType.SOCKET);
            case "join match":
                return new JoinMatchMessage(nickname, "match1", ConnectionType.SOCKET);
            case "start match":
                return new StartMatchEvent();
            case "place start card":
                return new PlaceStartCardEvent(nickname, true);
            case "select colour red":
                return new SelectColourEvent(nickname, PlayerColour.RED);
            case "select colour yellow":
                return new SelectColourEvent(nickname, PlayerColour.YELLOW);
            case "distribute cards":
                return new DistributeCardsEvent();
            default:
                return null;
        }

    }

    /**
     * Creates a message to show to the user on CLI, describing a message received
     * from the server though socket connection
     * @param o the Object received from the server
     */
    public boolean showUpdateSocket(Object o, String nickname) {
        if(o instanceof Exception) {
            System.out.println(((Exception) o).getMessage());
            return true;
        }
        if(o instanceof String) {
            System.out.println(o);
            return true;
        }
        if(o instanceof Update) {
            System.out.println(((Update) o).toString(nickname));
            return Objects.equals(((Update) o).getTurn(), nickname);
        }
        return true;
    }
}
