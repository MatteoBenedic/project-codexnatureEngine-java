package it.polimi.ingsw.am12.Client.ClientController;

import it.polimi.ingsw.am12.AppLauncher;
import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;
import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.Network.Messages.Updates.CardDrawnUpdate;
import it.polimi.ingsw.am12.Network.Messages.Updates.NicknameEstablishedUpdate;
import it.polimi.ingsw.am12.Network.Messages.Updates.Update;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

class ClientControllerTest {

    @Test
    void EmptyDeckManagement() throws RemoteException {
        ClientController controller = new ClientController("127.0.0.1", 1600);
        CLI cli = new CLI(controller);

        Update u0 = new NicknameEstablishedUpdate("prova");
        controller.catchMessage(u0);

        Update u1 = new CardDrawnUpdate("a", 3, 4, 0, CardColour.RED, null, "prova", 1000, State.PLACING);
        controller.catchMessage(u1);
        Update u2 = new CardDrawnUpdate("a", 3, 4, 0, null, null, "prova", 1000, State.PLACING);
        controller.catchMessage(u2);
    }

}