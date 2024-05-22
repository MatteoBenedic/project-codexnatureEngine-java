package it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards;

import it.polimi.ingsw.am12.Exceptions.IllegalRequirementsException;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.GameCard;
import it.polimi.ingsw.am12.Exceptions.InvalidPlacementException;
import it.polimi.ingsw.am12.Model.Logic.PlayingGrid;
import it.polimi.ingsw.am12.Utils.JSONParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatternObjectiveCardTest {

    @Test
    public void PatternObjectiveCard() throws IllegalRequirementsException {
        //Constructor test: a valid pattern objective card
        CoordinateSubmatrix[] coordObj86 = new CoordinateSubmatrix[3];
        coordObj86[0]= new CoordinateSubmatrix(2,0, CardColour.RED);
        coordObj86[1]= new CoordinateSubmatrix(1,1, CardColour.RED);
        coordObj86[2]= new CoordinateSubmatrix(0,2, CardColour.RED);
        PatternObjectiveCard objCard86 = new PatternObjectiveCard(coordObj86, 86, 2);
        objCard86.calculateDeltas();
        //Checking coordinate values for this valid pattern objective card
        assertEquals(2, objCard86.getCoordReqPattern(0).getX());
        assertEquals(0, objCard86.getCoordReqPattern(0).getY());
        assertEquals(1, objCard86.getCoordReqPattern(1).getX());
        assertEquals(1, objCard86.getCoordReqPattern(1).getY());
        assertEquals(0, objCard86.getCoordReqPattern(2).getX());
        assertEquals(2, objCard86.getCoordReqPattern(2).getY());
        //Constructor test: invalid pattern objective card (wrong number of coordinates)
        CoordinateSubmatrix[] obj1coord = new CoordinateSubmatrix[4];
        obj1coord[0]= new CoordinateSubmatrix(2,0, CardColour.RED);
        obj1coord[1]= new CoordinateSubmatrix(1,1, CardColour.RED);
        obj1coord[2]= new CoordinateSubmatrix(0,2, CardColour.RED);
        obj1coord[3]= new CoordinateSubmatrix(2,2,CardColour.BLUE);
        assertThrows(IllegalRequirementsException.class, ()-> new PatternObjectiveCard(obj1coord, 0, 2));
    }


    @Test
    void getCoordReqPattern() throws IllegalRequirementsException {
        CoordinateSubmatrix[] coordObj86 = new CoordinateSubmatrix[3];
        coordObj86[0]= new CoordinateSubmatrix(2,0, CardColour.BLUE);
        coordObj86[1]= new CoordinateSubmatrix(1,1, CardColour.BLUE);
        coordObj86[2]= new CoordinateSubmatrix(0,2, CardColour.BLUE);
        PatternObjectiveCard objCard88 = new PatternObjectiveCard(coordObj86, 88, 2);
        objCard88.calculateDeltas();
        CoordinateSubmatrix coord88First = new CoordinateSubmatrix(2,0,CardColour.BLUE);
        CoordinateSubmatrix coord88Second = new CoordinateSubmatrix(1,1,CardColour.BLUE);
        CoordinateSubmatrix coord88Third = new CoordinateSubmatrix(0,2,CardColour.BLUE);

        assertEquals(coord88First.getX(), objCard88.getCoordReqPattern(0).getX());
        assertEquals(coord88First.getY(), objCard88.getCoordReqPattern(0).getY());
        assertEquals(coord88First.getColour(), objCard88.getCoordReqPattern(0).getColour());

        assertEquals(coord88Second.getX(), objCard88.getCoordReqPattern(1).getX());
        assertEquals(coord88Second.getY(), objCard88.getCoordReqPattern(1).getY());
        assertEquals(coord88Second.getColour(), objCard88.getCoordReqPattern(1).getColour());

        assertEquals(coord88Third.getX(), objCard88.getCoordReqPattern(2).getX());
        assertEquals(coord88Third.getY(), objCard88.getCoordReqPattern(2).getY());
        assertEquals(coord88Third.getColour(), objCard88.getCoordReqPattern(2).getColour());
    }


    @Test
    void calculatePointsConstr0() throws IllegalRequirementsException, InvalidPlacementException {
        //Constructor for the objective card
        CoordinateSubmatrix[] coordObj86 = new CoordinateSubmatrix[3];
        coordObj86[0]= new CoordinateSubmatrix(2,0, CardColour.RED);
        coordObj86[1]= new CoordinateSubmatrix(1,1, CardColour.RED);
        coordObj86[2]= new CoordinateSubmatrix(0,2, CardColour.RED);
        ObjectiveCard objCard86 = new PatternObjectiveCard(coordObj86, 86, 2);
        objCard86.calculateDeltas();

        JSONParser parser = new JSONParser();
        List<GameCard> resourceCards = parser.parseResourceCards();
        List<GameCard> goldCards = parser.parseResourceCards();
        for(GameCard resourceCard : resourceCards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldCards) {
            goldCard.setValidSide(false);
        }
        //Case Test 0: see attached documentation
        PlayingGrid grid0= new PlayingGrid();
        grid0.placeStartCard(0,3, resourceCards.get(0));
        grid0.placeStartCard(1,2, goldCards.get(1));
        grid0.placeStartCard(2,1, resourceCards.get(2));
        grid0.placeStartCard(3,0, resourceCards.get(3));

        assertEquals(2, objCard86.calculatePoints(grid0));

    }

    @Test
    void calculatePointsConstr1() throws IllegalRequirementsException, InvalidPlacementException {
        //Constructor for the objective card
        CoordinateSubmatrix[] coordObj86 = new CoordinateSubmatrix[3];
        coordObj86[0]= new CoordinateSubmatrix(2,0, CardColour.RED);
        coordObj86[1]= new CoordinateSubmatrix(1,1, CardColour.RED);
        coordObj86[2]= new CoordinateSubmatrix(0,2, CardColour.RED);
        ObjectiveCard objCard86 = new PatternObjectiveCard(coordObj86, 86, 2);
        objCard86.calculateDeltas();

        JSONParser parser = new JSONParser();
        List<GameCard> resourceCards = parser.parseResourceCards();
        List<GameCard> goldCards = parser.parseResourceCards();
        for(GameCard resourceCard : resourceCards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldCards) {
            goldCard.setValidSide(false);
        }

        //Case Test 1: see attached documentation
        PlayingGrid grid1= new PlayingGrid();
        grid1.placeStartCard(0,5, resourceCards.get(0));
        grid1.placeStartCard(1,4, goldCards.get(1));
        grid1.placeStartCard(2,3, resourceCards.get(2));
        grid1.placeStartCard(3,2, goldCards.get(3));
        grid1.placeStartCard(4,1, resourceCards.get(3));
        grid1.placeStartCard(5,0, resourceCards.get(5));

        assertEquals(4, objCard86.calculatePoints(grid1));

    }

    @Test
    void calculatePointsConstr2() throws IllegalRequirementsException, InvalidPlacementException {
        //Constructor for the objective card
        CoordinateSubmatrix[] coordObj86 = new CoordinateSubmatrix[3];
        coordObj86[0]= new CoordinateSubmatrix(2,0, CardColour.RED);
        coordObj86[1]= new CoordinateSubmatrix(1,1, CardColour.RED);
        coordObj86[2]= new CoordinateSubmatrix(0,2, CardColour.RED);
        ObjectiveCard objCard86 = new PatternObjectiveCard(coordObj86, 86, 2);
        objCard86.calculateDeltas();

        JSONParser parser = new JSONParser();
        List<GameCard> resourceCards = parser.parseResourceCards();
        List<GameCard> goldCards = parser.parseResourceCards();
        for(GameCard resourceCard : resourceCards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldCards) {
            goldCard.setValidSide(false);
        }

        //Case Test 2: see attached documentation
        PlayingGrid grid2= new PlayingGrid();
        grid2.placeStartCard(0,5, resourceCards.get(0));
        grid2.placeStartCard(1,4, goldCards.get(1));
        grid2.placeStartCard(2,3, resourceCards.get(2));
        grid2.placeStartCard(3,2, goldCards.get(3));
        grid2.placeStartCard(4,1, resourceCards.get(3));
        grid2.placeStartCard(5,0, resourceCards.get(4));
        grid2.placeStartCard(6,13, resourceCards.get(5));
        grid2.placeStartCard(7,12, goldCards.get(2));
        grid2.placeStartCard(8,11, resourceCards.get(6));
        grid2.placeStartCard(11,20, resourceCards.get(7));
        grid2.placeStartCard(12,19, resourceCards.get(8));
        grid2.placeStartCard(18,23, goldCards.get(7));
        grid2.placeStartCard(18,24, goldCards.get(9));

        assertEquals(6, objCard86.calculatePoints(grid2));

    }

    @Test
    void calculatePointsConstr3() throws IllegalRequirementsException, InvalidPlacementException {
        //Constructor for the objective card
        CoordinateSubmatrix[] coordObj91 = new CoordinateSubmatrix[3];
        coordObj91[0]= new CoordinateSubmatrix(1,0, CardColour.GREEN);
        coordObj91[1]= new CoordinateSubmatrix(1,2, CardColour.GREEN);
        coordObj91[2]= new CoordinateSubmatrix(0,3,CardColour.PURPLE);
        ObjectiveCard objCard91 = new PatternObjectiveCard(coordObj91, 91, 3);
        objCard91.calculateDeltas();

        JSONParser parser = new JSONParser();
        List<GameCard> resourceCards = parser.parseResourceCards();
        List<GameCard> goldCards = parser.parseResourceCards();
        for(GameCard resourceCard : resourceCards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldCards) {
            goldCard.setValidSide(false);
        }
        //Case Test 3: see attached documentation
        PlayingGrid grid3= new PlayingGrid();
        grid3.placeStartCard(1,4, goldCards.get(16));
        grid3.placeStartCard(2,4, goldCards.get(17));
        grid3.placeStartCard(3,4, goldCards.get(18));
        grid3.placeStartCard(4,3, resourceCards.get(31));
        grid3.placeStartCard(4,4, resourceCards.get(14));
        grid3.placeStartCard(5,3, resourceCards.get(32));

        assertEquals(6, objCard91.calculatePoints(grid3));

    }

    @Test
    void calculatePointsConstr4() throws IllegalRequirementsException, InvalidPlacementException {
        //Constructor for the objective card
        CoordinateSubmatrix[] coordObj86 = new CoordinateSubmatrix[3];
        coordObj86[0]= new CoordinateSubmatrix(2,0, CardColour.RED);
        coordObj86[1]= new CoordinateSubmatrix(1,1, CardColour.RED);
        coordObj86[2]= new CoordinateSubmatrix(0,2, CardColour.RED);
        ObjectiveCard objCard86 = new PatternObjectiveCard(coordObj86, 86, 2);
        objCard86.calculateDeltas();

        JSONParser parser = new JSONParser();
        List<GameCard> resourceCards = parser.parseResourceCards();
        List<GameCard> goldCards = parser.parseResourceCards();
        List<GameCard> startCards = parser.parseStartCards();
        for(GameCard resourceCard : resourceCards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldCards) {
            goldCard.setValidSide(false);
        }
        for(GameCard startCard : startCards) {
            startCard.setValidSide(false);
        }

        //Case 4: see attached documentation
        PlayingGrid grid4= new PlayingGrid();
        grid4.placeStartCard(0,5, resourceCards.get(0));
        grid4.placeStartCard(1,4, goldCards.get(1));
        grid4.placeStartCard(2,3, resourceCards.get(2));
        grid4.placeStartCard(3,2, goldCards.get(3));
        grid4.placeStartCard(4,1, resourceCards.get(3));
        grid4.placeStartCard(5,0, resourceCards.get(4));
        grid4.placeStartCard(6,13, resourceCards.get(5));
        grid4.placeStartCard(7,12, goldCards.get(2));
        grid4.placeStartCard(8,11, startCards.get(0));
        grid4.placeStartCard(11,20, resourceCards.get(7));
        grid4.placeStartCard(12,19, resourceCards.get(8));
        grid4.placeStartCard(18,23, goldCards.get(7));
        grid4.placeStartCard(18,24, goldCards.get(9));

        assertEquals(4, objCard86.calculatePoints(grid4));

    }

    @Test
    void calculatePointsConstr5() throws IllegalRequirementsException, InvalidPlacementException {
        //Constructor for the objective card
        CoordinateSubmatrix[] coordObj86 = new CoordinateSubmatrix[3];
        coordObj86[0] = new CoordinateSubmatrix(2,0,CardColour.RED);
        coordObj86[1] = new CoordinateSubmatrix(1,1, CardColour.RED);
        coordObj86[2] = new CoordinateSubmatrix(0,2, CardColour.RED);
        ObjectiveCard objCard86 = new PatternObjectiveCard(coordObj86, 86, 2);
        objCard86.calculateDeltas();

        CoordinateSubmatrix[] coordObj90 = new CoordinateSubmatrix[3];
        coordObj90[0] = new CoordinateSubmatrix(0,0,CardColour.RED);
        coordObj90[1] = new CoordinateSubmatrix(0,2,CardColour.RED);
        coordObj90[2] = new CoordinateSubmatrix(1,3, CardColour.GREEN);
        PatternObjectiveCard objCard90 = new PatternObjectiveCard(coordObj90, 90, 3);
        objCard90.calculateDeltas();

        JSONParser parser = new JSONParser();
        List<GameCard> resourceCards = parser.parseResourceCards();
        List<GameCard> goldCards = parser.parseResourceCards();
        List<GameCard> startCards = parser.parseStartCards();
        for(GameCard resourceCard : resourceCards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldCards) {
            goldCard.setValidSide(false);
        }
        for(GameCard startCard : startCards) {
            startCard.setValidSide(false);
        }

        PlayingGrid grid5 = new PlayingGrid();
        grid5.placeStartCard(0,5, resourceCards.get(0));
        grid5.placeStartCard(1,4, goldCards.get(1));
        grid5.placeStartCard(2,3, resourceCards.get(2));
        grid5.placeStartCard(3,2, goldCards.get(3));
        grid5.placeStartCard(3,4, resourceCards.get(4));
        grid5.placeStartCard(4,1, resourceCards.get(5));
        grid5.placeStartCard(4,5, resourceCards.get(12));
        grid5.placeStartCard(5,0, resourceCards.get(6));
        grid5.placeStartCard(5,2, resourceCards.get(7));
        grid5.placeStartCard(5,14, goldCards.get(5));
        grid5.placeStartCard(6,3, resourceCards.get(15));
        grid5.placeStartCard(6,13, resourceCards.get(8));
        grid5.placeStartCard(7,12, goldCards.get(2));
        grid5.placeStartCard(8,11, startCards.get(0));
        grid5.placeStartCard(9,12, goldCards.get(6));
        grid5.placeStartCard(10,11, goldCards.get(7));
        grid5.placeStartCard(10,13, goldCards.get(18));
        grid5.placeStartCard(11,20, resourceCards.get(9));
        grid5.placeStartCard(12,19, resourceCards.get(8));
        grid5.placeStartCard(18,23, goldCards.get(8));
        grid5.placeStartCard(18,24, goldCards.get(9));

        assertEquals(6, objCard86.calculatePoints(grid5));
        assertEquals(9, objCard90.calculatePoints(grid5));

    }

    @Test
    void calculatePointsConstr6() throws IllegalRequirementsException, InvalidPlacementException {
        //Constructor for the objective card
        CoordinateSubmatrix[] coordObj86 = new CoordinateSubmatrix[3];
        coordObj86[0] = new CoordinateSubmatrix(2,0,CardColour.RED);
        coordObj86[1] = new CoordinateSubmatrix(1,1, CardColour.RED);
        coordObj86[2] = new CoordinateSubmatrix(0,2, CardColour.RED);
        ObjectiveCard objCard86 = new PatternObjectiveCard(coordObj86, 86, 2);
        objCard86.calculateDeltas();

        CoordinateSubmatrix[] coordObj90 = new CoordinateSubmatrix[3];
        coordObj90[0] = new CoordinateSubmatrix(0,0,CardColour.RED);
        coordObj90[1] = new CoordinateSubmatrix(0,2,CardColour.RED);
        coordObj90[2] = new CoordinateSubmatrix(1,3, CardColour.GREEN);
        PatternObjectiveCard objCard90 = new PatternObjectiveCard(coordObj90, 90, 3);
        objCard90.calculateDeltas();

        JSONParser parser = new JSONParser();
        List<GameCard> resourceCards = parser.parseResourceCards();
        List<GameCard> goldCards = parser.parseResourceCards();
        List<GameCard> startCards = parser.parseStartCards();
        for(GameCard resourceCard : resourceCards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldCards) {
            goldCard.setValidSide(false);
        }
        for(GameCard startCard : startCards) {
            startCard.setValidSide(false);
        }

        PlayingGrid grid6 = new PlayingGrid();
        grid6.placeStartCard(5,8, resourceCards.get(0));
        grid6.placeStartCard(5,14, goldCards.get(1));
        grid6.placeStartCard(6,9, resourceCards.get(2));
        grid6.placeStartCard(6,13, goldCards.get(3));
        grid6.placeStartCard(7,10, resourceCards.get(4));
        grid6.placeStartCard(7,12, resourceCards.get(5));
        grid6.placeStartCard(8,11, startCards.get(0));
        grid6.placeStartCard(9,10, resourceCards.get(6));
        grid6.placeStartCard(9,12, resourceCards.get(7));
        grid6.placeStartCard(10,9, resourceCards.get(15));
        grid6.placeStartCard(10,13, goldCards.get(18));

        assertEquals(2, objCard86.calculatePoints(grid6));
        assertEquals(3, objCard90.calculatePoints(grid6));

    }


    @Test
    void calculatePointsPrs0() throws InvalidPlacementException {
        //Parsing the game cards
        JSONParser parser = new JSONParser();
        List<GameCard> resourceCards = parser.parseResourceCards();
        List<GameCard> goldCards = parser.parseResourceCards();
        List<ObjectiveCard> objectiveCards = parser.parseObjectiveCards();
        for(GameCard resourceCard : resourceCards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldCards) {
            goldCard.setValidSide(false);
        }
        for(ObjectiveCard objectiveCard : objectiveCards) {
            objectiveCard.calculateDeltas();
        }

        //Case Test 0: see attached documentation
        PlayingGrid grid0= new PlayingGrid();
        ObjectiveCard objCard86 = objectiveCards.get(0);
        grid0.placeStartCard(0,3, resourceCards.get(0));
        grid0.placeStartCard(1,2, goldCards.get(1));
        grid0.placeStartCard(2,1, resourceCards.get(2));
        grid0.placeStartCard(3,0, resourceCards.get(3));

        assertEquals(2, objCard86.calculatePoints(grid0));

    }

    @Test
    void calculatePointsPrs1() throws InvalidPlacementException {
        //Parsing the game cards
        JSONParser parser = new JSONParser();
        List<GameCard> resourceCards = parser.parseResourceCards();
        List<GameCard> goldCards = parser.parseResourceCards();
        List<ObjectiveCard> objectiveCards = parser.parseObjectiveCards();
        for(GameCard resourceCard : resourceCards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldCards) {
            goldCard.setValidSide(false);
        }
        for(ObjectiveCard objectiveCard : objectiveCards) {
            objectiveCard.calculateDeltas();
        }

        //Case Test 1: see attached documentation
        PlayingGrid grid1= new PlayingGrid();
        ObjectiveCard objCard86 = objectiveCards.get(0);
        grid1.placeStartCard(0,5, resourceCards.get(0));
        grid1.placeStartCard(1,4, goldCards.get(1));
        grid1.placeStartCard(2,3, resourceCards.get(2));
        grid1.placeStartCard(3,2, goldCards.get(3));
        grid1.placeStartCard(4,1, resourceCards.get(3));
        grid1.placeStartCard(5,0, resourceCards.get(5));

        assertEquals(4, objCard86.calculatePoints(grid1));

    }

    @Test
    void calculatePointsPrs2() throws InvalidPlacementException {
        //Parsing the game cards
        JSONParser p = new JSONParser();
        List<GameCard> resourceCards = p.parseResourceCards();
        List<GameCard> goldCards = p.parseResourceCards();
        List<ObjectiveCard> objectiveCards = p.parseObjectiveCards();
        for(GameCard resourceCard : resourceCards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldCards) {
            goldCard.setValidSide(false);
        }
        for(ObjectiveCard objectiveCard : objectiveCards) {
            objectiveCard.calculateDeltas();
        }

        //Case Test 2: see attached documentation
        PlayingGrid grid2= new PlayingGrid();
        ObjectiveCard objCard86 = objectiveCards.get(0);
        grid2.placeStartCard(0,5, resourceCards.get(0));
        grid2.placeStartCard(1,4, goldCards.get(1));
        grid2.placeStartCard(2,3, resourceCards.get(2));
        grid2.placeStartCard(3,2, goldCards.get(3));
        grid2.placeStartCard(4,1, resourceCards.get(3));
        grid2.placeStartCard(5,0, resourceCards.get(4));
        grid2.placeStartCard(6,13, resourceCards.get(5));
        grid2.placeStartCard(7,12, goldCards.get(2));
        grid2.placeStartCard(8,11, resourceCards.get(6));
        grid2.placeStartCard(11,20, resourceCards.get(7));
        grid2.placeStartCard(12,19, resourceCards.get(8));
        grid2.placeStartCard(18,23, goldCards.get(7));
        grid2.placeStartCard(18,24, goldCards.get(9));

        assertEquals(6, objCard86.calculatePoints(grid2));

    }

    @Test
    void calculatePointsPrs3() throws InvalidPlacementException {
        //Parsing the game cards
        JSONParser parser = new JSONParser();
        List<GameCard> resourceCards = parser.parseResourceCards();
        List<GameCard> goldCards = parser.parseResourceCards();
        List<ObjectiveCard> objectiveCards = parser.parseObjectiveCards();
        for(GameCard resourceCard : resourceCards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldCards) {
            goldCard.setValidSide(false);
        }
        for(ObjectiveCard objectiveCard : objectiveCards) {
            objectiveCard.calculateDeltas();
        }

        //Case Test 3: see attached documentation
        PlayingGrid grid3= new PlayingGrid();
        ObjectiveCard objCard91 = objectiveCards.get(5);
        grid3.placeStartCard(1,4, goldCards.get(16));
        grid3.placeStartCard(2,4, goldCards.get(17));
        grid3.placeStartCard(3,4, goldCards.get(18));
        grid3.placeStartCard(4,3, resourceCards.get(31));
        grid3.placeStartCard(4,4, resourceCards.get(14));
        grid3.placeStartCard(5,3, resourceCards.get(32));

        assertEquals(6, objCard91.calculatePoints(grid3));


    }

    @Test
    void calculatePointsPrs4() throws InvalidPlacementException {
        //Parsing the game cards
        JSONParser parser = new JSONParser();
        List<GameCard> resourceCards = parser.parseResourceCards();
        List<GameCard> goldCards = parser.parseResourceCards();
        List<GameCard> startCards = parser.parseStartCards();
        List<ObjectiveCard> objectiveCards = parser.parseObjectiveCards();
        for(GameCard resourceCard : resourceCards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldCards) {
            goldCard.setValidSide(false);
        }
        for(GameCard startCard : startCards) {
            startCard.setValidSide(false);
        }
        for(ObjectiveCard objectiveCard : objectiveCards) {
            objectiveCard.calculateDeltas();
        }

        //Case Test 4: see attached documentation
        PlayingGrid grid4= new PlayingGrid();
        ObjectiveCard objCard86 = objectiveCards.get(0);
        grid4.placeStartCard(0,5, resourceCards.get(0));
        grid4.placeStartCard(1,4, goldCards.get(1));
        grid4.placeStartCard(2,3, resourceCards.get(2));
        grid4.placeStartCard(3,2, goldCards.get(3));
        grid4.placeStartCard(4,1, resourceCards.get(3));
        grid4.placeStartCard(5,0, resourceCards.get(4));
        grid4.placeStartCard(6,13, resourceCards.get(5));
        grid4.placeStartCard(7,12, goldCards.get(2));
        grid4.placeStartCard(8,11, startCards.get(0));
        grid4.placeStartCard(11,20, resourceCards.get(7));
        grid4.placeStartCard(12,19, resourceCards.get(8));
        grid4.placeStartCard(18,23, goldCards.get(7));
        grid4.placeStartCard(18,24, goldCards.get(9));

        assertEquals(4, objCard86.calculatePoints(grid4));

    }

    @Test
    void calculatePointsP5() throws InvalidPlacementException {
        //Parsing the game cards
        JSONParser parser = new JSONParser();
        List<GameCard> resourceCards = parser.parseResourceCards();
        List<GameCard> goldCards = parser.parseResourceCards();
        List<GameCard> startCards = parser.parseStartCards();
        List<ObjectiveCard> objectiveCards = parser.parseObjectiveCards();
        for(GameCard resourceCard : resourceCards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldCards) {
            goldCard.setValidSide(false);
        }
        for(GameCard startCard : startCards) {
            startCard.setValidSide(false);
        }
        for(ObjectiveCard objectiveCard : objectiveCards) {
            objectiveCard.calculateDeltas();
        }

        PlayingGrid grid5 = new PlayingGrid();
        ObjectiveCard objCard86 = objectiveCards.get(0);
        ObjectiveCard objCard90 = objectiveCards.get(4);
        grid5.placeStartCard(0,5, resourceCards.get(0));
        grid5.placeStartCard(1,4, goldCards.get(1));
        grid5.placeStartCard(2,3, resourceCards.get(2));
        grid5.placeStartCard(3,2, goldCards.get(3));
        grid5.placeStartCard(3,4, resourceCards.get(4));
        grid5.placeStartCard(4,1, resourceCards.get(5));
        grid5.placeStartCard(4,5, resourceCards.get(12));
        grid5.placeStartCard(5,0, resourceCards.get(6));
        grid5.placeStartCard(5,2, resourceCards.get(7));
        grid5.placeStartCard(5,14, goldCards.get(5));
        grid5.placeStartCard(6,3, resourceCards.get(15));
        grid5.placeStartCard(6,13, resourceCards.get(8));
        grid5.placeStartCard(7,12, goldCards.get(2));
        grid5.placeStartCard(8,11, startCards.get(0));
        grid5.placeStartCard(9,12, goldCards.get(6));
        grid5.placeStartCard(10,11, goldCards.get(7));
        grid5.placeStartCard(10,13, goldCards.get(18));
        grid5.placeStartCard(11,20, resourceCards.get(9));
        grid5.placeStartCard(12,19, resourceCards.get(8));
        grid5.placeStartCard(18,23, goldCards.get(8));
        grid5.placeStartCard(18,24, goldCards.get(9));

        assertEquals(6, objCard86.calculatePoints(grid5));
        assertEquals(9, objCard90.calculatePoints(grid5));


    }

    @Test
    void calculatePointsP6() throws InvalidPlacementException {
        //Parsing the game cards
        JSONParser parser = new JSONParser();
        List<GameCard> resourceCards = parser.parseResourceCards();
        List<GameCard> goldCards = parser.parseResourceCards();
        List<GameCard> startCards = parser.parseStartCards();
        List<ObjectiveCard> objectiveCards = parser.parseObjectiveCards();
        for(GameCard resourceCard : resourceCards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldCards) {
            goldCard.setValidSide(false);
        }
        for(GameCard startCard : startCards) {
            startCard.setValidSide(false);
        }
        for(ObjectiveCard objectiveCard : objectiveCards) {
            objectiveCard.calculateDeltas();
        }

        PlayingGrid grid5 = new PlayingGrid();
        ObjectiveCard objCard86 = objectiveCards.get(0);
        ObjectiveCard objCard90 = objectiveCards.get(4);
        grid5.placeStartCard(5,8, resourceCards.get(0));
        grid5.placeStartCard(5,14, goldCards.get(1));
        grid5.placeStartCard(6,9, resourceCards.get(2));
        grid5.placeStartCard(6,13, goldCards.get(3));
        grid5.placeStartCard(7,10, resourceCards.get(4));
        grid5.placeStartCard(7,12, resourceCards.get(5));
        grid5.placeStartCard(8,11, startCards.get(0));
        grid5.placeStartCard(9,10, resourceCards.get(6));
        grid5.placeStartCard(9,12, resourceCards.get(7));
        grid5.placeStartCard(10,9, resourceCards.get(15));
        grid5.placeStartCard(10,13, goldCards.get(18));

        assertEquals(2, objCard86.calculatePoints(grid5));
        assertEquals(3, objCard90.calculatePoints(grid5));

    }


}