package it.polimi.ingsw.am12.Client.UI.Gui;

import it.polimi.ingsw.am12.Client.ClientController.ClientController;
import it.polimi.ingsw.am12.Client.ClientController.ClientControllerRMI;
import it.polimi.ingsw.am12.Client.ClientController.ClientControllerSocket;
import it.polimi.ingsw.am12.Network.Messages.CloseMatchConnectionMessage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Launch the JavaFx application, with the args from ClientLauncher
 */
public class LaunchGUI extends Application {

    private final static int WINDOW_WIDTH = 1200;
    private final static int WINDOW_HEIGHT = 700;

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
        String ip = params.get(1);
        int port = Integer.parseInt(params.get(2));
        String connectionType = params.get(4);

        ClientController controller = null;
        if(connectionType.equals("socket")) {
            controller = new ClientControllerSocket(ip, port);
        }
        if(connectionType.equals("rmi")) {
            controller = new ClientControllerRMI(ip, port);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Nickname.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        ControllerNickname cn = fxmlLoader.getController();
        cn.setClientController(controller);

        new GUI(controller, stage);

        ClientController finalController = controller;
        stage.setOnCloseRequest(e -> {
            CloseMatchConnectionMessage closeConnectionMessage = new CloseMatchConnectionMessage();
            finalController.sendMessage(closeConnectionMessage);
            Platform.exit();
            System.exit(0);
        });

        stage.setTitle("Codex");
        stage.setScene(scene);
        stage.show();
    }
}
