package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.Gui.ControllerCreateOrJoinMatch;
import it.polimi.ingsw.am12.Gui.ControllerNickname;
import it.polimi.ingsw.am12.Gui.GUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

/**
 * Some players joined the lobby
 */
public class PropertyPlayersAdded implements PropertyChange{

    List<String> nicknames;

    /**
     * Class constructor
     * @param nicknames the list of the nicknames of the players who joined the lobby
     */
    public PropertyPlayersAdded(List<String> nicknames) {
        this.nicknames = nicknames;
    }

    /**
     * Update the CLI with the nicknames of the players who joined the lobby
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        System.out.println("The following players joined the lobby:");
        for(String nickname : nicknames) {
            System.out.println(" "+nickname);
        }
    }

    /**
     * Update the CLI with the nicknames of the players who joined the lobby
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) throws IOException {
        Stage stage = gui.getStage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Lobby.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        ControllerCreateOrJoinMatch cn = fxmlLoader.getController();
        cn.setClientController(gui.getController());
        stage.setScene(scene);
        stage.show();
    }
}
