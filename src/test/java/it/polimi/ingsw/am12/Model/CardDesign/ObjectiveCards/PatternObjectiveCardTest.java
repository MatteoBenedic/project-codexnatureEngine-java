package it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards;

import it.polimi.ingsw.am12.Model.CardDesign.GameCard.GameCard;
import it.polimi.ingsw.am12.Model.Logic.InvalidPlacementException;
import it.polimi.ingsw.am12.Model.Logic.PlayingGrid;
import it.polimi.ingsw.am12.Utils.JSONParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatternObjectiveCardTest {

    @Test
    public void PatternObjectiveCard() throws IllegalRequirementsException {


        //Constructor test 0: a valid pattern objective card
        CoordinateSubmatrix[] obj86Coord = new CoordinateSubmatrix[3];
        obj86Coord[0]= new CoordinateSubmatrix(2,0, "red");
        obj86Coord[1]= new CoordinateSubmatrix(1,1, "red");
        obj86Coord[2]= new CoordinateSubmatrix(0,2, "red");
        PatternObjectiveCard objcard86 = new PatternObjectiveCard(obj86Coord, 86, 2);
        objcard86.calculateDeltas();
        //checking coordinate values for this valid pattern objective card
        assertEquals(2, objcard86.getCoordReqPattern(0).getX());
        assertEquals(0, objcard86.getCoordReqPattern(0).getY());
        assertEquals(1, objcard86.getCoordReqPattern(1).getX());
        assertEquals(1, objcard86.getCoordReqPattern(1).getY());
        assertEquals(0, objcard86.getCoordReqPattern(2).getX());
        assertEquals(2, objcard86.getCoordReqPattern(2).getY());


        //Constructor test: invalid pattern objective card, wrong number of coordinates
        CoordinateSubmatrix[] obj1Coord = new CoordinateSubmatrix[4];
        obj1Coord[0]= new CoordinateSubmatrix(2,0, "red");
        obj1Coord[1]= new CoordinateSubmatrix(1,1, "red");
        obj1Coord[2]= new CoordinateSubmatrix(0,2, "red");
        obj1Coord[3]= new CoordinateSubmatrix(2,2,"blue");
        assertThrows(IllegalRequirementsException.class, ()-> new PatternObjectiveCard(obj1Coord, 0, 2));

    }

    @Test
    void getCoordReqPattern() throws IllegalRequirementsException {

        CoordinateSubmatrix[] obj86Coord = new CoordinateSubmatrix[3];
        obj86Coord[0]= new CoordinateSubmatrix(2,0, "blue");
        obj86Coord[1]= new CoordinateSubmatrix(1,1, "blue");
        obj86Coord[2]= new CoordinateSubmatrix(0,2, "blue");
        PatternObjectiveCard objcard88 = new PatternObjectiveCard(obj86Coord, 88, 2);
        objcard88.calculateDeltas();


        objcard88.calculateDeltas();

        CoordinateSubmatrix coord88first = new CoordinateSubmatrix(2,0,"blue");
        CoordinateSubmatrix coord88second = new CoordinateSubmatrix(1,1,"blue");
        CoordinateSubmatrix coord88third = new CoordinateSubmatrix(0,2,"blue");

        assertEquals(coord88first.getX(), objcard88.getCoordReqPattern(0).getX());
        assertEquals(coord88first.getY(), objcard88.getCoordReqPattern(0).getY());
        assertEquals(coord88first.getColour(), objcard88.getCoordReqPattern(0).getColour());

        assertEquals(coord88second.getX(), objcard88.getCoordReqPattern(1).getX());
        assertEquals(coord88second.getY(), objcard88.getCoordReqPattern(1).getY());
        assertEquals(coord88second.getColour(), objcard88.getCoordReqPattern(1).getColour());


        assertEquals(coord88third.getX(), objcard88.getCoordReqPattern(2).getX());
        assertEquals(coord88third.getY(), objcard88.getCoordReqPattern(2).getY());
        assertEquals(coord88third.getColour(), objcard88.getCoordReqPattern(2).getColour());

    }


    @Test
    void calculatePointsC0() throws IllegalRequirementsException, InvalidPlacementException {
        //Constructor
        CoordinateSubmatrix[] obj86Coord = new CoordinateSubmatrix[3];
        obj86Coord[0]= new CoordinateSubmatrix(2,0, "red");
        obj86Coord[1]= new CoordinateSubmatrix(1,1, "red");
        obj86Coord[2]= new CoordinateSubmatrix(0,2, "red");
        ObjectiveCard objcard86 = new PatternObjectiveCard(obj86Coord, 86, 2);
        objcard86.calculateDeltas();

        JSONParser p = new JSONParser();
        List<GameCard> resourcecards = p.parseResourceCards();
        List<GameCard> goldcards = p.parseResourceCards();
        for(GameCard resourceCard : resourcecards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldcards) {
            goldCard.setValidSide(false);
        }

        //Case 0: see attached documentation
        PlayingGrid plcards0= new PlayingGrid();
        plcards0.placeStartCard(0,3, resourcecards.get(0));
        plcards0.placeStartCard(1,2, goldcards.get(1));
        plcards0.placeStartCard(2,1, resourcecards.get(2));
        plcards0.placeStartCard(3,0, resourcecards.get(3));

        assertEquals(2, objcard86.calculatePoints(plcards0));

    }

    @Test
    void calculatePointsC1() throws IllegalRequirementsException, InvalidPlacementException {
        //Constructor
        CoordinateSubmatrix[] obj86Coord = new CoordinateSubmatrix[3];
        obj86Coord[0]= new CoordinateSubmatrix(2,0, "red");
        obj86Coord[1]= new CoordinateSubmatrix(1,1, "red");
        obj86Coord[2]= new CoordinateSubmatrix(0,2, "red");
        ObjectiveCard objcard86 = new PatternObjectiveCard(obj86Coord, 86, 2);
        objcard86.calculateDeltas();

        JSONParser p = new JSONParser();
        List<GameCard> resourcecards = p.parseResourceCards();
        List<GameCard> goldcards = p.parseResourceCards();
        for(GameCard resourceCard : resourcecards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldcards) {
            goldCard.setValidSide(false);
        }

        //Case 1: see attached documentation
        PlayingGrid plcards1= new PlayingGrid();
        plcards1.placeStartCard(0,5, resourcecards.get(0));
        plcards1.placeStartCard(1,4, goldcards.get(1));
        plcards1.placeStartCard(2,3, resourcecards.get(2));
        plcards1.placeStartCard(3,2, goldcards.get(3));
        plcards1.placeStartCard(4,1, resourcecards.get(3));
        plcards1.placeStartCard(5,0, resourcecards.get(5));

        assertEquals(4, objcard86.calculatePoints(plcards1));

    }

    @Test
    void calculatePointsC2() throws IllegalRequirementsException, InvalidPlacementException {
        //Constructor
        CoordinateSubmatrix[] obj86Coord = new CoordinateSubmatrix[3];
        obj86Coord[0]= new CoordinateSubmatrix(2,0, "red");
        obj86Coord[1]= new CoordinateSubmatrix(1,1, "red");
        obj86Coord[2]= new CoordinateSubmatrix(0,2, "red");
        ObjectiveCard objcard86 = new PatternObjectiveCard(obj86Coord, 86, 2);
        objcard86.calculateDeltas();

        JSONParser p = new JSONParser();
        List<GameCard> resourcecards = p.parseResourceCards();
        List<GameCard> goldcards = p.parseResourceCards();
        for(GameCard resourceCard : resourcecards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldcards) {
            goldCard.setValidSide(false);
        }

        //Case 2: see attached documentation
        PlayingGrid plcards2= new PlayingGrid();
        plcards2.placeStartCard(0,5, resourcecards.get(0));
        plcards2.placeStartCard(1,4, goldcards.get(1));
        plcards2.placeStartCard(2,3, resourcecards.get(2));
        plcards2.placeStartCard(3,2, goldcards.get(3));
        plcards2.placeStartCard(4,1, resourcecards.get(3));
        plcards2.placeStartCard(5,0, resourcecards.get(4));
        plcards2.placeStartCard(6,13, resourcecards.get(5));
        plcards2.placeStartCard(7,12, goldcards.get(2));
        plcards2.placeStartCard(8,11, resourcecards.get(6));
        plcards2.placeStartCard(11,20, resourcecards.get(7));
        plcards2.placeStartCard(12,19, resourcecards.get(8));
        plcards2.placeStartCard(18,23, goldcards.get(7));
        plcards2.placeStartCard(18,24, goldcards.get(9));

        assertEquals(6, objcard86.calculatePoints(plcards2));

    }

    @Test
    void calculatePointsC3() throws IllegalRequirementsException, InvalidPlacementException {
        //Constructor
        CoordinateSubmatrix[] obj91Coord = new CoordinateSubmatrix[3];
        obj91Coord[0]= new CoordinateSubmatrix(1,0, "green");
        obj91Coord[1]= new CoordinateSubmatrix(1,2, "green");
        obj91Coord[2]= new CoordinateSubmatrix(0,3,"purple");
        ObjectiveCard objcard91 = new PatternObjectiveCard(obj91Coord, 91, 3);
        objcard91.calculateDeltas();

        JSONParser p = new JSONParser();
        List<GameCard> resourcecards = p.parseResourceCards();
        List<GameCard> goldcards = p.parseResourceCards();
        for(GameCard resourceCard : resourcecards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldcards) {
            goldCard.setValidSide(false);
        }

        //Case 3: see attached documentation
        PlayingGrid plcards3= new PlayingGrid();
        plcards3.placeStartCard(1,4, goldcards.get(16));
        plcards3.placeStartCard(2,4, goldcards.get(17));
        plcards3.placeStartCard(3,4, goldcards.get(18));
        plcards3.placeStartCard(4,3, resourcecards.get(31));
        plcards3.placeStartCard(4,4, resourcecards.get(14));
        plcards3.placeStartCard(5,3, resourcecards.get(32));

        assertEquals(6, objcard91.calculatePoints(plcards3));

    }

    @Test
    void calculatePointsC4() throws IllegalRequirementsException, InvalidPlacementException {
        //Constructor
        CoordinateSubmatrix[] obj86Coord = new CoordinateSubmatrix[3];
        obj86Coord[0]= new CoordinateSubmatrix(2,0, "red");
        obj86Coord[1]= new CoordinateSubmatrix(1,1, "red");
        obj86Coord[2]= new CoordinateSubmatrix(0,2, "red");
        ObjectiveCard objcard86 = new PatternObjectiveCard(obj86Coord, 86, 2);
        objcard86.calculateDeltas();

        JSONParser p = new JSONParser();
        List<GameCard> resourcecards = p.parseResourceCards();
        List<GameCard> goldcards = p.parseResourceCards();
        List<GameCard> startcards = p.parseStartCards();
        for(GameCard resourceCard : resourcecards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldcards) {
            goldCard.setValidSide(false);
        }
        for(GameCard startCard : startcards) {
            startCard.setValidSide(false);
        }

        //Case 4: see attached documentation
        PlayingGrid plcards4= new PlayingGrid();
        plcards4.placeStartCard(0,5, resourcecards.get(0));
        plcards4.placeStartCard(1,4, goldcards.get(1));
        plcards4.placeStartCard(2,3, resourcecards.get(2));
        plcards4.placeStartCard(3,2, goldcards.get(3));
        plcards4.placeStartCard(4,1, resourcecards.get(3));
        plcards4.placeStartCard(5,0, resourcecards.get(4));
        plcards4.placeStartCard(6,13, resourcecards.get(5));
        plcards4.placeStartCard(7,12, goldcards.get(2));
        plcards4.placeStartCard(8,11, startcards.get(0));
        plcards4.placeStartCard(11,20, resourcecards.get(7));
        plcards4.placeStartCard(12,19, resourcecards.get(8));
        plcards4.placeStartCard(18,23, goldcards.get(7));
        plcards4.placeStartCard(18,24, goldcards.get(9));

        assertEquals(4, objcard86.calculatePoints(plcards4));

    }

    @Test
    void calculatePointsC5() throws IllegalRequirementsException, InvalidPlacementException {
        //Constructor
        CoordinateSubmatrix[] obj86Coord = new CoordinateSubmatrix[3];
        obj86Coord[0] = new CoordinateSubmatrix(2,0,"red");
        obj86Coord[1] = new CoordinateSubmatrix(1,1, "red");
        obj86Coord[2] = new CoordinateSubmatrix(0,2, "red");
        ObjectiveCard objcard86 = new PatternObjectiveCard(obj86Coord, 86, 2);
        objcard86.calculateDeltas();

        CoordinateSubmatrix[] obj90Coord = new CoordinateSubmatrix[3];
        obj90Coord[0] = new CoordinateSubmatrix(0,0,"red");
        obj90Coord[1] = new CoordinateSubmatrix(0,2,"red");
        obj90Coord[2] = new CoordinateSubmatrix(1,3, "green");
        PatternObjectiveCard objcard90 = new PatternObjectiveCard(obj90Coord, 90, 3);
        objcard90.calculateDeltas();

        JSONParser p = new JSONParser();
        List<GameCard> resourcecards = p.parseResourceCards();
        List<GameCard> goldcards = p.parseResourceCards();
        List<GameCard> startcards = p.parseStartCards();
        for(GameCard resourceCard : resourcecards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldcards) {
            goldCard.setValidSide(false);
        }
        for(GameCard startCard : startcards) {
            startCard.setValidSide(false);
        }


        PlayingGrid plcards5 = new PlayingGrid();
        plcards5.placeStartCard(0,5, resourcecards.get(0));
        plcards5.placeStartCard(1,4, goldcards.get(1));
        plcards5.placeStartCard(2,3, resourcecards.get(2));
        plcards5.placeStartCard(3,2, goldcards.get(3));
        plcards5.placeStartCard(3,4, resourcecards.get(4));
        plcards5.placeStartCard(4,1, resourcecards.get(5));
        plcards5.placeStartCard(4,5, resourcecards.get(12));
        plcards5.placeStartCard(5,0, resourcecards.get(6));
        plcards5.placeStartCard(5,2, resourcecards.get(7));
        plcards5.placeStartCard(5,14, goldcards.get(5));
        plcards5.placeStartCard(6,3, resourcecards.get(15));
        plcards5.placeStartCard(6,13, resourcecards.get(8));
        plcards5.placeStartCard(7,12, goldcards.get(2));
        plcards5.placeStartCard(8,11, startcards.get(0));
        plcards5.placeStartCard(9,12, goldcards.get(6));
        plcards5.placeStartCard(10,11, goldcards.get(7));
        plcards5.placeStartCard(10,13, goldcards.get(18));
        plcards5.placeStartCard(11,20, resourcecards.get(9));
        plcards5.placeStartCard(12,19, resourcecards.get(8));
        plcards5.placeStartCard(18,23, goldcards.get(8));
        plcards5.placeStartCard(18,24, goldcards.get(9));

        assertEquals(6, objcard86.calculatePoints(plcards5));
        assertEquals(9, objcard90.calculatePoints(plcards5));

    }

    @Test
    void calculatePointsC6() throws IllegalRequirementsException, InvalidPlacementException {
        //Constructor
        CoordinateSubmatrix[] obj86Coord = new CoordinateSubmatrix[3];
        obj86Coord[0] = new CoordinateSubmatrix(2,0,"red");
        obj86Coord[1] = new CoordinateSubmatrix(1,1, "red");
        obj86Coord[2] = new CoordinateSubmatrix(0,2, "red");
        ObjectiveCard objcard86 = new PatternObjectiveCard(obj86Coord, 86, 2);
        objcard86.calculateDeltas();

        CoordinateSubmatrix[] obj90Coord = new CoordinateSubmatrix[3];
        obj90Coord[0] = new CoordinateSubmatrix(0,0,"red");
        obj90Coord[1] = new CoordinateSubmatrix(0,2,"red");
        obj90Coord[2] = new CoordinateSubmatrix(1,3, "green");
        PatternObjectiveCard objcard90 = new PatternObjectiveCard(obj90Coord, 90, 3);
        objcard90.calculateDeltas();

        JSONParser p = new JSONParser();
        List<GameCard> resourcecards = p.parseResourceCards();
        List<GameCard> goldcards = p.parseResourceCards();
        List<GameCard> startcards = p.parseStartCards();
        for(GameCard resourceCard : resourcecards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldcards) {
            goldCard.setValidSide(false);
        }
        for(GameCard startCard : startcards) {
            startCard.setValidSide(false);
        }

        PlayingGrid plcards5 = new PlayingGrid();
        plcards5.placeStartCard(5,8, resourcecards.get(0));
        plcards5.placeStartCard(5,14, goldcards.get(1));
        plcards5.placeStartCard(6,9, resourcecards.get(2));
        plcards5.placeStartCard(6,13, goldcards.get(3));
        plcards5.placeStartCard(7,10, resourcecards.get(4));
        plcards5.placeStartCard(7,12, resourcecards.get(5));
        plcards5.placeStartCard(8,11, startcards.get(0));
        plcards5.placeStartCard(9,10, resourcecards.get(6));
        plcards5.placeStartCard(9,12, resourcecards.get(7));
        plcards5.placeStartCard(10,9, resourcecards.get(15));
        plcards5.placeStartCard(10,13, goldcards.get(18));

        assertEquals(2, objcard86.calculatePoints(plcards5));
        assertEquals(3, objcard90.calculatePoints(plcards5));

    }


    @Test
    void calculatePointsP0() throws InvalidPlacementException {
        //Parser
        JSONParser p = new JSONParser();
        List<GameCard> resourcecards = p.parseResourceCards();
        List<GameCard> goldcards = p.parseResourceCards();
        List<ObjectiveCard> objectivecards = p.parseObjectiveCards();
        for(GameCard resourceCard : resourcecards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldcards) {
            goldCard.setValidSide(false);
        }
        for(ObjectiveCard objectiveCard : objectivecards) {
            objectiveCard.calculateDeltas();
        }

        //Case 0: see attached documentation
        PlayingGrid plcardsp0= new PlayingGrid();
        ObjectiveCard objcard86 = objectivecards.get(0);
        plcardsp0.placeStartCard(0,3, resourcecards.get(0));
        plcardsp0.placeStartCard(1,2, goldcards.get(1));
        plcardsp0.placeStartCard(2,1, resourcecards.get(2));
        plcardsp0.placeStartCard(3,0, resourcecards.get(3));

        assertEquals(2, objcard86.calculatePoints(plcardsp0));

    }

    @Test
    void calculatePointsP1() throws InvalidPlacementException {
        //Parser
        JSONParser p = new JSONParser();
        List<GameCard> resourcecards = p.parseResourceCards();
        List<GameCard> goldcards = p.parseResourceCards();
        List<ObjectiveCard> objectivecards = p.parseObjectiveCards();
        for(GameCard resourceCard : resourcecards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldcards) {
            goldCard.setValidSide(false);
        }
        for(ObjectiveCard objectiveCard : objectivecards) {
            objectiveCard.calculateDeltas();
        }

        //Case 1: see attached documentation
        PlayingGrid plcards1= new PlayingGrid();
        ObjectiveCard objcard86 = objectivecards.get(0);
        plcards1.placeStartCard(0,5, resourcecards.get(0));
        plcards1.placeStartCard(1,4, goldcards.get(1));
        plcards1.placeStartCard(2,3, resourcecards.get(2));
        plcards1.placeStartCard(3,2, goldcards.get(3));
        plcards1.placeStartCard(4,1, resourcecards.get(3));
        plcards1.placeStartCard(5,0, resourcecards.get(5));

        assertEquals(4, objcard86.calculatePoints(plcards1));

    }

    @Test
    void calculatePointsP2() throws InvalidPlacementException {
        //Parser
        JSONParser p = new JSONParser();
        List<GameCard> resourcecards = p.parseResourceCards();
        List<GameCard> goldcards = p.parseResourceCards();
        List<ObjectiveCard> objectivecards = p.parseObjectiveCards();
        for(GameCard resourceCard : resourcecards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldcards) {
            goldCard.setValidSide(false);
        }
        for(ObjectiveCard objectiveCard : objectivecards) {
            objectiveCard.calculateDeltas();
        }

        //Case 2: see attached documentation
        PlayingGrid plcards2= new PlayingGrid();
        ObjectiveCard objcard86 = objectivecards.get(0);
        plcards2.placeStartCard(0,5, resourcecards.get(0));
        plcards2.placeStartCard(1,4, goldcards.get(1));
        plcards2.placeStartCard(2,3, resourcecards.get(2));
        plcards2.placeStartCard(3,2, goldcards.get(3));
        plcards2.placeStartCard(4,1, resourcecards.get(3));
        plcards2.placeStartCard(5,0, resourcecards.get(4));
        plcards2.placeStartCard(6,13, resourcecards.get(5));
        plcards2.placeStartCard(7,12, goldcards.get(2));
        plcards2.placeStartCard(8,11, resourcecards.get(6));
        plcards2.placeStartCard(11,20, resourcecards.get(7));
        plcards2.placeStartCard(12,19, resourcecards.get(8));
        plcards2.placeStartCard(18,23, goldcards.get(7));
        plcards2.placeStartCard(18,24, goldcards.get(9));

        assertEquals(6, objcard86.calculatePoints(plcards2));

    }

    @Test
    void calculatePointsP3() throws InvalidPlacementException {
        //Parser
        JSONParser p = new JSONParser();
        List<GameCard> resourcecards = p.parseResourceCards();
        List<GameCard> goldcards = p.parseResourceCards();
        List<ObjectiveCard> objectivecards = p.parseObjectiveCards();
        for(GameCard resourceCard : resourcecards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldcards) {
            goldCard.setValidSide(false);
        }
        for(ObjectiveCard objectiveCard : objectivecards) {
            objectiveCard.calculateDeltas();
        }

        //Case 3: see attached documentation
        PlayingGrid plcards3= new PlayingGrid();
        ObjectiveCard objcard91 = objectivecards.get(5);
        plcards3.placeStartCard(1,4, goldcards.get(16));
        plcards3.placeStartCard(2,4, goldcards.get(17));
        plcards3.placeStartCard(3,4, goldcards.get(18));
        plcards3.placeStartCard(4,3, resourcecards.get(31));
        plcards3.placeStartCard(4,4, resourcecards.get(14));
        plcards3.placeStartCard(5,3, resourcecards.get(32));

        assertEquals(6, objcard91.calculatePoints(plcards3));


    }

    @Test
    void calculatePointsP4() throws InvalidPlacementException {
        //Parser
        JSONParser p = new JSONParser();
        List<GameCard> resourcecards = p.parseResourceCards();
        List<GameCard> goldcards = p.parseResourceCards();
        List<GameCard> startcards = p.parseStartCards();
        List<ObjectiveCard> objectivecards = p.parseObjectiveCards();
        for(GameCard resourceCard : resourcecards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldcards) {
            goldCard.setValidSide(false);
        }
        for(GameCard startCard : startcards) {
            startCard.setValidSide(false);
        }
        for(ObjectiveCard objectiveCard : objectivecards) {
            objectiveCard.calculateDeltas();
        }

        //Case 4: see attached documentation
        PlayingGrid plcards4= new PlayingGrid();
        ObjectiveCard objcard86 = objectivecards.get(0);
        plcards4.placeStartCard(0,5, resourcecards.get(0));
        plcards4.placeStartCard(1,4, goldcards.get(1));
        plcards4.placeStartCard(2,3, resourcecards.get(2));
        plcards4.placeStartCard(3,2, goldcards.get(3));
        plcards4.placeStartCard(4,1, resourcecards.get(3));
        plcards4.placeStartCard(5,0, resourcecards.get(4));
        plcards4.placeStartCard(6,13, resourcecards.get(5));
        plcards4.placeStartCard(7,12, goldcards.get(2));
        plcards4.placeStartCard(8,11, startcards.get(0));
        plcards4.placeStartCard(11,20, resourcecards.get(7));
        plcards4.placeStartCard(12,19, resourcecards.get(8));
        plcards4.placeStartCard(18,23, goldcards.get(7));
        plcards4.placeStartCard(18,24, goldcards.get(9));

        assertEquals(4, objcard86.calculatePoints(plcards4));

    }

    @Test
    void calculatePointsP5() throws InvalidPlacementException {
        //Parser
        JSONParser p = new JSONParser();
        List<GameCard> resourcecards = p.parseResourceCards();
        List<GameCard> goldcards = p.parseResourceCards();
        List<GameCard> startcards = p.parseStartCards();
        List<ObjectiveCard> objectivecards = p.parseObjectiveCards();
        for(GameCard resourceCard : resourcecards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldcards) {
            goldCard.setValidSide(false);
        }
        for(GameCard startCard : startcards) {
            startCard.setValidSide(false);
        }
        for(ObjectiveCard objectiveCard : objectivecards) {
            objectiveCard.calculateDeltas();
        }

        PlayingGrid plcards5 = new PlayingGrid();
        ObjectiveCard objcard86 = objectivecards.get(0);
        ObjectiveCard objcard90 = objectivecards.get(4);
        plcards5.placeStartCard(0,5, resourcecards.get(0));
        plcards5.placeStartCard(1,4, goldcards.get(1));
        plcards5.placeStartCard(2,3, resourcecards.get(2));
        plcards5.placeStartCard(3,2, goldcards.get(3));
        plcards5.placeStartCard(3,4, resourcecards.get(4));
        plcards5.placeStartCard(4,1, resourcecards.get(5));
        plcards5.placeStartCard(4,5, resourcecards.get(12));
        plcards5.placeStartCard(5,0, resourcecards.get(6));
        plcards5.placeStartCard(5,2, resourcecards.get(7));
        plcards5.placeStartCard(5,14, goldcards.get(5));
        plcards5.placeStartCard(6,3, resourcecards.get(15));
        plcards5.placeStartCard(6,13, resourcecards.get(8));
        plcards5.placeStartCard(7,12, goldcards.get(2));
        plcards5.placeStartCard(8,11, startcards.get(0));
        plcards5.placeStartCard(9,12, goldcards.get(6));
        plcards5.placeStartCard(10,11, goldcards.get(7));
        plcards5.placeStartCard(10,13, goldcards.get(18));
        plcards5.placeStartCard(11,20, resourcecards.get(9));
        plcards5.placeStartCard(12,19, resourcecards.get(8));
        plcards5.placeStartCard(18,23, goldcards.get(8));
        plcards5.placeStartCard(18,24, goldcards.get(9));

        assertEquals(6, objcard86.calculatePoints(plcards5));
        assertEquals(9, objcard90.calculatePoints(plcards5));


    }

    @Test
    void calculatePointsP6() throws InvalidPlacementException {
        //Parser
        JSONParser p = new JSONParser();
        List<GameCard> resourcecards = p.parseResourceCards();
        List<GameCard> goldcards = p.parseResourceCards();
        List<GameCard> startcards = p.parseStartCards();
        List<ObjectiveCard> objectivecards = p.parseObjectiveCards();
        for(GameCard resourceCard : resourcecards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldcards) {
            goldCard.setValidSide(false);
        }
        for(GameCard startCard : startcards) {
            startCard.setValidSide(false);
        }
        for(ObjectiveCard objectiveCard : objectivecards) {
            objectiveCard.calculateDeltas();
        }

        PlayingGrid plcards5 = new PlayingGrid();
        ObjectiveCard objcard86 = objectivecards.get(0);
        ObjectiveCard objcard90 = objectivecards.get(4);
        plcards5.placeStartCard(5,8, resourcecards.get(0));
        plcards5.placeStartCard(5,14, goldcards.get(1));
        plcards5.placeStartCard(6,9, resourcecards.get(2));
        plcards5.placeStartCard(6,13, goldcards.get(3));
        plcards5.placeStartCard(7,10, resourcecards.get(4));
        plcards5.placeStartCard(7,12, resourcecards.get(5));
        plcards5.placeStartCard(8,11, startcards.get(0));
        plcards5.placeStartCard(9,10, resourcecards.get(6));
        plcards5.placeStartCard(9,12, resourcecards.get(7));
        plcards5.placeStartCard(10,9, resourcecards.get(15));
        plcards5.placeStartCard(10,13, goldcards.get(18));

        assertEquals(2, objcard86.calculatePoints(plcards5));
        assertEquals(3, objcard90.calculatePoints(plcards5));

    }


}