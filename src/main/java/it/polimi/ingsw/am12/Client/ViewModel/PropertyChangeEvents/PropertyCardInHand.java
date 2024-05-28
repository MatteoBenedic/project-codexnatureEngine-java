package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.CLI.CLIDrawBufferHand;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import it.polimi.ingsw.am12.Utils.Assets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.List;

/**
 * The player has new cards in hand
 */
public class PropertyCardInHand implements PropertyChange{

    List<Integer> cards;

    /**
     * Class constructor
     * @param cards a list with the indexes of the cards in hand
     */
    public PropertyCardInHand(List<Integer> cards) {
        this.cards = cards;
    }

    /**
     * Update the CLI with the new card in hand
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        CLIDrawBufferHand hand = cli.getHand();
        hand.insertCardsInBuffer(cards);
        System.out.println("Your hand:");
        hand.printBuffer();
    }

    /**
     * Update the GUI with the new card in hand
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {
        Stage stage = gui.getStage();
        Scene scene = stage.getScene();

        HBox hand = (HBox) scene.lookup("#hand");
        hand.getChildren().clear();
        Assets a = new Assets();
        for(int cardIndex : cards) {
            String fileName = a.getFileName(cardIndex, true);
            Image img = new Image(fileName);
            ImageView imageView = new ImageView(img);
            imageView.setFitWidth(180);
            imageView.setFitHeight(100);
            hand.getChildren().add(imageView);

            imageView.setOnMouseClicked(event -> {
                        for (int i = 0; i < hand.getChildren().size(); i++) {
                            ImageView cardinhand = (ImageView) hand.getChildren().get(i);
                            if(cardinhand.equals(imageView))
                                gui.setSelectedCardInHand(i);
                        }
                    }
            );
        }

        Button switchSideButton = (Button) scene.lookup("#switchSideButton");
        switchSideButton.setOnAction(event -> {
            gui.switchCardSide();
            hand.getChildren().clear();
            for(int cardIndex : cards) {
                String fileName = a.getFileName(cardIndex, gui.getCardSide());
                Image img = new Image(fileName);
                ImageView imageView = new ImageView(img);
                imageView.setFitWidth(180);
                imageView.setFitHeight(100);
                hand.getChildren().add(imageView);

                imageView.setOnMouseClicked(event1 -> {
                            for (int i = 0; i < hand.getChildren().size(); i++) {
                                ImageView cardinhand = (ImageView) hand.getChildren().get(i);
                                if(cardinhand.equals(imageView))
                                    gui.setSelectedCardInHand(i);
                            }
                        }
                );
            }
        });
        switchSideButton.setVisible(true);

    }
}
