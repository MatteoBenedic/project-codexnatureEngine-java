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

    String sender;
    boolean isPublic;
    boolean isYourMessage;
    String message;
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

        Stage stage = gui.getStage();
        Scene scene = stage.getScene();
        VBox messages = (VBox) scene.lookup("#messages");

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

        if(messages.getChildren().size() == MAX_NUM_MESSAGES) {
            messages.getChildren().removeFirst();
        }
        messages.getChildren().add(new Text(s));

    }
}
