package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import it.polimi.ingsw.am12.Utils.Assets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * There's a new public objective
 */
public class PropertyPublicObjective implements PropertyChange{

    private final int index;

    /**
     * Class constructor
     * @param index the index of the new public objective
     */
    public PropertyPublicObjective(int index) {
        this.index = index;
    }

    /**
     * Update the CLI with the new public objective
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        System.out.println("Public objective ");
        cli.printObjectiveCard(index);
        System.out.println(" ");
    }

    /**
     * Update the GUI with the new public objective
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {
        Stage stage = gui.getStage();
        Scene scene = stage.getScene();

        GridPane drawtable = (GridPane) scene.lookup("#drawTable");
        int row = 2;
        int column = 0;

        Assets a = new Assets();
        String fileName = a.getFileName(index, true);
        Image img = new Image(fileName);
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(180);
        imageView.setFitHeight(100);
        if(drawtable.getChildren().size()==7) {
            column = 1;
        }
        drawtable.add(imageView, column, row);
    }
}
