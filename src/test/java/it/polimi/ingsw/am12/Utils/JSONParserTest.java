package it.polimi.ingsw.am12.Utils;

import it.polimi.ingsw.am12.Model.CardDesign.GameCard.*;
import it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JSONParserTest {

    @Test
    void parseObjectiveCards(){

        JSONParser parser = new JSONParser();
        List<ObjectiveCard> cards = parser.parseObjectiveCards();

        assertEquals(16, cards.size());
        for(int i=0; i<8; i++)
        {
            assertInstanceOf(PatternObjectiveCard.class, cards.get(i));
            assertEquals(86+i, cards.get(i).getObjIndex());
        }
        for(int i = 8; i<16; i++)
        {
            assertInstanceOf(ElementsObjectiveCard.class, cards.get(i));
            assertEquals(86+i, cards.get(i).getObjIndex());
        }
    }
    @Test
    void parseResourceCards(){
        JSONParser parser = new JSONParser();
        List<GameCard> cards = parser.parseResourceCards();
        int[] numOfElements = new int[9]; //number of elements, empty corners, hidden corners
        int sumOfPoints = 0;
        for(int i=0; i<9; i++){
            numOfElements[i]=0;
        }

        assertEquals(40, cards.size());
        for(int i = 0; i<40; i++) {
            GameCard card = cards.get(i);
            assertEquals(i, card.getIndex());
            if(i<10)
                assertEquals("red", card.getColour());
            if(i>=10 && i<20)
                assertEquals("green", card.getColour());
            if(i>=20 && i<30)
                assertEquals("blue", card.getColour());
            if(i>=30)
                assertEquals("purple", card.getColour());

            //Resource card front
            card.setValidSide(true);
            assertInstanceOf(NotGoldFront.class, card.getValidSide());

            for(int j=0; j<4; j++) {
                if (!card.isThereCorner(j)) {
                    numOfElements[8]++;
                }
                else {
                    switch (card.getResourceCorner(j)) {
                        case Element.ANIMAL:
                            numOfElements[0]++;
                            break;
                        case Element.FUNGUS:
                            numOfElements[1]++;
                            break;
                        case Element.INKWELL:
                            numOfElements[2]++;
                            break;
                        case Element.INSECT:
                            numOfElements[3]++;
                            break;
                        case Element.MANUSCRIPT:
                            numOfElements[4]++;
                            break;
                        case Element.PLANT:
                            numOfElements[5]++;
                            break;
                        case Element.QUILL:
                            numOfElements[6]++;
                            break;
                        case null:
                            numOfElements[7]++;
                    }
                }
            }

            sumOfPoints = sumOfPoints + card.getValidSide().getPoints();

            //Resource card back
            card.setValidSide(false);
            assertInstanceOf(ColouredBack.class, card.getValidSide());

            assertEquals(1, card.getValidSide().getCenter().size());

            if(i<10)
                assertEquals(Element.FUNGUS, card.getValidSide().getCenter().getFirst());
            if(i>=10 && i<20)
                assertEquals(Element.PLANT, card.getValidSide().getCenter().getFirst());
            if(i>=20 && i<30)
                assertEquals(Element.ANIMAL, card.getValidSide().getCenter().getFirst());
            if(i>=30)
                assertEquals(Element.INSECT, card.getValidSide().getCenter().getFirst());

            for(int j=0; j<4; j++) {
                assertTrue(card.isThereCorner(j));
                assertNull(card.getResourceCorner(j));
            }

            sumOfPoints = sumOfPoints + card.getValidSide().getPoints();
        }

        assertEquals(17, numOfElements[0]); //animal
        assertEquals(17, numOfElements[1]); //fungus
        assertEquals(4, numOfElements[2]); //inkwell
        assertEquals(17, numOfElements[3]); //insect
        assertEquals(4, numOfElements[4]); //manuscript
        assertEquals(17, numOfElements[5]); //plant
        assertEquals(4, numOfElements[6]); //quill
        assertEquals(41, numOfElements[7]); //empty corners
        assertEquals(39, numOfElements[8]); //hidden corners
        assertEquals(12, sumOfPoints);
    }

    @Test
    void parseGoldCards(){

        JSONParser parser = new JSONParser();
        List<GameCard> cards = parser.parseGoldCards();
        int[] numOfElements = new int[9]; //number of elements, empty corners, hidden corners
        int[] numOfConditions = new int [5]; //number of object, corner, null conditions
        int[] numOfRequirements = new int[7]; //number of resources in requirements
        int sumOfPoints = 0;
        for(int i=0; i<9; i++){
            numOfElements[i]=0;
        }
        for(int i=0; i<5; i++){
            numOfConditions[i]=0;
        }
        for(int i=0; i<7; i++){
            numOfRequirements[i]=0;
        }

        assertEquals(40, cards.size());
        for(int i = 0; i<40; i++) {
            GameCard card = cards.get(i);
            assertEquals(40+i, card.getIndex());
            if(i<10)
                assertEquals("red", card.getColour());
            if(i>=10 && i<20)
                assertEquals("green", card.getColour());
            if(i>=20 && i<30)
                assertEquals("blue", card.getColour());
            if(i>=30)
                assertEquals("purple", card.getColour());

            //Gold card front
            card.setValidSide(true);
            assertInstanceOf(GoldFront.class, card.getValidSide());

            for(int j=0; j<4; j++) {
                if (!card.isThereCorner(j)) {
                    numOfElements[8]++;
                }
                else {
                    switch (card.getResourceCorner(j)) {
                        case Element.ANIMAL:
                            numOfElements[0]++;
                            break;
                        case Element.FUNGUS:
                            numOfElements[1]++;
                            break;
                        case Element.INKWELL:
                            numOfElements[2]++;
                            break;
                        case Element.INSECT:
                            numOfElements[3]++;
                            break;
                        case Element.MANUSCRIPT:
                            numOfElements[4]++;
                            break;
                        case Element.PLANT:
                            numOfElements[5]++;
                            break;
                        case Element.QUILL:
                            numOfElements[6]++;
                            break;
                        case null:
                            numOfElements[7]++;
                    }
                }
            }

            sumOfPoints = sumOfPoints + card.getValidSide().getPoints();

            Condition c = card.getCondition();
            if(c==null)
            {
                numOfConditions[4]++;
            }
            else if(c.equals(Condition.CORNER))
            {
                assertNull(c.getName());
                numOfConditions[3]++;
            }
            else
            {
                switch (c.getName()) {
                    case Element.INKWELL:
                        numOfConditions[0]++;
                        break;
                    case Element.MANUSCRIPT:
                        numOfConditions[1]++;
                        break;
                    case Element.QUILL:
                        numOfConditions[2]++;
                        break;
                }
            }

            for(int h=0; h<card.getRequirements().length; h++) {
                numOfRequirements[h] = numOfRequirements[h] + card.getRequirements()[h];
            }

            //Gold card back
            card.setValidSide(false);
            assertInstanceOf(ColouredBack.class, card.getValidSide());

            assertEquals(1, card.getValidSide().getCenter().size());

            if(i<10)
                assertEquals(Element.FUNGUS, card.getValidSide().getCenter().getFirst());
            if(i>=10 && i<20)
                assertEquals(Element.PLANT, card.getValidSide().getCenter().getFirst());
            if(i>=20 && i<30)
                assertEquals(Element.ANIMAL, card.getValidSide().getCenter().getFirst());
            if(i>=30)
                assertEquals(Element.INSECT, card.getValidSide().getCenter().getFirst());

            for(int j=0; j<4; j++) {
                assertTrue(card.isThereCorner(j));
                assertNull(card.getResourceCorner(j));
            }

            sumOfPoints = sumOfPoints + card.getValidSide().getPoints();
        }

        assertEquals(0, numOfElements[0]); //animal
        assertEquals(0, numOfElements[1]); //fungus
        assertEquals(8, numOfElements[2]); //inkwell
        assertEquals(0, numOfElements[3]); //insect
        assertEquals(8, numOfElements[4]); //manuscript
        assertEquals(0, numOfElements[5]); //plant
        assertEquals(8, numOfElements[6]); //quill
        assertEquals(80, numOfElements[7]); //empty corners
        assertEquals(56, numOfElements[8]); //hidden corners

        assertEquals(92, sumOfPoints);

        assertEquals(4, numOfConditions[0]); //object inkwell
        assertEquals(4, numOfConditions[1]); //object manuscript
        assertEquals(4, numOfConditions[2]); //object quill
        assertEquals(12, numOfConditions[3]); //corner
        assertEquals(16, numOfConditions[4]); //null

        assertEquals(35, numOfRequirements[0]); //animal
        assertEquals(35, numOfRequirements[1]); //fungus
        assertEquals(0, numOfRequirements[2]); //inkwell
        assertEquals(35, numOfRequirements[3]); //insect
        assertEquals(0, numOfRequirements[4]); //manuscript
        assertEquals(35, numOfRequirements[5]); //plant
        assertEquals(0, numOfRequirements[6]); //quill
    }

    @Test
    void parseStartCards() {

        JSONParser parser = new JSONParser();
        List<GameCard> cards = parser.parseStartCards();

        assertEquals(6, cards.size());

        int[] numOfCornerResources = new int[9]; //number of corner resources, empty corners, hidden corners
        int[] numOfCentreResources = new int[7]; //number of centre resources

        for (int i = 0; i < 9; i++) {
            numOfCornerResources[i] = 0;
        }

        for (int i = 0; i < 7; i++) {
            numOfCentreResources[i] = 0;
        }
        
        for(GameCard card : cards) {
            assertNull(card.getColour());
            
            //Start card front
            card.setValidSide(true);
            assertInstanceOf(NotGoldFront.class, card.getValidSide());
            assertEquals(0, card.getPoints());

            for(int j=0; j<4; j++) {
                if (!card.isThereCorner(j)) {
                    numOfCornerResources[5]++;
                }
                else {
                    switch (card.getResourceCorner(j)) {
                        case Element.ANIMAL:
                            numOfCornerResources[0]++;
                            break;
                        case Element.FUNGUS:
                            numOfCornerResources[1]++;
                            break;
                        case Element.INKWELL:
                            numOfCornerResources[2]++;
                            break;
                        case Element.INSECT:
                            numOfCornerResources[3]++;
                            break;
                        case Element.MANUSCRIPT:
                            numOfCornerResources[4]++;
                            break;
                        case Element.PLANT:
                            numOfCornerResources[5]++;
                            break;
                        case Element.QUILL:
                            numOfCornerResources[6]++;
                            break;
                        case null:
                            numOfCornerResources[7]++;
                    }
                }
            }
            
            //Start card back
            card.setValidSide(false);
            assertInstanceOf(StartBack.class, card.getValidSide());

            for(int j=0; j<4; j++) {
                if (!card.isThereCorner(j)) {
                    numOfCornerResources[8]++;
                }
                else {
                    switch (card.getResourceCorner(j)) {
                        case Element.ANIMAL:
                            numOfCornerResources[0]++;
                            break;
                        case Element.FUNGUS:
                            numOfCornerResources[1]++;
                            break;
                        case Element.INKWELL:
                            numOfCornerResources[2]++;
                            break;
                        case Element.INSECT:
                            numOfCornerResources[3]++;
                            break;
                        case Element.MANUSCRIPT:
                            numOfCornerResources[4]++;
                            break;
                        case Element.PLANT:
                            numOfCornerResources[5]++;
                            break;
                        case Element.QUILL:
                            numOfCornerResources[6]++;
                            break;
                        case null:
                            numOfCornerResources[7]++;
                    }
                }
            }
            
            for(Element e : card.getValidSide().getCenter())
            {
                switch(e) {
                    case Element.ANIMAL:
                        numOfCentreResources[0]++;
                        break;
                    case Element.FUNGUS:
                        numOfCentreResources[1]++;
                        break;
                    case Element.INKWELL:
                        numOfCentreResources[2]++;
                        break;
                    case Element.INSECT:
                        numOfCentreResources[3]++;
                        break;
                    case Element.MANUSCRIPT:
                        numOfCentreResources[4]++;
                        break;
                    case Element.PLANT:
                        numOfCentreResources[5]++;
                        break;
                    case Element.QUILL:
                        numOfCentreResources[6]++;
                        break;
                }
            }
        }

        assertEquals(7, numOfCornerResources[0]); //animal
        assertEquals(7, numOfCornerResources[1]); //fungus
        assertEquals(0, numOfCornerResources[2]); //inkwell
        assertEquals(7, numOfCornerResources[3]); //insect
        assertEquals(0, numOfCornerResources[4]); //manuscript
        assertEquals(7, numOfCornerResources[5]); //plant
        assertEquals(0, numOfCornerResources[6]); //quill
        assertEquals(16, numOfCornerResources[7]); //empty corners
        assertEquals(4, numOfCornerResources[8]); //hidden corners

        assertEquals(3, numOfCentreResources[0]); //animal
        assertEquals(3, numOfCentreResources[1]); //fungus
        assertEquals(0, numOfCentreResources[2]); //inkwell
        assertEquals(3, numOfCentreResources[3]); //insect
        assertEquals(0, numOfCentreResources[4]); //manuscript
        assertEquals(3, numOfCentreResources[5]); //plant
        assertEquals(0, numOfCentreResources[6]); //quill
    }
}