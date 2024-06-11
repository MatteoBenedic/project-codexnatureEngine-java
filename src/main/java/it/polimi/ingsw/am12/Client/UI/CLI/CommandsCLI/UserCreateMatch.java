package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

import it.polimi.ingsw.am12.Network.Messages.CreateMatchMessage;
import it.polimi.ingsw.am12.Message;

/**
 * The user wnats to create a match
 */
public class UserCreateMatch implements UserAction{

    private static final int ALLOWED_NUMPLAYERS_MIN = 2;
    private static final int ALLOWED_NUMPLAYERS_MAX = 4;

    /**
     * Create a Message to create a match
     * @param params the string command
     * @return a CreateMatchMessage
     */
    @Override
    public Message createMessage(String params) {
        //String[] parameters = params.split(" ");
        String[] parameters = params.split("\\s+");

        int numPlayers;
        try {
           numPlayers = Integer.parseInt(parameters[0]);
            if (checkValidNumPlayers(numPlayers)) return null;
        } catch (NumberFormatException e) {
            System.out.println("Invalid format. Syntax: creatematch [number of players] [match name]");
            return null;
        }
        String matchName = parameters[1];
        commandParametersSent(numPlayers, matchName);
        return new CreateMatchMessage(numPlayers, matchName);
    }

    /**
     * Set the nickname
     * @param nickname the nickname of the user who requested the action
     */
    @Override
    public void setNickname(String nickname) {
        
    }

    /**
     * Check that the number of players in the params is valid
     * @param numPlayers the number of players in the params
     * @return true if the number of players is valid, otherwise false
     */
    private static boolean checkValidNumPlayers(int numPlayers) {
        if(numPlayers < ALLOWED_NUMPLAYERS_MIN || numPlayers > ALLOWED_NUMPLAYERS_MAX){
            System.out.println("Invalid number of players! Allowed a minimum of " + ALLOWED_NUMPLAYERS_MIN + " to a maximum of " + ALLOWED_NUMPLAYERS_MAX);
            return true;
        }
        return false;
    }

    /**
     * Print a summary of the requested action
     * @param numPlayers the number of players of the match
     * @param matchName the name of the match
     */
    private static void commandParametersSent(int numPlayers, String matchName) {
        System.out.println("Asked to create a match named " + matchName + " with " + numPlayers + " players");
    }

}
