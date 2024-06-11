package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * A number of remaining rounds is set
 */
public class PropertyRemainingRounds implements PropertyChange {

    private final int remainingRounds;

    /**
     * Class constructor
     * @param remainingRounds the number of remaining rounds
     */
    public PropertyRemainingRounds(int remainingRounds) {
        this.remainingRounds = remainingRounds;
    }

    /**
     * Update the CLI with the number of remaining rounds
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        System.out.println("Remaining rounds: "+ remainingRounds);
    }

    /**
     * Update the GUI with the number of remaining rounds
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {
        Stage stage = gui.getStage();
        Scene scene = stage.getScene();
        Text remainingRoundsText= (Text) scene.lookup("#remainingRounds");
        remainingRoundsText.setText("Remainig rounds: " + remainingRounds);
    }
}
