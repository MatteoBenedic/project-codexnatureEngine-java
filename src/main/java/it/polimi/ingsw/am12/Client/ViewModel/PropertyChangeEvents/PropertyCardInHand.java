package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.CLI.CLIDrawBufferHand;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import it.polimi.ingsw.am12.Utils.Assets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.List;

/**
 * The player has new cards in hand
 */
public class PropertyCardInHand implements PropertyChange{

    private final List<Integer> cards;
    private final static int CARD_WIDTH_SMALL = 180;
    private final static int CARD_HEIGHT_SMALL = 100;

    private final static int CARD_WIDTH_BIG = 210;
    private final static int CARD_HEIGHT_BIG = 120;

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
        gui.setCardSide(true);
        gui.setSelectedCardInHand(0);
        for(int cardIndex : cards) {
            String fileName = a.getFileName(cardIndex, true);
            Image img = new Image(fileName);
            ImageView imageView = new ImageView(img);
            if(cards.indexOf(cardIndex) == 0) {
                imageView.setFitWidth(CARD_WIDTH_BIG);
                imageView.setFitHeight(CARD_HEIGHT_BIG);
            }
            else {
                imageView.setFitWidth(CARD_WIDTH_SMALL);
                imageView.setFitHeight(CARD_HEIGHT_SMALL);
            }
            hand.getChildren().add(imageView);
            setCardInHandOnClick(hand, imageView, gui);
        }

        //Select first card by default



        Button switchSideButton = (Button) scene.lookup("#switchSideButton");
        switchSideButton.setOnAction(event -> {
            gui.switchCardSide();
            gui.setSelectedCardInHand(0);
            hand.getChildren().clear();
            for(int cardIndex : cards) {
                String fileName = a.getFileName(cardIndex, gui.getCardSide());
                Image img = new Image(fileName);
                ImageView imageView = new ImageView(img);
                if(cards.indexOf(cardIndex) == 0) {
                    imageView.setFitWidth(CARD_WIDTH_BIG);
                    imageView.setFitHeight(CARD_HEIGHT_BIG);
                }
                else {
                    imageView.setFitWidth(CARD_WIDTH_SMALL);
                    imageView.setFitHeight(CARD_HEIGHT_SMALL);
                }
                hand.getChildren().add(imageView);

                setCardInHandOnClick(hand, imageView, gui);

            }
        });
        switchSideButton.setVisible(true);
    }

    /**
     * Set the onClick listener of a card in hand
     * @param hand the container of the cards in hand
     * @param imageView the card in hand
     * @param gui the GUI
     */
    private void setCardInHandOnClick(HBox hand, ImageView imageView, GUI gui) {
        imageView.setOnMouseClicked(event1 -> {
            for (int i = 0; i < hand.getChildren().size(); i++) {
                ImageView cardInHand = (ImageView) hand.getChildren().get(i);

                if(cardInHand.equals(imageView)) {
                    gui.setSelectedCardInHand(i);
                    cardInHand.setFitWidth(CARD_WIDTH_BIG);
                    cardInHand.setFitHeight(CARD_HEIGHT_BIG);
                }
                else {
                    cardInHand.setFitWidth(CARD_WIDTH_SMALL);
                    cardInHand.setFitHeight(CARD_HEIGHT_SMALL);
                }
            }
        });
    }
}

