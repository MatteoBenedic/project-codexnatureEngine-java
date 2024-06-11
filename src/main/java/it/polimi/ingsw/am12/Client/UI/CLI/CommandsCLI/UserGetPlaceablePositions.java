package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

import it.polimi.ingsw.am12.Network.Messages.Events.GetPlaceablePositionsEvent;
import it.polimi.ingsw.am12.Message;

/**
 * A user wants to know the placeable positions around a card
 */
public class UserGetPlaceablePositions implements UserAction{

    private String nickname;
    private static final int MAX_NUMBER_OF_ROWS = 81;
    private static final int MAX_NUMBER_OF_COL = 81;

    /**
     * Create a Message to get the placeable positions
     * @param params the string command
     * @return a GetPlaceablePositionsEvent
     */
    @Override
    public Message createMessage(String params) {
        //String[] parameters = params.split(" ");
        String[] parameters = params.split("\\s+");
        int x, y;
        try {
            x = Integer.parseInt(parameters[0]);
            y = Integer.parseInt(parameters[1]);
            if (!checkCoordinatesWithinPlayingGridLimits(x, y)){
                System.out.println("Invalid coordinate value: Syntax: getpos [x] [y] where x and y are between 0 and 81");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid coordinate value: Syntax: getpos [x] [y] where x and y are between 0 and 81");
            return null;
        }
        commandParametersSent(x, y);
        return new GetPlaceablePositionsEvent(nickname, x, y);
    }

    /**
     * Check that the coordinates in the string command are valid for a request of placeable positions
     * @param x the row of the card around which the user wants to know the placeable positions
     * @param y the column of  the card around which the user wants to know the placeable positions
     * @return true if the coordinates are valid (inside the playing grid), otherwise false
     */
    private boolean checkCoordinatesWithinPlayingGridLimits(int x, int y) {
        if(x >= 0 && x < MAX_NUMBER_OF_COL && y>=0 && y < MAX_NUMBER_OF_ROWS){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Set the nickname
     * @param nickname the nickname of the user who requested the action
     */
    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Print a summary of the requested action
     * @param x the row of the card around which the user wants to know the placeable positions
     * @param y the column of the card around which the user wants to know the placeable positions
     */
    public void commandParametersSent(int x, int y) {
        System.out.println("Asked for placeable positions in x = " + x + " y = " +y);
    }
}
