package it.polimi.ingsw.am12.CommandsCLI;

import it.polimi.ingsw.am12.Controller.Events.PlaceStartCardEvent;
import it.polimi.ingsw.am12.Message;

/**
 * A user wants to place a start card
 */
public class UserPlaceStartCard implements UserAction{

    String nickname;
    private static final String FRONT_SIDE = "front";
    private static final String BACK_SIDE = "back";

    /**
     * Create a Message to place a start card
     * @param params the string command
     * @return a PlaceStartCardEvent
     */
    @Override
    public Message createMessage(String params) {
        //String[] parameters = params.split(" ");
        String[] parameters = params.split("\\s+");
        Boolean side = null;
        if(parameters[0].equals(FRONT_SIDE)){
            side = true;
        } else if (parameters[0].equals(BACK_SIDE)) {
            side = false;
        }

        if(side != null) {
            commandParametersSent(parameters[0]);
            return new PlaceStartCardEvent(nickname, side);
        }
        else{
            System.out.println("Invalid format for parameter! Syntax: placestartcard front || placestartcard back");
            return null;
        }
    }

    /**
     * Set the nickname
     * @param nickname the nickname of the user who requested the action
     */
    @Override
    public void setNickname(String nickname) {
        this.nickname=nickname;
    }

    /**
     * Print a summary of the requested action
     * @param side a boolean that indicated the side on which the start card has to be placed (TRUE = front, FALSE = back)
     */
    private static void commandParametersSent(String side) {
        System.out.println("Asked to place start card on its " + side);
    }
}
