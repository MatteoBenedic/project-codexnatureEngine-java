package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.CLI.CLIDrawBufferGrid;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import it.polimi.ingsw.am12.Network.Messages.Events.GetPlaceablePositionsEvent;
import it.polimi.ingsw.am12.Utils.Assets;
import it.polimi.ingsw.am12.Utils.Coordinate;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static java.lang.Math.abs;

/**
 * A new card has been placed on a playing grid
 */
public class PropertyCardPlaced implements PropertyChange {

    String nickname;
    boolean isYourPlayingGrid;
    boolean isStartCard;
    int cardIndex;
    boolean side;
    Coordinate position;
    private final static int CENTRE_GRID_ROW = 40;
    private final static int CENTRE_GRID_COL = 40;
    private final static int CELL_DIM_ROW = 30;
    private final static int CELL_DIM_COL = 40;
    private final static int MUL_ROW = 3;
    private final static int MUL_COL = 4;

    /**
     * Class constructor
     * @param nickname the player who placed a card
     * @param isYourPlayingGrid a boolean:
     *                          TRUE if the card was placed by the player to whom the update is displayed
     *                          FALSE if the card was placed by another player
     * @param isStartCard a boolean:
     *                    TRUE if the card is a start card
     *                    FALSE if the card is not a start card
     * @param cardIndex the index of the placed card
     * @param side the side on which it was placed (TRUE = front, FALSE = back)
     * @param position the position on the playing grid
     */
    public PropertyCardPlaced(String nickname, boolean isYourPlayingGrid, boolean isStartCard, int cardIndex, boolean side, Coordinate position) {
        this.nickname = nickname;
        this.isYourPlayingGrid = isYourPlayingGrid;
        this.isStartCard = isStartCard;
        this.cardIndex = cardIndex;
        this.side = side;
        this.position = position;
    }

    /**
     * Update the CLI with the new card on the playing grid
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        CLIDrawBufferGrid grid = cli.getPlayingGrids().get(nickname);
        grid.insertCardInBuffer(cardIndex, side, position);
        if(isYourPlayingGrid)
            System.out.println("Your new playing grid:");
        else
            System.out.println(nickname + "'s new playing grid:");
        grid.printBuffer();
    }

    /**
     * Update the GUI with the new card on the playing grid
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {
        Stage stage = gui.getStage();
        Scene scene = stage.getScene();

        GridPane grid = (GridPane) scene.lookup("#" + nickname);
        int row = position.getX()*MUL_ROW-(position.getX()-CENTRE_GRID_ROW);
        int column = position.getY()*MUL_COL-(position.getY()-CENTRE_GRID_COL);

        Assets a = new Assets();
        String fileName = a.getFileName(cardIndex, side);
        Image img = new Image(fileName);
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(CELL_DIM_COL*MUL_COL);
        imageView.setFitHeight(CELL_DIM_ROW*MUL_ROW);
        Pane cell = new Pane();
        cell.setMinSize(CELL_DIM_COL, CELL_DIM_ROW);
        cell.setMaxSize(CELL_DIM_COL, CELL_DIM_ROW);
        cell.getChildren().add(imageView);
        grid.add(cell, column, row);
        imageView.setOnMouseClicked(event ->
                gui.getController().sendMessage(
                        new GetPlaceablePositionsEvent(
                                gui.getNickname(),
                                position.getX(),
                                position.getY())));

        if(isStartCard && isYourPlayingGrid) {
            HBox hand = (HBox) scene.lookup("#hand");
            hand.getChildren().clear();
        }

        if(isStartCard) {
            grid.setGridLinesVisible(true);
        }
    }
}
