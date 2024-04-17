package it.polimi.ingsw.am12.Model.CardDesign.GameCard;
import it.polimi.ingsw.am12.Utils.JSONParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameCardTest {
    @Test
    public void initializationValidSide(){
        JSONParser parser = new JSONParser();
        List<GameCard> cards = parser.parseGoldCards();
        GameCard prCard = cards.get(3);

        assertNull(prCard.getValidSide());
        assertThrows(NullPointerException.class, prCard::getWhichSide);
        assertDoesNotThrow(() -> {prCard.setValidSide(true);});


        prCard.setDefaultSide();
        assertInstanceOf(GoldFront.class, prCard.getValidSide());
        assertTrue(prCard.getWhichSide());

        prCard.setValidSide(false);
        assertInstanceOf(ColouredBack.class, prCard.getValidSide());
        assertFalse(prCard.getWhichSide());
    }

    @Test
    public void checkReturnValuesForPlacing(){
        JSONParser parser = new JSONParser();
        List<GameCard> golds = parser.parseGoldCards();
        List<GameCard> resses = parser.parseResourceCards();
        List<GameCard> starters = parser.parseStartCards();
        GameCard gc = golds.get(3); // 43
        GameCard rc = resses.get(5); // 5
        GameCard sc = starters.get(0); // 80


        gc.setDefaultSide();
        rc.setDefaultSide();

        int[] t1;
        int[] t2;

        t1 = gc.getRequirements();
        t2 = rc.getRequirements();
        assertEquals(1, t1[0]);
        assertEquals(3, t1[1]);
        for (int i = 2; i < 7; i++)
            assertEquals(0, t1[i]);
        for(int i = 0; i < 7; i++)
            assertEquals(0, t2[i]);


        assertNull(rc.getCondition());
        assertNotNull(gc.getCondition());
        assertEquals("Corner", gc.getCondition().getType());
        assertNull(gc.getCondition().getObject());


        t1 = gc.getResources();
        for (int i = 0; i < 7; i++)
            assertEquals(0, t1[i]);

        t2 = rc.getResources();
        for (int i = 0; i < 3; i++)
            assertEquals(1, t2[i]);
        for (int i = 3; i < 7; i++)
            assertEquals(0, t2[i]);


        gc.setValidSide(false);
        rc.setValidSide(false);

        assertNull(gc.getCondition());
        assertNull(rc.getCondition());
        t1 = gc.getRequirements();
        for(int i = 0; i < 7; i++)
            assertEquals(0, t1[i]);

        t1 = gc.getResources();
        for(int i = 0; i < 7; i++){
            if(i == 1)
                assertEquals(1, t1[i]);
            else
                assertEquals(0, t1[i]);
        }

        t2 = rc.getResources();
        for(int i = 0; i < 7; i++){
            if(i == 1)
                assertEquals(1, t2[i]);
            else
                assertEquals(0, t2[i]);
        }
    }

    @Test
    public void CornerCheckingFromCard(){
        JSONParser parser = new JSONParser();
        List<GameCard> cards = parser.parseResourceCards();
        GameCard card = cards.get(5);


        card.setDefaultSide();
        assertThrows(IndexOutOfBoundsException.class, () -> card.isThereCorner(7));
        assertTrue(card.isThereCorner(0));
        assertFalse(card.isThereCorner(2));

        assertThrows(NullPointerException.class, () -> card.getResourceCorner(2));
        assertEquals(Element.INKWELL, card.getResourceCorner(0));


        card.setValidSide(false);
        assertTrue(card.isThereCorner(0));

        assertNull(card.getResourceCorner(0));
    }
}