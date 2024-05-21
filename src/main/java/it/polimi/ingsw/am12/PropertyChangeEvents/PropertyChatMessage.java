package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.GUI;

/**
 * The user receives a message in the chat
 */
public class PropertyChatMessage implements PropertyChange {

    String sender;
    boolean isPublic;
    boolean isYourMessage;
    String message;

    /**
     * class constructor
     * @param sender the nickname of the user who sent the message
     * @param isPublic a boolean:
     *                 TRUE if the message is public
     *                 FALSE if the message is private
     * @param isYourMessage a boolean:
     *                 TRUE if the user is also the sender of the message
     *                 FALSE if the message was sent by some other user
     * @param message the content of the message
     */
    public PropertyChatMessage(String sender, boolean isPublic, boolean isYourMessage, String message) {
        this.sender = sender;
        this.isPublic = isPublic;
        this.isYourMessage = isYourMessage;
        this.message = message;
    }

    /**
     * Update the CLI with the received message
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        String s ="[";
        if(isYourMessage) {
            s+="You";
        }
        else {
            s+=sender;
        }

        if(isPublic) {
            s+=" to Everyone";
        }
        s+="]: " + message;
        System.out.println(s);
    }

    /**
     * Update the GUI with the received message
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
