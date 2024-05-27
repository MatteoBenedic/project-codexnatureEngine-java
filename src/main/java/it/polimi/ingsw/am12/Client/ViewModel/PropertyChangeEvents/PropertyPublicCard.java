package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.CLI.CLIDrawBufferTable;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import it.polimi.ingsw.am12.Utils.Assets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * There is a new public card on the table
 */
public class PropertyPublicCard implements PropertyChange{

    int deckIndex;
    int cardIndex;

    /**
     * Class constructor
     * @param deckIndex an int that specifies which public card ha changed
     *                    0 --> first gold
     *                    1 --> second gold
     *                    2 --> first resource
     *                    3 --> second resource
     * @param cardIndex the index of the new public card
     */
    public PropertyPublicCard(int deckIndex, int cardIndex) {
        this.deckIndex = deckIndex;
        this.cardIndex = cardIndex;
    }

    /**
     * Update the CLI with the new public card
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        CLIDrawBufferTable drawtable = cli.getDrawtable();
        drawtable.insertCardInBuffer(cardIndex, deckIndex);
    }

    /**
     * Update the GUI with the new public card
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) throws IOException {
        Stage stage = gui.getStage();
        Scene scene = stage.getScene();

        GridPane drawtable = (GridPane) scene.lookup("#drawTable");
        int row = (deckIndex < 2) ? 0: 1;
        int column = deckIndex % 2;

        Assets a = new Assets();
        String fileName = a.getFileName(cardIndex, true);
        Image img = new Image(fileName);
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(180);
        imageView.setFitHeight(100);
        drawtable.add(imageView, column, row);

    }
}
