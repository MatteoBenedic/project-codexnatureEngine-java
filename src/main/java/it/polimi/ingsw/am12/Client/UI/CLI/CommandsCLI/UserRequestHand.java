package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.CLI.CLIDrawBufferHand;

/**
 * Show tha player's hand
 */
public class UserRequestHand implements UserRequest{

    String nickname;

    /**
     * Show the player's hand
     * @param cli the cli;
     */
    @Override
    public void showRequest(CLI cli){
        CLIDrawBufferHand hand = cli.getHand();
        System.out.println("Your hand:");
        hand.printBuffer();
    }

    /**
     * Set the nickname
     * @param nickname the nickname of the user who requested the action
     */
    @Override
   public void setNickname(String nickname){}

}
