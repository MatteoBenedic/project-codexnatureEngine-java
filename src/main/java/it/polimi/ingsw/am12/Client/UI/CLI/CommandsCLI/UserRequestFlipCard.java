package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;

/**
 * Show the new hand with a flip card after a user request
 */
public class UserRequestFlipCard implements UserRequest{
    private int value;

    /**
     * Show the new visualization of the hand
     * @param cli the cli;
     */
    @Override
    public void showRequest(CLI cli) {
        cli.getHand().flipCard(value);
        cli.getHand().printBuffer();
    }

    /**
     * Set the nickname
     * @param nickname the nickname of the user who requested the action
     */
    @Override
    public void setNickname(String nickname) {
    }

    /**
     * Method that defines the integer parameter of the object
     * @param value the integer parameter to set
     */
    @Override
    public void setPossibleParameter(int value) {
        this.value = value;
    }
}
