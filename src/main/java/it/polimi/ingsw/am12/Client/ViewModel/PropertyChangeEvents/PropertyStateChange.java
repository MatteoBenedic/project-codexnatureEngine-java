package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import it.polimi.ingsw.am12.Model.Logic.PlayerColour;
import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.Network.Messages.Events.GetPlaceablePositionsEvent;
import it.polimi.ingsw.am12.Network.Messages.Events.SelectColourEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.IOException;

/**
 * The state of the game changed
 */
public class PropertyStateChange implements PropertyChange{

    State newState;

    private final static int COLUMNS_SCOREBOARD = 50;
    private final static int CELL_SIZE = 5;
    private final static int ROWS_SCOREBOARD = 100;

    /**
     * Class constructor
     * @param newState the new state of the game
     */
    public PropertyStateChange(State newState) {
        this.newState = newState;
    }

    /**
     * Update the CLI with the new state
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        switch (newState) {
            case INITIALIZATION -> System.out.println("All the players have joined: type 'startmatch' in order to start the match");
            case DISTRIBUTION ->
            {
                System.out.println("Commands available at any time:" +
                        "\n showmycardsinhand" +
                        "\n showmygrid" +
                        "\n showdrawtable" +
                        "\n chat ['public'/'private'] [name of the recipient] [text of the message]");
                System.out.println("Type 'distributecards' to distribute the cards");
            }
            case DRAWING -> System.out.println("Draw a card: type 'drawcard 0/1/2/3/4/5'");
            case END -> System.out.println("Type 'endgame' to get the results");
            case CLOSED -> System.out.println("The game has ended: type q to quit");
        }
    }

    /**
     * Update the GUI with the new state
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) throws IOException {
        Stage stage = gui.getStage();
        Scene scene = stage.getScene();
        switch (newState) {
            case INITIALIZATION:
                Button startMatchButton = (Button) scene.lookup("#startMatchButton");

                startMatchButton.setVisible(true);
                stage.setScene(scene);
                stage.show();
                break;
            case COLOUR:
                GridPane scoreBoard = (GridPane) scene.lookup("#scoreBoard");

                Image red = new Image("pion_rouge.png");
                ImageView redImage = new ImageView(red);
                Pane cell = new Pane();
                cell.setMinSize(CELL_SIZE, CELL_SIZE);
                cell.setMaxSize(CELL_SIZE, CELL_SIZE);
                redImage.setFitWidth(50);
                redImage.setFitHeight(50);
                redImage.setOnMouseClicked(event ->
                        gui.getController().sendMessage(
                                new SelectColourEvent(gui.getNickname(), PlayerColour.RED)));

                Image yellow = new Image("pion_jaune.png");
                ImageView yellowImage = new ImageView(yellow);
                yellowImage.setFitWidth(50);
                yellowImage.setFitHeight(50);
                yellowImage.setOnMouseClicked(event ->
                        gui.getController().sendMessage(
                                new SelectColourEvent(gui.getNickname(), PlayerColour.YELLOW)));

                Image green = new Image("pion_vert.png");
                ImageView greenImage = new ImageView(green);
                greenImage.setFitWidth(50);
                greenImage.setFitHeight(50);
                greenImage.setOnMouseClicked(event ->
                        gui.getController().sendMessage(
                                new SelectColourEvent(gui.getNickname(), PlayerColour.GREEN)));

                Image blue = new Image("pion_bleu.png");
                ImageView blueImage = new ImageView(blue);
                blueImage.setFitWidth(50);
                blueImage.setFitHeight(50);
                blueImage.setOnMouseClicked(event ->
                        gui.getController().sendMessage(
                                new SelectColourEvent(gui.getNickname(), PlayerColour.BLUE)));

                Pane cellRed = new Pane();
                cellRed.setMinSize(CELL_SIZE, CELL_SIZE);
                cellRed.setMaxSize(CELL_SIZE, CELL_SIZE);
                cellRed.setId("red");
                cellRed.getChildren().add(redImage);

                Pane cellYellow = new Pane();
                cellYellow.setMinSize(CELL_SIZE, CELL_SIZE);
                cellYellow.setMaxSize(CELL_SIZE, CELL_SIZE);
                cellYellow.setId("yellow");
                cellYellow.getChildren().add(yellowImage);

                Pane cellGreen = new Pane();
                cellGreen.setMinSize(CELL_SIZE, CELL_SIZE);
                cellGreen.setMaxSize(CELL_SIZE, CELL_SIZE);
                cellGreen.setId("green");
                cellGreen.getChildren().add(greenImage);

                Pane cellBlue = new Pane();
                cellBlue.setMinSize(CELL_SIZE, CELL_SIZE);
                cellBlue.setMaxSize(CELL_SIZE, CELL_SIZE);
                cellBlue.setId("blue");
                cellBlue.getChildren().add(blueImage);

                scoreBoard.add(cellRed, COLUMNS_SCOREBOARD, 0);
                scoreBoard.add(cellYellow, COLUMNS_SCOREBOARD, 25);
                scoreBoard.add(cellGreen, COLUMNS_SCOREBOARD, 50);
                scoreBoard.add(cellBlue, COLUMNS_SCOREBOARD, 75);

                stage.setScene(scene);
                stage.show();
                break;
        }
    }
}
