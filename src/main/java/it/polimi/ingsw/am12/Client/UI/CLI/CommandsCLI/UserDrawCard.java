package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

import it.polimi.ingsw.am12.Network.Messages.Events.DrawCardEvent;
import it.polimi.ingsw.am12.Message;

/**
 * The user wants to draw a card
 */
public class UserDrawCard implements UserAction{

    private String nickname;
    private static final int MIN_DECK_INDEX = 0;
    private static final int MAX_DECK_INDEX= 5;

    /**
     * Create a message to draw a card
     * @param params the string command
     * @return a DrawCardEvent
     */
    @Override
    public Message createMessage(String params) {
        //String[] parameters = params.split(" ");
        String[] parameters = params.split("\\s+");
        int deckIndex;
        try {
            deckIndex = Integer.parseInt(parameters[0]);
            if(!isAValidDeckIndex(deckIndex)){
                System.out.println("Invalid deck index! Syntax: drawcard [deck index] where the deck index must be between 0 and 5");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid deck index! Syntax: drawcard [deck index] where the deck index must be between 0 and 5");
            return null;
        }
        commandParametersSent(deckIndex);
        return new DrawCardEvent(nickname, deckIndex);

    }

    /**
     * Check if the deckIndex in the params is valid
     * @param deckIndex the deckIndex in the params
     * @return true if the deckIndex is valid, otherwise false
     */
    private boolean isAValidDeckIndex(int deckIndex) {
        if(deckIndex >= MIN_DECK_INDEX && deckIndex <= MAX_DECK_INDEX){
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
     * @param deckIndex the index of the deck from which the user wants to draw
     */
    public void commandParametersSent(int deckIndex) {
        System.out.println("Asked to draw a card from deck " + deckIndex);
    }
}
