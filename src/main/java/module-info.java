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
    opens it.polimi.ingsw.am12.CLI to com.google.gson;
    exports it.polimi.ingsw.am12;

}