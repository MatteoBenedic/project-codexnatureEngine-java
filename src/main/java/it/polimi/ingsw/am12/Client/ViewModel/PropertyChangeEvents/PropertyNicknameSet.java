package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.ControllerCreateOrJoinMatch;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The nickname has been set
 */
public class PropertyNicknameSet implements PropertyChange {

    String nickname;

    /**
     * Class constructor
     * @param nickname the nickname tha has been set
     */
    public PropertyNicknameSet(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Update the CLI with the nickname
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        System.out.println("You've set your nickname: " + nickname);
        System.out.println("Commands available are:" +
                "\n creatematch [num players] [name of the match]" +
                "\n joinmatch [name of the match]" +
                "\n getlobbies");
        cli.setNickname(nickname);
    }

    /**
     * Update the GUI with the nickname
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) throws IOException {
        gui.setNickname(nickname);
        Stage stage = gui.getStage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateOrJoinMatch.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        ControllerCreateOrJoinMatch cn = fxmlLoader.getController();
        cn.setClientController(gui.getController());
        stage.setScene(scene);
        stage.show();
    }
}
