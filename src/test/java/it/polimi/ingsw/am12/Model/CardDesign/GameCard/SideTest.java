package it.polimi.ingsw.am12.Model.CardDesign.GameCard;

import it.polimi.ingsw.am12.Utils.JSONParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SideTest {
    @Test
    public void getCornerTest(){
        JSONParser parser = new JSONParser();
        List<GameCard> cards = parser.parseGoldCards();
        GameCard card = cards.get(3);

        card.setDefaultSide();
        Side side = card.getValidSide();

        assertNull(side.getCorner(2));
        assertThrows(IndexOutOfBoundsException.class, () -> {side.getCorner(7);});
        assertNull(side.getCorner(0).getElement());
    }

    @Test
    public void getPointsTest(){
        JSONParser parser = new JSONParser();
        List<GameCard> cards = parser.parseGoldCards();
        GameCard card = cards.get(3);

        card.setDefaultSide();
        Side front = card.getValidSide();
        card.setValidSide(false);
        Side back = card.getValidSide();

        assertEquals(2, front.getPoints());
        assertEquals(0, back.getPoints());
    }

    @Test
    public void getRequirementsTest(){
        JSONParser parser = new JSONParser();
        List<GameCard> cards = parser.parseGoldCards();
        List<GameCard> resCards = parser.parseResourceCards();
        GameCard card = cards.get(3);
        GameCard resCard = resCards.get(5);

        card.setDefaultSide();
        Side front = card.getValidSide();
        card.setValidSide(false);
        Side back = card.getValidSide();
        resCard.setDefaultSide();
        Side resFront = card.getValidSide();
        Side test = new StartBack();

        assertNull(test.getRequirements());
        assertNull(back.getRequirements());
        assertNull(resFront.getRequirements());
        List<Element> req = front.getRequirements();
        assertEquals(Element.FUNGUS, req.get(0));
        assertEquals(Element.FUNGUS, req.get(1));
        assertEquals(Element.FUNGUS, req.get(2));
        assertEquals(Element.ANIMAL, req.get(3));
    }

    @Test
    public void getConditionTest(){
        JSONParser parser = new JSONParser();
        List<GameCard> cards = parser.parseGoldCards();
        List<GameCard> resCards = parser.parseResourceCards();
        GameCard gc1 = cards.get(3);
        GameCard gc2 = cards.get(12);
        GameCard gc3 = cards.get(16);
        GameCard resCard = resCards.get(5);

        gc1.setDefaultSide();
        gc2.setDefaultSide();
        gc3.setDefaultSide();
        resCard.setDefaultSide();
        Side f1 = gc1.getValidSide();
        Side f2 = gc2.getValidSide();
        Side f3 = gc3.getValidSide();
        Side fr1 = resCard.getValidSide();

        gc1.setValidSide(false);
        resCard.setValidSide(false);
        Side b1 = gc1.getValidSide();
        Side br1 = resCard.getValidSide();

        assertEquals("Corner", f1.getCondition().getType());
        assertNull(f1.getCondition().getObject());

        assertEquals("Object", f2.getCondition().getType());
        assertEquals(Element.INKWELL, f2.getCondition().getObject());

        assertNull(f3.getCondition());
        assertNull(fr1.getCondition());
        assertNull(b1.getCondition());
        assertNull(br1.getCondition());
    }

    @Test
    public void getCenterTest(){
        JSONParser parser = new JSONParser();
        List<GameCard> resCards = parser.parseResourceCards();
        List<GameCard> stCards = parser.parseStartCards();
        GameCard sc = stCards.get(4);
        GameCard rc = resCards.get(5);

        sc.setDefaultSide();
        Side fs = sc.getValidSide();

        sc.setValidSide(false);
        rc.setValidSide(false);
        Side bs = sc.getValidSide();
        Side br = rc.getValidSide();

        assertNull(fs.getCenter());
        assertNotNull(bs.getCenter());
        assertNotNull(br.getCenter());
        List<Element> cs = bs.getCenter();
        List<Element> cr = br.getCenter();

        assertEquals(1, cr.size());
        assertEquals(Element.FUNGUS, cr.getFirst());
        assertEquals(Element.ANIMAL, cs.get(0));
        assertEquals(Element.INSECT, cs.get(1));
        assertEquals(Element.PLANT, cs.get(2));
    }

}