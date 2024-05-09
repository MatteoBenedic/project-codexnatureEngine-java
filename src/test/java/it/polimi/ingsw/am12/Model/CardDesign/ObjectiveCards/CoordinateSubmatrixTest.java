package it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards;

import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CoordinateSubmatrixTest {

    @Test
    public void CoordinateSubmatrix() throws IllegalRequirementsException {
        //constructor test with valid coordinates
        CoordinateSubmatrix coord1 = new CoordinateSubmatrix(2,1,CardColour.RED);
        assertEquals(2, coord1.getX());
        assertEquals(1, coord1.getY());
        assertEquals(CardColour.RED, coord1.getColour());
        //constructor test with invalid x coordinate
        assertThrows(IllegalRequirementsException.class, ()-> new CoordinateSubmatrix(90,1, CardColour.RED));
        //constructor test with invalid y coordinate
        assertThrows(IllegalRequirementsException.class, () -> new CoordinateSubmatrix(1,-1,CardColour.BLUE));

    }


    @Test
    public void getX() throws IllegalRequirementsException {
        CoordinateSubmatrix coord2 = new CoordinateSubmatrix(1,2,CardColour.BLUE);
        assertEquals(1, coord2.getX());
    }


    @Test
    void getY() throws IllegalRequirementsException {
        CoordinateSubmatrix coord4 = new CoordinateSubmatrix(2,0,CardColour.BLUE);
        assertEquals(2, coord4.getX());
    }

}