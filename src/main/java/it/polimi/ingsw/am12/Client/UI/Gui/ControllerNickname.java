package it.polimi.ingsw.am12.Client.UI.Gui;

import it.polimi.ingsw.am12.Network.Messages.NicknameMessage;
import javafx.fxml.*;
import javafx.scene.control.*;

/**
 * GUI controller to handle nickname selection
 */
public class ControllerNickname extends ControllerGUI{
    @FXML
    private TextField username;

    /**
     * When the user confirms the nickname, send a NicknameMessage
     */
    @FXML
    public void onConfirmClick() {
        String nickname = username.getText();
        clientController.sendMessage(new NicknameMessage(nickname));
    }

}
