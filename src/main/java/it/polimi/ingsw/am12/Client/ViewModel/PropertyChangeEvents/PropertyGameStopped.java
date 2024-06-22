package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PropertyGameStopped implements PropertyChange{

    private static final String GAME_STOPPED_MSG = "The game has been stopped";

    /**
     * Updates the CLI that the game has been stopped
     * Disables the possibility to enter commands in the CLI
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        System.out.println(GAME_STOPPED_MSG);
        cli.disableCommand();
    }

    @Override
    public void updateGUI(GUI gui) throws IOException {
        gui.stopGui();
        Stage stage = gui.getStage();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameStopped.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
}
