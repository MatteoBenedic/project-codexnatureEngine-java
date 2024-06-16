package it.polimi.ingsw.am12.VirtualView;

import it.polimi.ingsw.am12.Client.ClientController.ClientController;
import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Controller.Controller;
import it.polimi.ingsw.am12.Exceptions.*;
import it.polimi.ingsw.am12.Exceptions.IllegalStateException;
import it.polimi.ingsw.am12.Network.Messages.Events.DistributeCardsEvent;
import it.polimi.ingsw.am12.Network.Messages.Events.JoinMatchEvent;
import it.polimi.ingsw.am12.Network.Messages.Events.StartMatchEvent;
import it.polimi.ingsw.am12.Model.Logic.GameModel;

import it.polimi.ingsw.am12.Server;
import org.junit.jupiter.api.Test;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class VirtualViewTest {
//This virtualViewTest checks all the chain of communication from an internal view in the server itself

    @Test
    void chainCommsPerformUpdate() throws WrongNumberOfPlayersException, NotBoundException, RemoteException, WrongInformationException, InvalidSearchPositionException, NotYourTurnException, EmptyDeckException, DuplicateNicknameException, InvalidPlacementException, IllegalStateException, InvalidParameterException, it.polimi.ingsw.am12.Exceptions.IllegalStateException {

        Controller c = new Controller(2);
        GameModel gm = c.getModel();
        //Client and RMISimulator set because it's needed to do not throw exception from the threads created in the model

        ClientController client = new ClientController("127.0.0.1", 1600);
        CLI clientUI = new CLI(client);

        VirtualView v1 = new VirtualViewRMI("p1", client, new Server(0, null));

        c.addView(v1);

        JoinMatchEvent e = new JoinMatchEvent("p1", v1);

        assertDoesNotThrow(() -> {v1.performEvent(e);});

        assertEquals(1, gm.getLobby().size());
        assertEquals("p1", gm.getLobby().getFirst());
        assertEquals(1, gm.getListeners().size());
        assertEquals(v1, gm.getListeners().getFirst());

        StartMatchEvent e1 = new StartMatchEvent();
        assertThrows(WrongNumberOfPlayersException.class, () -> v1.performEvent(e1));
        DistributeCardsEvent e2 = new DistributeCardsEvent();
        assertThrows(IllegalStateException.class, () -> v1.performEvent(e2));

        //Created second player
        VirtualView v2 = new VirtualViewRMI("p2", client, new Server(0, null));
        c.addView(v2);
        JoinMatchEvent e3 = new JoinMatchEvent("p2", v2);
        v2.performEvent(e3);
        assertEquals(2, gm.getLobby().size());
        assertEquals(2, gm.getListeners().size());


        StartMatchEvent e4 = new StartMatchEvent();
        StartMatchEvent e5 = new StartMatchEvent();
        Thread t1 = new Thread(() -> {
            try {
                sleep(1000);
                v1.performEvent(e4);
            } catch (Exception exc) {
                throw new RuntimeException("GeneralExc");
            }
        });

        Thread t2 = new Thread(() -> assertThrows(IllegalStateException.class, () -> {
            try {
                sleep(3000);
                v2.performEvent(e5);
            } catch (IllegalStateException exc2) {
                throw new IllegalStateException("IllegalState");
            } catch (Exception exc3) {
                throw new RuntimeException(" ");
            }
        }));

        t1.start();
        t2.start();
    }
}