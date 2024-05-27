package it.polimi.ingsw.am12.Client.UI.Gui;

import it.polimi.ingsw.am12.Client.ClientController.ClientController;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.scene.*;

/**
 * This abstract class defines a controller used to handle user actions from the GUI,
 * by sending the correct message to the server
 */
public abstract class ControllerGUI {

    @FXML
    Text errorMessage;
    @FXML
    Button closeErrorMessage;

    protected ClientController clientController;

    /**
     * Set the ClientController that will send the message to the server
     * @param clientController the ClientController
     */
    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    /**
     * Close the error message
     */
    @FXML
    public void closeErrorMessage(){
           errorMessage.setText("");
           closeErrorMessage.setVisible(false);
    }
}
