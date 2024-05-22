package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.CLI.CLIDrawBufferTable;

/**
 * Show the draw table after user request
 */
public class UserRequestDrawTable implements UserRequest{

    /**
     * Show the draw table
     * @param cli the cli;
     */
    @Override
    public void showRequest(CLI cli){
        CLIDrawBufferTable drawtable = cli.getDrawtable();
        System.out.println("Draw table: ");
        drawtable.printBuffer();
    }

    /**
     * Set the nickname
     * @param nickname the nickname of the user who requested the action
     */
    @Override
    public void setNickname(String nickname) {}
}
