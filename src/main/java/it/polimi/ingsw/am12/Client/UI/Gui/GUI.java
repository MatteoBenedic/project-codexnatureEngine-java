package it.polimi.ingsw.am12.Client.UI.Gui;

import it.polimi.ingsw.am12.Client.ClientController.ClientController;
import it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents.PropertyChange;
import it.polimi.ingsw.am12.Client.UI.UserInterface;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Graphical User Interface
 */
public class GUI implements UserInterface {

    Stage stage;
    ClientController controller;

    /**
     * Class constructor
     * @param stage the JavaFx stage
     * @param controller the Client controller
     */
    public GUI(ClientController controller, Stage stage) {
        this.controller = controller;
        controller.addViewModelListener(this);
        this.stage = stage;
    }

    /**
     * Get the JavaFx stage
     * @return the JavaFx stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Get the client controller
     * @return the client controller
     */
    public ClientController getController() {
        return controller;
    }

    /**
     * Get this instance of GUI
     * @return this instance of GUI
     */
    private GUI thisGUI() {
        return this;
    }

    /**
     * Notify the GUI that a property in the ViewModel changed
     * @param p an instance of PropertyChange that describes with property has changed
     */
    @Override
    public void propertyChange(PropertyChange p) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    p.updateGUI(thisGUI());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
