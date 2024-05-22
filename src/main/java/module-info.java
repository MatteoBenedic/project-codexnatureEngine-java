module it.polimi.ingsw.am12 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires org.controlsfx.controls;
    requires com.google.gson;
    requires java.rmi;

    opens it.polimi.ingsw.am12 to javafx.fxml;
    opens it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards to com.google.gson;
    opens it.polimi.ingsw.am12.Model.CardDesign.GameCard to com.google.gson;
    opens it.polimi.ingsw.am12.Utils to com.google.gson;
    exports it.polimi.ingsw.am12;
    exports it.polimi.ingsw.am12.Client.UI.CLI;
    opens it.polimi.ingsw.am12.Client.UI.CLI to com.google.gson, javafx.fxml;
    exports it.polimi.ingsw.am12.Exceptions;
    opens it.polimi.ingsw.am12.Exceptions to com.google.gson, javafx.fxml;
    exports it.polimi.ingsw.am12.Network;
    opens it.polimi.ingsw.am12.Network to javafx.fxml;
    exports it.polimi.ingsw.am12.Network.Messages;
    opens it.polimi.ingsw.am12.Network.Messages to javafx.fxml;
    exports it.polimi.ingsw.am12.Client.ViewModel;
    opens it.polimi.ingsw.am12.Client.ViewModel to javafx.fxml;
    exports it.polimi.ingsw.am12.Client.ClientController;
    opens it.polimi.ingsw.am12.Client.ClientController to javafx.fxml;
    exports it.polimi.ingsw.am12.Client.UI;
    opens it.polimi.ingsw.am12.Client.UI to javafx.fxml;
    exports it.polimi.ingsw.am12.Client.UI.Gui;
    opens it.polimi.ingsw.am12.Client.UI.Gui to javafx.fxml;
}