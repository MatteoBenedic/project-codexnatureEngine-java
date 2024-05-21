package it.polimi.ingsw.am12.Gui;

import it.polimi.ingsw.am12.CreateMatchMessage;
import it.polimi.ingsw.am12.JoinMatchMessage;
import it.polimi.ingsw.am12.LobbiesRequestMessage;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * GUI controller to handle the actions of match creation, match joining
 * and request of available lobbies
 */
public class ControllerCreateOrJoinMatch extends ControllerGUI {

    @FXML
    VBox root;
    @FXML
    VBox content;
    @FXML
    Label matchNameLabel;
    @FXML
    TextField matchNameField;
    @FXML
    Label numPlayersLabel;
    @FXML
    TextField numPlayersField;
    @FXML
    Button confirmButton;
    @FXML
    Text errorMessage;

    /**
     * Reset the content of the view, to call when the user selects a different action
     */
    private void resetContent() {
        content.getChildren().clear();
        errorMessage.setText("");
    }


    /**
     * The user wants to create a match and clicks on the corresponding button:
     * display the text fields for match creation and a confirm button that will send
     * a CreateMatchMessage
     */
    @FXML
    public void onCreateMatchClick() {

        resetContent();

        matchNameLabel = new Label("Enter match name...");
        matchNameField = new TextField();
        numPlayersLabel = new Label("Enter number of players: (min 2, max 4)");
        numPlayersField = new TextField();
        confirmButton = new Button("Create");

        content.getChildren().add(matchNameLabel);
        content.getChildren().add(matchNameField);
        content.getChildren().add(numPlayersLabel);
        content.getChildren().add(numPlayersField);
        content.getChildren().add(confirmButton);

        confirmButton.setOnAction(event -> {
            String matchName = matchNameField.getText();
            try {
                int numPlayers = Integer.parseInt(numPlayersField.getText());
                if(numPlayers > 4 || numPlayers < 2)
                    throw new NumberFormatException();
                clientController.sendMessage(new CreateMatchMessage(numPlayers, matchName));

            } catch (NumberFormatException e) {
                errorMessage.setText("Insert a valid number (min 2, max 4)");
            }

        });

    }

    /**
     * The user wants to join a match and clicks on the corresponding button:
     * display the text field for match joining and a confirm button that will send
     * a JoinMatchMessage
     */
    @FXML
    public void onJoinMatchClick() {

        resetContent();

        matchNameLabel = new Label("Enter match name...");
        matchNameField = new TextField();
        confirmButton = new Button("Join");

        content.getChildren().add(matchNameLabel);
        content.getChildren().add(matchNameField);
        content.getChildren().add(confirmButton);

        confirmButton.setOnAction(event -> {
            String matchName = matchNameField.getText();
            clientController.sendMessage(new JoinMatchMessage(matchName));
        });
    }

    /**
     * The user wants to see a list of available lobbies and clicks on the corresponding button:
     * send a LobbiesRequestMessage
     */
    @FXML
    public void onGetLobbiesClick() {
        clientController.sendMessage(new LobbiesRequestMessage());
    }
}
