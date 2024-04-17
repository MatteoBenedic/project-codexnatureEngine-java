package it.polimi.ingsw.am12.Model.Logic;

import it.polimi.ingsw.am12.Model.CardDesign.GameCard.Element;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.GameCard;
import it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards.CoordinateSubmatrix;
import it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards.IllegalRequirementsException;
import it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards.ObjectiveCard;
import it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards.PatternObjectiveCard;
import it.polimi.ingsw.am12.Utils.Coordinate;
import it.polimi.ingsw.am12.Utils.JSONParser;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayingGridTest {
    JSONParser parser = new JSONParser();
    List<GameCard> golds = parser.parseGoldCards();
    List<GameCard> resources = parser.parseResourceCards();
    List<GameCard> starters = parser.parseStartCards();

    @Test
    void place() throws IllegalRequirementsException, InvalidPlacementException {
        //defined numElements (setNumElements)
        PlayingGrid playingGrid = new PlayingGrid();
        GameCard[][] grid = playingGrid.getPlcards();
        int[] numElemNull = {0, 0, 0, 0, 0, 0, 0};
        playingGrid.setNumElements(numElemNull);
        //
        GameCard st1 = starters.getFirst();
        st1.setValidSide(true);
        playingGrid.placeStartCard(40, 40, st1);

        //test case 1: try placing when there's already a card - PLACE FAILED
        GameCard rs1 = resources.getFirst();
        rs1.setValidSide(true);
        InvalidPlacementException e1 = assertThrows(InvalidPlacementException.class, () -> playingGrid.place(40, 40, rs1));
        assertEquals("There's already a card in this position!" , e1.getMessage());

        //test case 2: try placing with no cards around - PLACE FAILED
        InvalidPlacementException e2 = assertThrows(InvalidPlacementException.class, () -> playingGrid.place(1, 1, rs1));
        assertEquals("There's no card near this one!" , e2.getMessage());

        //test case 3: try placing but position not placeable (hidden corner) - PLACE FAILED
        GameCard rs2 = resources.get(7);
        rs2.setValidSide(true);
        grid[0][0] = rs2;
        InvalidPlacementException e3 = assertThrows(InvalidPlacementException.class, () -> playingGrid.place(1, 1, rs1));
        assertEquals("You can't place here!" , e3.getMessage());

        //test case 4: try placing card with no requirements achieved - PLACE FAILED
        GameCard gl1 = golds.get(1);
        gl1.setValidSide(true);
        InvalidPlacementException e4 = assertThrows(InvalidPlacementException.class, () -> playingGrid.place(39, 41, gl1));
        assertEquals("You can't place this card on this side, requirements not achieved" , e4.getMessage());

        //test case 5: try good placing (placing not topLeft of a card)- PLACE SUCCEDED
        int[] numElemNotCrash = {0, 0, 0, 0, 0, 0, 0};
        playingGrid.setNumElements(numElemNotCrash);
        GameCard rs8 =  resources.get(8);
        rs8.setValidSide(true);
        int place_points = playingGrid.place(41,39,rs8);

        //                  check new resources added in numElements
        //                  check Points returned correctly
        //                  check that this has become lastPlacedCard

        assertEquals(1,playingGrid.getNumElements(it.polimi.ingsw.am12.Model.CardDesign.GameCard.Element.FUNGUS));
        assertEquals(1,place_points);
        assertEquals(rs8,playingGrid.getLastPlacedCard());

        //test case 6: try good placing and this has to be only linked to a single card
        //                 and it has to be topLeft

        int[] numElemNotCrash1 = {10, 10, 10, 10, 10, 10, 10};
        playingGrid.setNumElements(numElemNotCrash1);
        GameCard rs6 =  resources.get(6);
        rs6.setValidSide(true);
        int placeTopLeft = playingGrid.place(39,39,rs6);
        assertEquals(1,place_points);

    }



    @Test
    public void getPlaceablePosition() throws InvalidSearchPositionException{
        PlayingGrid playingGrid = new PlayingGrid();
        GameCard[][] grid = playingGrid.getPlcards();

        //test case 0: no card
        assertThrows(InvalidSearchPositionException.class, () -> playingGrid.getPlaceablePosition(2, 2));
        assertThrows(InvalidSearchPositionException.class, () -> playingGrid.getPlaceablePosition(0, 0));
        assertThrows(InvalidSearchPositionException.class, () -> playingGrid.getPlaceablePosition(81, 81));
        assertThrows(InvalidSearchPositionException.class, () -> playingGrid.getPlaceablePosition(34, 81));
        assertThrows(InvalidSearchPositionException.class, () -> playingGrid.getPlaceablePosition(-1, 80));


        //test case 1: only the start card
        GameCard st = starters.getFirst();
        st.setValidSide(true);
        grid[40][40] = st;
        assertDoesNotThrow(() -> playingGrid.getPlaceablePosition(40, 40));
        List<Coordinate> plPos = playingGrid.getPlaceablePosition(40, 40);
        assertEquals(4, plPos.size());
        assertEquals(39, plPos.get(0).getX());
        assertEquals(39, plPos.get(0).getY());
        assertEquals(39, plPos.get(1).getX());
        assertEquals(41, plPos.get(1).getY());
        assertEquals(41, plPos.get(2).getX());
        assertEquals(39, plPos.get(2).getY());
        assertEquals(41, plPos.get(3).getX());
        assertEquals(41, plPos.get(3).getY());

        //test case 2: a card more, with a hidden corner (and linked to the start card)
        GameCard rs1 = resources.get(2);
        rs1.setValidSide(true);
        grid[41][41] = rs1;
        plPos = playingGrid.getPlaceablePosition(40, 40);
        assertEquals(3, plPos.size());
        assertEquals(39, plPos.get(0).getX());
        assertEquals(39, plPos.get(0).getY());
        assertEquals(39, plPos.get(1).getX());
        assertEquals(41, plPos.get(1).getY());
        assertEquals(41, plPos.get(2).getX());
        assertEquals(39, plPos.get(2).getY());

        plPos = playingGrid.getPlaceablePosition(41, 41);
        assertEquals(2, plPos.size());
        assertEquals(42, plPos.get(0).getX());
        assertEquals(40, plPos.get(0).getY());
        assertEquals(42, plPos.get(1).getX());
        assertEquals(42, plPos.get(1).getY());

        //test case 3: 3 cards, to check that a placeable position of one card can be discarded if
        //              there's a hidden corner of another card that blocks that place
        GameCard rs2 = resources.get(3);
        rs2.setValidSide(true);
        grid[39][41] = rs2;
        plPos = playingGrid.getPlaceablePosition(40, 40);
        assertEquals(2, plPos.size());
        assertEquals(39, plPos.get(0).getX());
        assertEquals(39, plPos.get(0).getY());
        assertEquals(41, plPos.get(1).getX());
        assertEquals(39, plPos.get(1).getY());

        plPos = playingGrid.getPlaceablePosition(41, 41);
        assertEquals(2, plPos.size());
        assertEquals(42, plPos.get(0).getX());
        assertEquals(40, plPos.get(0).getY());
        assertEquals(42, plPos.get(1).getX());
        assertEquals(42, plPos.get(1).getY());

        plPos = playingGrid.getPlaceablePosition(39, 41);
        assertEquals(1, plPos.size());
        assertEquals(38, plPos.get(0).getX());
        assertEquals(42, plPos.get(0).getY());


        //test case 4: there's no valid position around the card (cause: already 4 card placed around)
        GameCard gl1 = golds.get(13);
        gl1.setValidSide(true);
        grid[41][39] = gl1;
        GameCard rs3 = resources.get(4);
        rs3.setValidSide(true);
        grid[39][39] = rs3;

        assertDoesNotThrow(() -> playingGrid.getPlaceablePosition(40, 40));
        plPos = playingGrid.getPlaceablePosition(40, 40);
        assertEquals(0, plPos.size());

        //test case 4bis: there's no valid position around the card (cause: mixed, hidden corners, blocked from another
        //                  card, card already placed)
        assertDoesNotThrow(() -> playingGrid.getPlaceablePosition(39, 39));
        plPos = playingGrid.getPlaceablePosition(39, 39);
        assertEquals(0, plPos.size());

        //test case 5 : Position not considered -> cause: OutOfBound
        rs3.setValidSide(false);
        grid[0][0] = rs3;
        plPos = playingGrid.getPlaceablePosition(0, 0);
        assertEquals(1, plPos.size());
        assertEquals(1, plPos.getFirst().getX());
        assertEquals(1, plPos.getFirst().getY());

        //test case 6 : EdgeGrid position is considered, aware of "blockers" from other cards
        grid[79][79] = rs3;
        GameCard rs4 = resources.get(7);
        rs4.setValidSide(true);
        grid[77][79] = rs4;
        plPos = playingGrid.getPlaceablePosition(79, 79);
        assertEquals(3, plPos.size());
        assertEquals(78, plPos.getFirst().getX());
        assertEquals(78, plPos.getFirst().getY());
        assertEquals(80, plPos.get(1).getX());
        assertEquals(78, plPos.get(1).getY());
        assertEquals(80, plPos.get(2).getX());
        assertEquals(80, plPos.get(2).getY());
    }



    @Test
    void getNumElements() throws IllegalRequirementsException {

        PlayingGrid playingGrid = new PlayingGrid();
        int[] numElements = {10, 15, 200, 30, 1, 50, 60}; // Esempio di array di numeri di elementi
        playingGrid.setNumElements(numElements);

        // Verifica il numero di elementi per ciascun tipo di Element

        assertEquals(10, playingGrid.getNumElements(it.polimi.ingsw.am12.Model.CardDesign.GameCard.Element.ANIMAL));
        assertEquals(15, playingGrid.getNumElements(it.polimi.ingsw.am12.Model.CardDesign.GameCard.Element.FUNGUS));
        assertEquals(200, playingGrid.getNumElements(it.polimi.ingsw.am12.Model.CardDesign.GameCard.Element.INKWELL));
        assertEquals(30, playingGrid.getNumElements(it.polimi.ingsw.am12.Model.CardDesign.GameCard.Element.INSECT));
        assertEquals(1, playingGrid.getNumElements(it.polimi.ingsw.am12.Model.CardDesign.GameCard.Element.MANUSCRIPT));
        assertEquals(50, playingGrid.getNumElements(it.polimi.ingsw.am12.Model.CardDesign.GameCard.Element.PLANT));
        assertEquals(60, playingGrid.getNumElements(it.polimi.ingsw.am12.Model.CardDesign.GameCard.Element.QUILL));

    }


    @Test
    void checkCardPlaceability() throws IllegalRequirementsException {

        PlayingGrid playingGrid = new PlayingGrid();
        int[] numElements = {10, 15, 200, 30, 1, 50, 60}; // Esempio di array di numeri di elementi
        playingGrid.setNumElements(numElements);

        GameCard gc = golds.get(3);
        gc.setValidSide(true);
        int[] temp1 = gc.getRequirements();

        assertTrue(playingGrid.CheckCardPlaceability(gc));

        int[] numElements1 = {10, 2, 200, 30, 1, 50, 60};
        GameCard rs_7 = resources.get(7);
        rs_7.setValidSide(true); //test no req
        playingGrid.setNumElements(numElements1);
        assertFalse(playingGrid.CheckCardPlaceability(gc));
        assertTrue(playingGrid.CheckCardPlaceability(rs_7));

    }

    @Test
    void computePoints() throws IllegalRequirementsException {
        int points = 0;
        PlayingGrid pg = new PlayingGrid();
        GameCard[][] grid = pg.getPlcards();

        //test case 1: compute points of a no point card (or side)
        GameCard rs1 = resources.get(6);
        rs1.setValidSide(true);
        pg.setLastPlacedCard(rs1);
        points = pg.ComputePoints();
        assertEquals(0, points);

        //test case 2: compute points of a resource card with points
        GameCard rs2 = resources.get(7);
        rs2.setValidSide(true);
        pg.setLastPlacedCard(rs2);
        points = pg.ComputePoints();
        assertEquals(1, points);

        //test case 3: compute points of a gold card without condition
        GameCard gl1 = golds.get(36);
        gl1.setValidSide(true);
        pg.setLastPlacedCard(gl1);
        points = pg.ComputePoints();
        assertEquals(3, points);

        //test case 3bis: compute points of the back of the last one
        gl1.setValidSide(false);
        points = pg.ComputePoints();
        assertEquals(0, points);

        //test case 4: compute points of gold card with corner condition
        GameCard gl2 = golds.get(34);
        gl2.setValidSide(true);
        pg.setLastPlacedCard(gl2);
        grid[40][40] = gl1;
        grid[39][39] = gl2;
        gl2.setCoordinates(39, 39);
        points = pg.ComputePoints();
        assertEquals(2, points);

        //test case 5: check multiplying corner condition
        rs1.setValidSide(false);
        rs2.setValidSide(false);
        pg.setLastPlacedCard(gl2);
        grid[38][40] = rs1;
        grid[38][38] = rs2;
        points = pg.ComputePoints();
        assertEquals(6, points);

        //test case 6: check points of gold card with object/element condition
        int[] numEl = {0, 0, 0, 0, 0, 0, 3};
        pg.setNumElements(numEl);
        GameCard gl3 = golds.getFirst();
        gl3.setValidSide(true);
        pg.setLastPlacedCard(gl3);
        points = pg.ComputePoints();
        assertEquals(3, points);
    }

    @Test
    void cellIsEmpty() {
        PlayingGrid placedCards = new PlayingGrid();
        for(int i=0; i<placedCards.getPlcards().length; i++){
            for(int j=0; j<placedCards.getPlcards()[i].length; j++){
                assertTrue(placedCards.cellIsEmpty(i,j));
            }
        }
    }

    @Test
    void checkColourMatch() throws IllegalRequirementsException {
        JSONParser p = new JSONParser();
        List<GameCard> goldcards = p.parseGoldCards();
        List<GameCard> startcards = p.parseStartCards();
        for(GameCard startCard : startcards) {
            startCard.setValidSide(false);
        }
        for(GameCard goldCard : goldcards) {
            goldCard.setValidSide(false);
        }

        CoordinateSubmatrix[] obj86Coord = new CoordinateSubmatrix[3];
        obj86Coord[0]= new CoordinateSubmatrix(2,0, "red");
        obj86Coord[1]= new CoordinateSubmatrix(1,1, "red");
        obj86Coord[2]= new CoordinateSubmatrix(0,2, "red");
        PatternObjectiveCard objcard86 = new PatternObjectiveCard(obj86Coord, 86, 2);
        objcard86.calculateDeltas();
        CoordinateSubmatrix[] obj88Coord = new CoordinateSubmatrix[3];
        obj88Coord[0]= new CoordinateSubmatrix(2,0, "blue");
        obj88Coord[1]= new CoordinateSubmatrix(1,1, "blue");
        obj88Coord[2]= new CoordinateSubmatrix(0,2, "blue");
        PatternObjectiveCard objcard88 = new PatternObjectiveCard(obj88Coord, 88, 2);
        objcard88.calculateDeltas();
        PlayingGrid plcards = new PlayingGrid();
        plcards.placeStartCard(3,3, goldcards.get(0));
        //Colour does match
        assertTrue(plcards.checkColourMatch(3,3, objcard86, 0));
        //Colour does not match
        assertFalse(plcards.checkColourMatch(3,3, objcard88, 0));
        //Checking that colour doesn't match for start cards
        plcards.placeStartCard(3,4, startcards.get(0));
        assertFalse(plcards.checkColourMatch(3,4, objcard86, 0 ));
    }

    @Test
    void cardWasAlreadyUsedForThisObjective() throws IllegalRequirementsException {

        JSONParser p = new JSONParser();
        List<GameCard> goldcards = p.parseGoldCards();
        for(GameCard goldCard : goldcards) {
            goldCard.setValidSide(false);
        }
        CoordinateSubmatrix[] obj86Coord = new CoordinateSubmatrix[3];
        obj86Coord[0]= new CoordinateSubmatrix(2,0, "red");
        obj86Coord[1]= new CoordinateSubmatrix(1,1, "red");
        obj86Coord[2]= new CoordinateSubmatrix(0,2, "red");
        PatternObjectiveCard objcard86 = new PatternObjectiveCard(obj86Coord, 86, 2);
        objcard86.calculateDeltas();
        PlayingGrid plcards = new PlayingGrid();
        GameCard goldcard0 = goldcards.get(0);
        plcards.placeStartCard(3,3, goldcard0);

        //marking the card as completed for an objective card
        plcards.markCardAsUsedForThisObjective(3,3, objcard86);
        //using the method to verify that the card was already used for the objective
        assertTrue(plcards.cardWasAlreadyUsedForThisObjective(3,3, objcard86));

    }

    @Test
    void markCardAsUsedForThisObjective() throws IllegalRequirementsException {

        JSONParser p = new JSONParser();
        List<GameCard> goldcards = p.parseGoldCards();
        for(GameCard goldCard : goldcards) {
            goldCard.setValidSide(false);
        }
        PlayingGrid plcards = new PlayingGrid();
        GameCard goldcard0 = goldcards.get(0);
        plcards.placeStartCard(3,3, goldcard0);
        CoordinateSubmatrix[] obj87Coord = new CoordinateSubmatrix[3];
        obj87Coord[0]= new CoordinateSubmatrix(0,0, "green");
        obj87Coord[1]= new CoordinateSubmatrix(1,1, "green");
        obj87Coord[2]= new CoordinateSubmatrix(2,2, "green");
        PatternObjectiveCard objcard87 = new PatternObjectiveCard(obj87Coord, 87, 2);
        objcard87.calculateDeltas();
        //marking the card as completed for an objective card
        plcards.markCardAsUsedForThisObjective(3,3, objcard87);
        //using the method to verify that the card was already used for the objective
        assertTrue(plcards.cardWasAlreadyUsedForThisObjective(3,3, objcard87));
    }

    @Test
    void setNumElements() throws IllegalRequirementsException {

        PlayingGrid plcards = new PlayingGrid();
        int[] numElements = {0,3,4,2,4,0,4};
        plcards.setNumElements(numElements);
        assertEquals(0, plcards.getNumElements(Element.ANIMAL));
        assertEquals(3,plcards.getNumElements(Element.FUNGUS));
        assertEquals(4, plcards.getNumElements(Element.INKWELL));
        assertEquals(2, plcards.getNumElements(Element.INSECT));
        assertEquals(4, plcards.getNumElements(Element.MANUSCRIPT));
        assertEquals(0, plcards.getNumElements(Element.PLANT));
        assertEquals(4, plcards.getNumElements(Element.QUILL));

    }


}