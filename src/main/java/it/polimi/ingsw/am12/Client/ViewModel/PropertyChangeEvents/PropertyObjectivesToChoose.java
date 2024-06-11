package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import it.polimi.ingsw.am12.Network.Messages.Events.SelectObjectiveEvent;
import it.polimi.ingsw.am12.Utils.Assets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


/**
 * The secret objective is selected
 */
public class PropertyObjectivesToChoose implements PropertyChange{

    private final int[] objectives;

    /**
     * Class constructor
     * @param objectives an array with the indexes of the 2 secret objective. The user has to select one of them.
     */
    public PropertyObjectivesToChoose(int[] objectives) {
        this.objectives = objectives;
    }

    /**
     * Update the CLI with the new secret objective
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        int first = objectives[0];
        int second = objectives[1];
        System.out.println("You have to choose one of these secret objectives ");
        cli.printObjectiveCard(first);
        System.out.println(" ");
        System.out.println(" or  ");
        cli.printObjectiveCard(second);
        System.out.println(" ");
    }

    /**
     * Update the GUI with the new secret objective
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {
        Stage stage = gui.getStage();
        Scene scene = stage.getScene();

        HBox objContainer = (HBox) scene.lookup("#objectives");
        Assets a = new Assets();
        for(int i = 0; i<objectives.length; i++) {
            String fileName = a.getFileName(objectives[i], true);
            Image img = new Image(fileName);
            ImageView imageView = new ImageView(img);
            imageView.setFitWidth(180);
            imageView.setFitHeight(100);
            objContainer.getChildren().add(imageView);

            boolean isFirst = (i==0);
            imageView.setOnMouseClicked(event -> {
                        gui.getController().sendMessage(new SelectObjectiveEvent(gui.getNickname(), isFirst));
                    }
            );
        }


    }
}
