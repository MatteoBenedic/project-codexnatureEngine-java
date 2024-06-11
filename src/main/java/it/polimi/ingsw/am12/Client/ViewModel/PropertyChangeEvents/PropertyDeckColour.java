package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.CLI.CLIDrawBufferTable;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;
import it.polimi.ingsw.am12.Network.Messages.Events.DrawCardEvent;
import it.polimi.ingsw.am12.Utils.Assets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * There is a new colour of the gold deck or the resource deck
 */
public class PropertyDeckColour implements PropertyChange{

    private final CardColour colour;
    private final int deckIndex;
    private final boolean print;

    /**
     * Class constructor
     * @param colour the new deck colour
     * @param deckIndex an int:
     *                   4 if the new colour refers to the gold deck;
     *                   5 if the new colour refers to the resource deck;
     * @param print a boolean: TRUE is the draw table has to be printed, otherwise FALSE
     */
    public PropertyDeckColour(CardColour colour, int deckIndex, boolean print) {
        this.colour = colour;
        this.deckIndex = deckIndex;
        this.print = print;
    }

    /**
     * Update the CLI with the new deck colour
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        CLIDrawBufferTable drawtable = cli.getDrawtable();
        drawtable.insertCardInDeck(deckIndex, colour);
        if(print) {
            System.out.println("Draw table:");
            drawtable.printBuffer();
        }
    }

    /**
     * Update the GUI with the new deck colour
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {
        Stage stage = gui.getStage();
        Scene scene = stage.getScene();

        GridPane drawtable = (GridPane) scene.lookup("#drawTable");
        int row = 0;
        int column = 2;

        Assets a = new Assets();
        String fileName = "";
        if(deckIndex == 4) {
            fileName = a.getGoldBack(colour);
        }
        if(deckIndex == 5) {
            fileName = a.getResourceBack(colour);
            row = 1;
        }
        Image img = new Image(fileName);
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(180);
        imageView.setFitHeight(100);
        imageView.setOnMouseClicked(event ->
                gui.getController().sendMessage(new DrawCardEvent(gui.getNickname(), deckIndex)));

        drawtable.add(imageView, column, row);
    }
}
