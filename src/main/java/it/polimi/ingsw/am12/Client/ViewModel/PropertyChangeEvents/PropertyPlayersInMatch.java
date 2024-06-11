package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.ControllerLobby;
import it.polimi.ingsw.am12.Client.UI.Gui.ControllerMatch;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import it.polimi.ingsw.am12.Network.Messages.Events.ChatEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * All the players have been added to the match
 */
public class PropertyPlayersInMatch implements PropertyChange {

    private final List<String> nicknames;
    private final static int ROWS_GRID = 81;
    private final static int COLUMNS_GRID = 81;
    private final static int ROWS_DRAWTABLE = 3;
    private final static int COLUMNS_DRAWTABLE = 3;

    private final static int COLUMNS_SCOREBOARD = 50;
    private final static int CELL_SIZE = 5;
    private final static int ROWS_SCOREBOARD = 100;

    /**
     * Class constructor
     * @param nicknames the list of the players in the match
     */
    public PropertyPlayersInMatch(List<String> nicknames) {
        this.nicknames = nicknames;
    }

    /**
     * Update the CLI with the players in the match
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        for(String nickname : nicknames)
            cli.addPlayer(nickname);
    }

    /**
     * Update the GUI with the players in the match: initialize playing grids, score board and chat
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) throws IOException {
        Stage stage = gui.getStage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Match.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        ControllerMatch cn = fxmlLoader.getController();
        cn.setClientController(gui.getController());
        stage.setScene(scene);

        //Add a playing grid for each player
        TabPane tabs = (TabPane) scene.lookup("#tabs");
        for(String nickname : nicknames) {
            Tab tab = new Tab(nickname);
            GridPane grid = new GridPane();
            grid.setId(nickname);
            for (int i = 0; i < COLUMNS_GRID*4; i++) {
                ColumnConstraints colConst = new ColumnConstraints();
                colConst.setPercentWidth(100.0 / COLUMNS_GRID*4);
                grid.getColumnConstraints().add(colConst);
            }
            for (int i = 0; i <ROWS_GRID*3; i++) {
                RowConstraints rowConst = new RowConstraints();
                rowConst.setPercentHeight(100.0 / ROWS_GRID*3);
                grid.getRowConstraints().add(rowConst);
            }


            ScrollPane scrollPane = new ScrollPane(grid);
            scrollPane.setId("scroll"+nickname);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            tab.setContent(scrollPane);
            tab.setClosable(false);
            tabs.getTabs().add(tab);
        }

        //Score board
        Tab tab = new Tab("Draw table");
        GridPane drawTable = new GridPane();
        drawTable.setId("drawTable");
        drawTable.setHgap(50);
        drawTable.setVgap(50);
        drawTable.setPadding(new Insets(50));
        for (int i = 0; i < COLUMNS_DRAWTABLE; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / COLUMNS_DRAWTABLE);
            drawTable.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i <ROWS_DRAWTABLE; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / ROWS_DRAWTABLE);
            drawTable.getRowConstraints().add(rowConst);
        }
        tab.setContent(drawTable);
        tab.setClosable(false);
        tabs.getTabs().add(tab);

        TabPane rightTabs = (TabPane) scene.lookup("#rightTabs");
        Tab scoreBoardTab = new Tab("Score board");
        GridPane scoreBoardGrid = new GridPane();

        scoreBoardGrid.setMaxWidth(CELL_SIZE*COLUMNS_SCOREBOARD);
        scoreBoardGrid.setMinWidth(CELL_SIZE*COLUMNS_SCOREBOARD);
        scoreBoardGrid.setMaxHeight(CELL_SIZE*ROWS_SCOREBOARD);
        scoreBoardGrid.setMinHeight(CELL_SIZE*ROWS_SCOREBOARD);

        Image img = new Image("plateau_score_imp.png");
        BackgroundSize backgroundSize = new BackgroundSize(scoreBoardGrid.getWidth(), scoreBoardGrid.getHeight(), false, false, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        scoreBoardGrid.setBackground(new Background(backgroundImage));

        for (int i = 0; i < COLUMNS_SCOREBOARD; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / COLUMNS_SCOREBOARD);
            scoreBoardGrid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i <ROWS_SCOREBOARD; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / ROWS_SCOREBOARD);
            scoreBoardGrid.getRowConstraints().add(rowConst);
        }
        scoreBoardGrid.setId("scoreBoard");

        scoreBoardTab.setContent(scoreBoardGrid);
        scoreBoardTab.setClosable(false);
        rightTabs.getTabs().add(scoreBoardTab);

        //Chat
        Tab chatTab = new Tab("Chat");
        chatTab.setClosable(false);
        VBox chat = new VBox();
        VBox messages = new VBox();
        messages.setId("messages");
        chat.getChildren().add(messages);

        CheckBox[] checkBoxes = new CheckBox[nicknames.size()];
        for(int i = 0; i<checkBoxes.length; i++) {
            checkBoxes[i] = new CheckBox();
            checkBoxes[i].setText(nicknames.get(i));
            chat.getChildren().add(checkBoxes[i]);
        }

        TextField messageField = new TextField();
        chat.getChildren().add(messageField);
        Button sendMessage = new Button("Send");
        sendMessage.setOnAction((event) -> {
            if(messageField.getText() != null && !messageField.getText().trim().isEmpty()) {
                int numSelected = 0;
                for(int i = 0; i<checkBoxes.length; i++) {
                    if(checkBoxes[i].isSelected()) {
                        numSelected ++;
                        gui.getController().sendMessage(new ChatEvent(gui.getNickname(), nicknames.get(i), false, messageField.getText()));
                    }
                }
                if(numSelected == 0) {
                    gui.getController().sendMessage(new ChatEvent(gui.getNickname(), "", true, messageField.getText()));
                }
            }

        });
        chat.getChildren().add(sendMessage);
        chatTab.setContent(chat);
        rightTabs.getTabs().add(chatTab);
    }
}

