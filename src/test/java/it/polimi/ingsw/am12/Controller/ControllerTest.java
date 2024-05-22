package it.polimi.ingsw.am12.Controller;

import it.polimi.ingsw.am12.Exceptions.WrongNumberOfPlayersException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void constructorCheck() throws WrongNumberOfPlayersException {
        //test wrong creation controller
        assertThrows(WrongNumberOfPlayersException.class, () -> {Controller c = new Controller(5);});
        assertDoesNotThrow(() -> {Controller c = new Controller(2);});
    }
}