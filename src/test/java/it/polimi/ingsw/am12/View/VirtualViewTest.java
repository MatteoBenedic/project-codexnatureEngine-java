package it.polimi.ingsw.am12.View;

import it.polimi.ingsw.am12.Controller.Controller;
import it.polimi.ingsw.am12.Model.Logic.WrongNumberOfPlayersException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VirtualViewTest {

    /*
    @Test
    void joinMatch() throws WrongNumberOfPlayersException {
        Controller controller1 = new Controller(2);
        VirtualView viewPlayer1 = new VirtualView("player1");
        controller1.addView(viewPlayer1);
        viewPlayer1.joinMatch();

        VirtualView viewPlayer2 = new VirtualView("player2");
        controller1.addView(viewPlayer2);
        viewPlayer2.joinMatch();

        String expectedMessage1 = "Ciao player1\nI giocatori della partita sono tutti arrivati: player2";
        String expectedMessage2 = "Ciao player2\nI giocatori della partita sono tutti arrivati: player1";
        assertEquals(expectedMessage1, viewPlayer1.getMessage());
        assertEquals(expectedMessage2, viewPlayer2.getMessage());
    }

     */
}