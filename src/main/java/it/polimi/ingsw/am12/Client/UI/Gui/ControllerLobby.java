package it.polimi.ingsw.am12.Client.UI.Gui;

import it.polimi.ingsw.am12.Network.Messages.Events.StartMatchEvent;
import javafx.fxml.FXML;

/**
 * GUI controller to handle users in the lobby
 */
public class ControllerLobby extends ControllerGUI{

    /**
     * When a user clicks on "Start match", send a StartMatchEvent
     */
    @FXML
    public void onStartMatchClick(){
        clientController.sendMessage(new StartMatchEvent());
    }
}
