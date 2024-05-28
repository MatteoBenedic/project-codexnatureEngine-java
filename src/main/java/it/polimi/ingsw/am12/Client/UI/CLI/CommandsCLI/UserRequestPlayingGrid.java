package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.CLI.CLIDrawBufferGrid;

/**
 * Show the player's playing grid
 */
public class UserRequestPlayingGrid implements UserRequest{

    String nickname;
    /**
     * Show the player's playing grid
     * @param cli the cli;
     */
    @Override
    public void showRequest(CLI cli){
        CLIDrawBufferGrid grid = cli.getPlayingGrids().get(nickname);
        System.out.println("Your playing grid: ");
        grid.printBuffer();
    }

    /**
     * Set the nickname
     * @param nickname the nickname of the user who requested the action
     */
    @Override
    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    /**
     * Method that defines, if there is one, the integer parameter of the object
     * @param value the integer parameter to set
     */
    @Override
    public void setPossibleParameter(int value) {
    }

    ;
}
