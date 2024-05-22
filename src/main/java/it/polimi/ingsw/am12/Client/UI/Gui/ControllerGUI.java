package it.polimi.ingsw.am12.Client.UI.Gui;

import it.polimi.ingsw.am12.Client.ClientController.ClientController;

/**
 * This abstract class defines a controller used to handle user actions from the GUI,
 * by sending the correct message to the server
 */
public abstract class ControllerGUI {

    protected ClientController clientController;

    /**
     * Set the ClientController that will send the message to the server
     * @param clientController the ClientController
     */
    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

}
