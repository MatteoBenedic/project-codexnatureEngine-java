package it.polimi.ingsw.am12.CommandsCLI;

import it.polimi.ingsw.am12.Controller.Events.DistributeCardsEvent;
import it.polimi.ingsw.am12.Message;

/**
 * The user wants to distribute the cards in order to start the match
 */
public class UserDistributeCards implements UserAction{

    /**
     * Create Message to distribute cards
     * @param params the string command
     * @return a DistributeCardsEvent
     */
    @Override
    public Message createMessage(String params) {
        distributeCardsEventSent();
        return new DistributeCardsEvent();
    }

    /**
     * Set the nickname
     * @param nickname the nickname of the user who requested the action
     */
    @Override
    public void setNickname(String nickname) {}

    /**
     * Print a summary of the requested action
     */
    private void distributeCardsEventSent() {
        System.out.println("Asked to distribute cards");
    }
}

