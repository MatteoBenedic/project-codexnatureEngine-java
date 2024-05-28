package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import it.polimi.ingsw.am12.Model.Logic.PlayerColour;
import it.polimi.ingsw.am12.Utils.PointPosition;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * A player has a new colour
 */
public class PropertyPlayerColour implements PropertyChange{

    String nickname;
    PlayerColour playerColour;
    boolean isYourColour;

    /**
     * Class constructor
     * @param nickname the nickname of the player who has a new colour
     * @param playerColour the new colour
     * @param isYourColour a boolean:
     *                     TRUE if the colour belongs to the player to whom the update is displayed
     *                     FALSE if the colour belongs to another player
     */

    public PropertyPlayerColour(String nickname, PlayerColour playerColour, boolean isYourColour) {
        this.nickname = nickname;
        this.playerColour = playerColour;
        this.isYourColour = isYourColour;
    }

    /**
     * Update the CLI with the new player colour
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        if(isYourColour) {
            System.out.println("You colour is now "+ playerColour.getDescription());
        }
        else {
            System.out.println(nickname + "'s colour is now "+playerColour.getDescription());
        }
    }

    /**
     * Update the GUI with the new player colour
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
        scoreBoardGrid.add(pion, pos.getPosition(0).getX(), pos.getPosition(0).getY());

    }
}
