package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import it.polimi.ingsw.am12.Utils.Assets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * The secret objective is selected
 */
public class PropertySecretObjective implements PropertyChange{

    private final String nickname;
    private final int index;
    private final boolean isYourObjective;

    /**
     * Class constructor
     * @param nickname the player who selected his objective
     * @param index the index of the secret objective
     * @param isYourObjective a boolean:
     *                        TRUE if the colour belongs to the player to whom the update is displayed
     *                        FALSE if the colour belongs to another player
     */
    public PropertySecretObjective(String nickname, int index, boolean isYourObjective) {
        this.nickname = nickname;
        this.index = index;
        this.isYourObjective = isYourObjective;
    }

    /**
     * Update the CLI with the new secret objective
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        if(isYourObjective) {
            cli.getObjectives().setSecretObjective(index);
            System.out.println("Your secret objective is ");
            cli.printObjectiveCard(index);
        }else
            System.out.println(nickname + " has selected his secret objective");
    }

    /**
     * Update the GUI with the new secret objective
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {
        Stage stage = gui.getStage();
        Scene scene = stage.getScene();

        if(isYourObjective) {
            HBox objContainer = (HBox) scene.lookup("#objectives");
            objContainer.getChildren().clear();

            Assets a = new Assets();
            String fileName = a.getFileName(index, true);
            Image img = new Image(fileName);
            ImageView imageView = new ImageView(img);
            imageView.setFitWidth(180);
            imageView.setFitHeight(100);
            objContainer.getChildren().add(imageView);
        }

    }
}
