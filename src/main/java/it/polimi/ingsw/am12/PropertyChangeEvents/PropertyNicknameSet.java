package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.GUI;

/**
 * The nickname has been set
 */
public class PropertyNicknameSet implements PropertyChange {

    String nickname;

    /**
     * Class constructor
     * @param nickname the nickname tha has been set
     */
    public PropertyNicknameSet(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Update the CLI with the nickname
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        System.out.println("You've set your nickname: " + nickname);
        cli.setNickname(nickname);
    }

    /**
     * Update the GUI with the nickname
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
