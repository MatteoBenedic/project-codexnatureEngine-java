package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

import it.polimi.ingsw.am12.Network.Messages.Events.PlaceCardEvent;
import it.polimi.ingsw.am12.Message;

/**
 * A user wants to place a card
 */
public class UserPlaceCard implements UserAction{

    private String nickname;
    private static final int MAX_NUMBER_OF_ROWS = 81;
    private static final int MAX_NUMBER_OF_COL = 81;
    private static final String FRONT_SIDE = "front";
    private static final String BACK_SIDE = "back";
    private static final int MAX_CARDS_IN_HAND = 3;

    /**
     * Create a Message to place a card
     * @param params the string command
     * @return a PlaceCardEvent
     */
    @Override
    public Message createMessage(String params) {
        //String[] parameters = params.split(" ");
        String[] parameters = params.split("\\s+");
        int index, x, y;
        try {
            index = Integer.parseInt(parameters[0]);
            x = Integer.parseInt(parameters[2]);
            y = Integer.parseInt(parameters[3]);
            if(!indexIsACardInHand(index)){
                System.out.println("The specified card index is wrong: Syntax: placecard [index] [side] [x] [y] where the index must be one of a cards in hand");
                return null;
            }
            else if (!checkCoordinatesWithinPlayingGridLimits(x, y)){
                System.out.println("Invalid coordinate value: Syntax: placecard [index] [side] [x] [y] where x and y are between 0 and 81");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid parameter: Syntax: placecard [index] [side] [x] [y] where index must be of a card in hand and x and y are between 0 and 81");
            return null;
        }

        Boolean side;
        if(parameters[1].equals(FRONT_SIDE)){
            side = true;
        } else if (parameters[1].equals(BACK_SIDE)) {
            side = false;
        } else {
            System.out.println("Invalid side specified: Syntax: placecard [index] [side] [x] [y] where side is either front or back");
            return null;
        }

        commandParametersSent(index, parameters[1], x, y);
        return new PlaceCardEvent(nickname, index, side, x, y);
    }

    /**
     * Check if the index in the string command corresponds to one of the cards in hand
     * @param index an int that indicated which card has to be placed (0 = first card in hand, 1 = second card in hand...)
     * @return true if the index corresponds to one cards in hand
     */
    private boolean indexIsACardInHand(int index) {
        return (index>=0 && index < MAX_CARDS_IN_HAND);
    }

    /**
     * Check that the coordinates where the user wants to place are valid coordinates on the playing grid
     * @param x the row of the position where to place the card
     * @param y the column of position where to place the card
     * @return true if the coordinates are valid (inside the playing grid), otherwise false
     */
    private boolean checkCoordinatesWithinPlayingGridLimits(int x, int y) {
        if(x >= 0 && x < MAX_NUMBER_OF_COL && y>=0 && y < MAX_NUMBER_OF_ROWS){
            return true;
        }
        else {
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
     * @param index an int that indicated which card has to be placed (0 = first card in hand, 1 = second card in hand...)
     * @param side a boolean that indicated the side on which the card has to be placed (TRUE = front, FALSE = back)
     * @param x the row of the position where to place the card
     * @param y the column of position where to place the card
     */
    public void commandParametersSent(int index, String side, int x, int y) {
        System.out.println("Asked to place card " + index + " on its " + side +" in x = " + x + " y = " +y);
    }
}
