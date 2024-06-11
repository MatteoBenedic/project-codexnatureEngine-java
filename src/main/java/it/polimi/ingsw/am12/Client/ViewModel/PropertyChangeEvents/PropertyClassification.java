package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.ControllerCreateOrJoinMatch;
import it.polimi.ingsw.am12.Client.UI.Gui.ControllerMatch;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import it.polimi.ingsw.am12.Controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * The game has ended and the classification is available
 */
public class PropertyClassification implements PropertyChange {

    private final int numWinners;
    private final LinkedHashMap<String, Integer> classification;

    /**
     * Class constructor
     * @param numWinners the number of winners
     * @param classification the classification of the match, with the final points of each player
     */
    public PropertyClassification(int numWinners, LinkedHashMap<String, Integer> classification) {
        this.numWinners = numWinners;
        this.classification = classification;
    }

    /**
     * Update the CLI with the classification
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
       System.out.println("Winners: ");
       String[] players = classification.sequencedKeySet().toArray(String[]::new);
       for(int i=0; i<numWinners; i++) {
           System.out.println(players[i] + " with " + classification.get(players[i]) + " points");
       }
       System.out.println("Non winners: ");
        for(int i=numWinners; i< players.length; i++) {
            System.out.println(players[i] + " with " + classification.get(players[i]) + " points");
        }
    }

    /**
     * Update the GUI with the classification
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) throws IOException {
        Stage stage = gui.getStage();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Classification.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        ControllerMatch cn = fxmlLoader.getController();
        cn.setClientController(gui.getController());
        VBox stats = (VBox) scene.lookup("#classification");

        String[] players = classification.sequencedKeySet().toArray(String[]::new);
        stats.getChildren().add(new Text("Winners: "));
        for(int i = 0; i<numWinners; i++) {
            stats.getChildren().add(new Text(players[i] + " with " + classification.get(players[i]) + " points"));
        }

        stats.getChildren().add(new Text("Non winners: "));
        for(int i=numWinners; i< players.length; i++) {
            stats.getChildren().add(new Text(players[i] + " with " + classification.get(players[i]) + " points"));
        }
        stage.setScene(scene);
        stage.show();
    }
}
