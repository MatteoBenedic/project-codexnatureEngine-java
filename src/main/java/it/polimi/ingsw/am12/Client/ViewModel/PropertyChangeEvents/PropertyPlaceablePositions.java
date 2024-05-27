package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import it.polimi.ingsw.am12.Utils.Coordinate;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

/**
 * The player hase received a list of available positions for placing
 */
public class PropertyPlaceablePositions implements PropertyChange{

    List<Coordinate> availablePositions;

    /**
     * Class constructor
     * @param availablePositions a list with the coordinates of the available positions for placing
     */
    public PropertyPlaceablePositions(List<Coordinate> availablePositions) {
        this.availablePositions = availablePositions;
    }

    /**
     * Update the CLI with the list of available positions
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        String s = "Available positions:";
        for(Coordinate c : availablePositions) {
            s+= " (" + c.getX() + ", " + c.getY() + ")";
        }
        System.out.println(s);
    }

    /**
     * Update the GUI with the list of available positions
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {
        Stage stage = gui.getStage();
        Scene scene = stage.getScene();
        GridPane grid = (GridPane) scene.lookup("#"+gui.getNickname());
        for(Coordinate c: availablePositions){
            Pane cell = new Pane();
            cell.setStyle("-fx-background-color: red;");
            cell.setMinSize(50, 50);
            cell.setMaxSize(50, 50);
            grid.add(cell,c.getX(),c.getY());
            cell.setOnMouseClicked(event -> {
                gui.setSelectedCoordinates(c.getX(), c.getY());
                    }
            );

    }
}
}
