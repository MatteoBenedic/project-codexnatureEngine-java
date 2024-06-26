package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The user receives a message in the chat
 */
public class PropertyChatMessage implements PropertyChange {

    private final String sender;
    private final boolean isPublic;
    private final boolean isYourMessage;
    private final String recipient;
    private final String message;
    private final static int MAX_NUM_MESSAGES = 10;

    /**
     * class constructor
     * @param sender the nickname of the user who sent the message
     * @param isPublic a boolean:
     *                 TRUE if the message is public
     *                 FALSE if the message is private
     * @param isYourMessage a boolean:
     *                 TRUE if the user is also the sender of the message
     *                 FALSE if the message was sent by some other user
     * @param recipient the nickname of the recipient
     * @param message the content of the message
     */
    public PropertyChatMessage(String sender, boolean isPublic, boolean isYourMessage, String recipient, String message) {
        this.sender = sender;
        this.isPublic = isPublic;
        this.isYourMessage = isYourMessage;
        this.recipient = recipient;
        this.message = message;
    }

    /**
     * Update the CLI with the received message
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        String s = composeMessage();
        System.out.println(s);
    }

    /**
     * Update the GUI with the received message
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

        Stage stage = gui.getStage();
        Scene scene = stage.getScene();
        VBox messages = (VBox) scene.lookup("#messages");

        String s = composeMessage();

        if(messages.getChildren().size() == MAX_NUM_MESSAGES) {
            messages.getChildren().removeFirst();
        }
        messages.getChildren().add(new Text(s));

    }

    /**
     * Compose the string to print the message
     * @return the string to be printed
     */
    private String composeMessage() {
        String s ="[";
        if(isYourMessage) {
            s+="You";
            if(!isPublic) {
                s+=" to "+recipient;
            }
        }
        else {
            s+=sender;
        }

        if(isPublic) {
            s+=" to Everyone";
        }
        s+="]: " + message;

        return s;
    }
}
