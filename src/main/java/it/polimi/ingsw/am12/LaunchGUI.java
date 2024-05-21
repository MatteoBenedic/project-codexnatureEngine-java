package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.Gui.ControllerNickname;
import it.polimi.ingsw.am12.Gui.GUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Launch the JavaFx application, with the args from ClientLauncher
 */
public class LaunchGUI extends Application {

    /**
     * Launch the JavaFx application, with the args from ClientLauncher
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException if an I/O exception occurs
     */
    @Override
    public void start(Stage stage) throws IOException {
        List<String> params = getParameters().getRaw();
        String ip = params.get(0);
        int port = Integer.parseInt(params.get(1));
        String connectionType = params.get(3);

        ClientController controller = null;
        if(connectionType.equals("socket")) {
            controller = new ClientControllerSocket(ip, port);
        }
        if(connectionType.equals("rmi")) {
            controller = new ClientControllerRMI(ip, port);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Nickname.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        ControllerNickname cn = fxmlLoader.getController();
        cn.setClientController(controller);

        new GUI(controller, stage);

        stage.setTitle("Codex");
        stage.setScene(scene);
        stage.show();
    }
}
