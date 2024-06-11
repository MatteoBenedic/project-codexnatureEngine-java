package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Map;

/**
 * The list of non completed lobbies has been received
 */
public class PropertyLobbiesNonCompleted implements PropertyChange{

    private final Map<String, Integer> lobbies;

    /**
     * Class constructor
     * @param lobbies the list of non completed lobbies
     */
    public PropertyLobbiesNonCompleted(Map<String, Integer> lobbies) {
        this.lobbies = lobbies;
    }

    /**
     * Update the CLI with the list of non completed lobbies
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        for(String lobby : lobbies.keySet()) {
            System.out.println(lobby +" ("+lobbies.get(lobby)+" spots)");
        }
    }

    /**
     * Update the GUI with the list of non completed lobbies
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {
        Stage stage = gui.getStage();
        Scene scene = stage.getScene();
        VBox content = (VBox) scene.lookup("#content");
        content.getChildren().clear();

        ObservableList<String> lobbiesAsStrings = FXCollections.observableArrayList();
        for(String lobby : lobbies.keySet()) {
            lobbiesAsStrings.add(lobby +" ("+lobbies.get(lobby)+" spots)");
        }

        ListView<String> listView = new ListView<>(lobbiesAsStrings);
        content.getChildren().add(listView);
        stage.setScene(scene);
        stage.show();
    }
}
