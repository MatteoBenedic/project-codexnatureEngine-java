package it.polimi.ingsw.am12.View;

import it.polimi.ingsw.am12.ConnectionType;
import it.polimi.ingsw.am12.Controller.Controller;
import it.polimi.ingsw.am12.Controller.Events.DistributeCardsEvent;
import it.polimi.ingsw.am12.Controller.Events.JoinMatchEvent;
import it.polimi.ingsw.am12.Controller.Events.StartMatchEvent;
import it.polimi.ingsw.am12.Model.Logic.*;
import org.junit.jupiter.api.Test;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;


import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class VirtualViewTest {
//This virtualViewTest checks all the chain of communication from an internal view in the server itself

    @Test
    void chainCommsPerformUpdate() throws WrongNumberOfPlayersException, NotBoundException, RemoteException, WrongInformationException, InvalidSearchPositionException, NotYourTurnException, EmptyDeckException, DuplicateNicknameException, InvalidPlacementException, IllegalStateException {

        Controller c = new Controller(2);
        GameModel gm = c.getModel();

        VirtualView v1 = new VirtualView("p1", ConnectionType.SOCKET, null);

        c.addView(v1);
        //The type of connection is not important for the test, neither the registry
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
        VirtualView v2 = new VirtualView("p2", ConnectionType.SOCKET, null);
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