package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * The error message changed
 */
public class PropertyErrorMessage implements PropertyChange{

    private final String message;

    /**
     * Class costructor
     * @param message the new error message
     */
    public PropertyErrorMessage(String message) {
        this.message = message;
    }

    /**
     * Update the CLI with the new error message
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        System.out.println(message);
        cli.enableCommand();
    }

    /**
     * Update the GUI with the new error message
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {
        Stage stage = gui.getStage();
        Scene scene = stage.getScene();

        Text errorMessage = (Text) scene.lookup("#errorMessage");
        errorMessage.setText(message);

        Button closeErrorMessage = (Button) scene.lookup("#closeErrorMessage");
        closeErrorMessage.setVisible(true);
    }
}
