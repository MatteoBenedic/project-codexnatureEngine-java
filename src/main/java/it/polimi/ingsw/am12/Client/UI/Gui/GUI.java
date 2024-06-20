package it.polimi.ingsw.am12.Client.UI.Gui;

import it.polimi.ingsw.am12.Client.ClientController.ClientController;
import it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents.PropertyChange;
import it.polimi.ingsw.am12.Client.UI.UserInterface;
import it.polimi.ingsw.am12.Utils.Coordinate;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

import static java.lang.System.exit;

/**
 * Graphical User Interface
 */
public class GUI implements UserInterface {

    private Stage stage;
    private ClientController controller;
    private String nickname;
    private boolean cardSide;
    private int selectedCardInHand;
    private Coordinate selectedCoordinates;
    private final static int GRID_CENTRE_ROW = 40;
    private final static int GRID_CENTRE_COL = 40;


    /**
     * Class constructor
     * @param stage the JavaFx stage
     * @param controller the Client controller
     */
    public GUI(ClientController controller, Stage stage) {
        this.controller = controller;
        controller.addViewModelListener(this);
        this.stage = stage;
        this.cardSide = true;
        this.selectedCardInHand = 0;
        this.selectedCoordinates = new Coordinate(GRID_CENTRE_ROW, GRID_CENTRE_COL);
    }

    /**
     * Set the nickname of the player
     * @param nickname the nickname of the player
     */
    public void setNickname(String nickname) {
        controller.setVirtualView(nickname);
        new Thread(() -> controller.startPingPong()).start();
        this.nickname = nickname;
    }

    /**
     * Get the nickname of the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Get the JavaFx stage
     * @return the JavaFx stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Get the client controller
     * @return the client controller
     */
    public ClientController getController() {
        return controller;
    }

    /**
     * Get this instance of GUI
     * @return this instance of GUI
     */
    private GUI thisGUI() {
        return this;
    }

    /**
     * Get the current side of the cards in hand
     * @return the current side of the cards in hand
     */
    public boolean getCardSide() {
        return cardSide;
    }

    /**
     * Switch the current side of the cards in hand
     */
    public void switchCardSide() {
        cardSide = !cardSide;
    }

    /**
     * Set the current side of the cards in hand
     * @param cardSide the side of the cards in hand (TRUE = front, FALSE = back)
     */
    public void setCardSide(boolean cardSide) {
        this.cardSide = cardSide;
    }

    /**
     * Get the position of the selected card in hand
     * @return the position of the selected card in hand
     */
    public int getSelectedCardInHand() {
        return selectedCardInHand;
    }

    /**
     * Set the position of the selected card in hand
     * @param pos the position of the selected card in hand
     */
    public void setSelectedCardInHand(int pos) {
        this.selectedCardInHand=pos;
    }

    /**
     * Get the selected coordinates
     * @return the selected coordinates
     */
    public Coordinate getSelectedCoordinates() {
        return selectedCoordinates;
    }

    /**
     * Set the selected coordinates
     * @param x the row of the selected coordinates
     * @param y the column of the selected coordinates
     */
    public void setSelectedCoordinates(int x, int y) {
        selectedCoordinates.setX(x);
        selectedCoordinates.setY(y);
    }

    /**
     * Notify the GUI that a property in the ViewModel changed
     * @param p an instance of PropertyChange that describes with property has changed
     */
    @Override
    public void propertyChange(PropertyChange p) {
        Platform.runLater(() -> {
            try {
                p.updateGUI(thisGUI());
            } catch (IOException e) {
                System.err.println("Error in updating the GUI");
                exit(1);
            }
        });
    }
}
