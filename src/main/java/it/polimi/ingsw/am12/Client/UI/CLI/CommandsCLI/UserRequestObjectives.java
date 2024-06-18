package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.CLI.CLIObjectivesBuffer;

/**
 * Show the player's objectives
 */
public class UserRequestObjectives implements UserRequest{

    /**
     * Prints in the command line the player's objectives
     * @param cli the cli;
     */
    @Override
    public void showRequest(CLI cli) {
        CLIObjectivesBuffer cliObjectives = cli.getObjectives();
        cliObjectives.printBuffer();
    }

    /**
     * Set the nickname
     * @param nickname the nickname of the user who requested the action
     */
    @Override
    public void setNickname(String nickname){}

    /**
     * Method that defines, if there is one, the integer parameter of the object
     * @param value the integer parameter to set
     */
    @Override
    public void setPossibleParameter(int value) {
    }
}
