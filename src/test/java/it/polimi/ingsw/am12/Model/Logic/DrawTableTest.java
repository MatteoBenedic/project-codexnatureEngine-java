package it.polimi.ingsw.am12.Model.Logic;

import it.polimi.ingsw.am12.Exceptions.EmptyDeckException;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.GameCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrawTableTest {

    @Test
    void drawCard() throws EmptyDeckException {
        //Test case: draw 1 public resource and 1 public gold
        //Given
        DrawTable t0 = new DrawTable();
        t0.drawCard(0);
        t0.drawCard(2);
        //Expect
        assertInstanceOf(GameCard.class, t0.drawCard(0));
        assertInstanceOf(GameCard.class, t0.drawCard(2));

        //Test case: draw all resources
        //Given
        DrawTable t1 = new DrawTable();
        for(int i = 0; i<38; i++) {
            t1.drawCard(5);
        }
        t1.drawCard(3);
        t1.drawCard(2);
        //Expect
        assertInstanceOf(GameCard.class, t1.drawCard(0));
        assertInstanceOf(GameCard.class, t1.drawCard(1));
        assertThrows(EmptyDeckException.class, () -> t1.drawCard(5));
        assertThrows(EmptyDeckException.class, () -> t1.drawCard(6));

        //Test case: draw all golds
        //Given
        DrawTable t2 = new DrawTable();
        for(int i = 0; i<38; i++) {
            t2.drawCard(4);
        }
        t2.drawCard(0);
        t2.drawCard(1);

        //Expect
        assertInstanceOf(GameCard.class, t2.drawCard(2));
        assertInstanceOf(GameCard.class, t2.drawCard(3));
        assertThrows(EmptyDeckException.class, () -> t2.drawCard(4));

        //Test case: draw all cards
        //Given
        DrawTable t3 = new DrawTable();
        for(int i = 0; i<39; i++) {
            t3.drawCard(0);
            t3.drawCard(2);
        }
        t3.drawCard(1);
        t3.drawCard(3);
        //Expect
        assertArrayEquals(new GameCard[] {null, null, null, null}, t3.getPublicCards());
        for(int i=0; i<6; i++)
        {
            int finalI = i;
            assertThrows(EmptyDeckException.class, () -> t3.drawCard(finalI));
        }
    }
}