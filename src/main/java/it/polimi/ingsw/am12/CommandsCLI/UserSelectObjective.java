package it.polimi.ingsw.am12.CommandsCLI;

import it.polimi.ingsw.am12.Controller.Events.SelectObjectiveEvent;
import it.polimi.ingsw.am12.Message;

/**
 * A user selects his secret objective
 */
public class UserSelectObjective implements UserAction{

    String nickname;
    private static final String FIRST_OBJ = "first";
    private static final String SECOND_OBJ = "second";

    /**
     * Create Message to select a secret objective
     * @param params the string command
     * @return a SelectObjectiveEvent
     */
    @Override
    public Message createMessage(String params) {
        //String[] parameters = params.split(" ");
        String[] parameters = params.split("\\s+");
        Boolean chosenObjective = null;
        if(parameters[0].equals(FIRST_OBJ)){
            chosenObjective = true;
        } else if (parameters[0].equals(SECOND_OBJ)) {
            chosenObjective = false;
        }

        if(chosenObjective != null) {
            commandParametersSent(parameters[0]);
            return new SelectObjectiveEvent(nickname, chosenObjective);
        }
        else{
            System.out.println("Invalid parameter! Syntax: selectobjective first || selectobjective second");
            return null;
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
     * @param chosenObjective a string the indicates if the user selected the first
     *                        or the second objective
     */
    private static void commandParametersSent(String chosenObjective) {
        System.out.println("Selected the " + chosenObjective + " objective");
    }
}
