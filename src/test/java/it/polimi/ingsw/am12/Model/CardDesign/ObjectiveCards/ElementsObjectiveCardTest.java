package it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards;

import it.polimi.ingsw.am12.Model.Logic.PlayingGrid;
import it.polimi.ingsw.am12.Utils.JSONParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ElementsObjectiveCardTest {

    @Test
    void calculatePointsConstr100() throws IllegalRequirementsException {
        //Case Objective Card index 100
        int[] requiredElements = {0,0,2,0,0,0,0};
        ElementsObjectiveCard elem100 = new ElementsObjectiveCard(100,2,requiredElements);
        int[] numElements = {0,0,6,2,2,0,2};
        PlayingGrid grid = new PlayingGrid();
        grid.setNumElements(numElements);
        assertEquals(6, elem100.calculatePoints(grid));

    }


    @Test
    void calculatePointsPrs100() throws IllegalRequirementsException {
        JSONParser parser = new JSONParser();
        List<ObjectiveCard> objCards = parser.parseObjectiveCards();
        //Case Objective Card index 100
        ObjectiveCard elem100 = objCards.get(14);
        int[] numElements = {0,0,6,2,2,0,2};
        PlayingGrid grid = new PlayingGrid();
        grid.setNumElements(numElements);
        assertEquals(6, elem100.calculatePoints(grid));

    }

    @Test
    void calculatePointsConstr0() throws IllegalRequirementsException {
        //Case Test 0: see attached documentation
        int[] reqiredElements0 = {0,0,2,1,0,2,0};
        int[] numElements0={0,0,4,1,0,2,2};
        ElementsObjectiveCard elem0 = new ElementsObjectiveCard(0,2,reqiredElements0);
        PlayingGrid grid0 = new PlayingGrid();
        grid0.setNumElements(numElements0);
        assertEquals(2,elem0.calculatePoints(grid0));

    }

    @Test
    void calculatePointsConstr1() throws IllegalRequirementsException {
        //Case Test 1: see attached documentation
        int[] requiredElements1 = {0,0,2,2,0,2,0};
        int[] numElements1={0,0,4,1,0,2,2};
        ElementsObjectiveCard elem1 = new ElementsObjectiveCard(0, 2, requiredElements1);
        PlayingGrid grid1 = new PlayingGrid();
        grid1.setNumElements(numElements1);
        assertEquals(0, elem1.calculatePoints(grid1));
    }

    @Test
    void calculatePointsConstr2() throws IllegalRequirementsException {
        //Case Test 2: see attached documentation
        int[] requiredElements2 = {0,0,0,2,2,0,2};
        int[] numElements2={0,0,0,2,4,0,6};
        ElementsObjectiveCard elem2 = new ElementsObjectiveCard(0,2,requiredElements2);
        PlayingGrid grid2 = new PlayingGrid();
        grid2.setNumElements(numElements2);
        assertEquals(2, elem2.calculatePoints(grid2));
    }

    @Test
    void calculatePointsConstr3() throws IllegalRequirementsException {
        //Case Test 3: see attached documentation
        int[] requiredElements3 = {0,0,0,2,2,0,2};
        int[] numElements3={0,0,0,6,4,0,2};
        ElementsObjectiveCard elem3 = new ElementsObjectiveCard(0,2,requiredElements3);
        PlayingGrid grid3 = new PlayingGrid();
        grid3.setNumElements(numElements3);
        assertEquals(2, elem3.calculatePoints(grid3));
    }

    @Test
    void calculatePointsConstr4() throws IllegalRequirementsException {
        //Case Test 4: see attached documentation
        int[] requiredElements4 = {0,0,0,2,2,0,2};
        int[] numElements4={0,0,0,6,4,0,1};
        ElementsObjectiveCard elem4 = new ElementsObjectiveCard(0,2,requiredElements4);
        PlayingGrid grid4 = new PlayingGrid();
        grid4.setNumElements(numElements4);
        assertEquals(0, elem4.calculatePoints(grid4));
    }


}