package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import it.polimi.ingsw.am12.Model.Logic.PlayerColour;
import it.polimi.ingsw.am12.Utils.PointPosition;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * A player has incremented his score
 */
public class PropertyPoints implements PropertyChange{

    private final String nickname;
    private final PlayerColour playerColour;
    private final boolean isYourPoints;
    private final int points;

    /**
     * Class constructor
     * @param nickname the player who has incremented his score
     * @param isYourPoints a boolean:
     *                     TRUE if the points belong to the player to whom the update is displayed
     *                     FALSE if the points belong to another player
     * @param playerColour the colour of the player
     * @param points the number of gained points
     */
    public PropertyPoints(String nickname, boolean isYourPoints, PlayerColour playerColour, int points) {
        this.nickname = nickname;
        this.playerColour = playerColour;
        this.isYourPoints = isYourPoints;
        this.points = points;
    }

    /**
     * Update the CLI with the new score
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        if(isYourPoints)
            System.out.println("You have now " + points + " points");
        else
            System.out.println(nickname + " has now " + points + " points");

        cli.getIntermediateClassification().setPoints(nickname, points);
    }

    /**
     * Update the GUI with the new score
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {
        Stage stage = gui.getStage();
        Scene scene = stage.getScene();

        Pane pion = (Pane) scene.lookup("#"+playerColour.getDescription());
        GridPane scoreBoardGrid = (GridPane) scene.lookup("#scoreBoard");
        scoreBoardGrid.getChildren().remove(pion);
        PointPosition pos = new PointPosition();
        scoreBoardGrid.add(pion, pos.getPosition(points).getX(), pos.getPosition(points).getY());

    }
}
