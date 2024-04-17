package it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards;

import it.polimi.ingsw.am12.Model.Logic.PlayingGrid;
import it.polimi.ingsw.am12.Utils.JSONParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ElementsObjectiveCardTest {
    @Test
    void calculatePointsC100() throws IllegalRequirementsException {
        //Case Objective Card 100
        int[] reqElements = {0,0,2,0,0,0,0};
        ElementsObjectiveCard elem100 = new ElementsObjectiveCard(100,2,reqElements);
        int[] numElements100 = {0,0,6,2,2,0,2};
        PlayingGrid plcards100 = new PlayingGrid();
        plcards100.setNumElements(numElements100);
        assertEquals(6, elem100.calculatePoints(plcards100));

    }


    @Test
    void calculatePointsP100() throws IllegalRequirementsException {
        JSONParser p = new JSONParser();
        List<ObjectiveCard> objcards = p.parseObjectiveCards();
        //Case Objective Card 100
        ObjectiveCard elem100 = objcards.get(14);
        int[] numElements100 = {0,0,6,2,2,0,2};
        PlayingGrid plcards100 = new PlayingGrid();
        plcards100.setNumElements(numElements100);
        assertEquals(6, elem100.calculatePoints(plcards100));

    }

    @Test
    void calculatePointsC0() throws IllegalRequirementsException {
        //Case 0: see attached documentation
        int[] reqElements0 ={0,0,2,1,0,2,0};
        int[] numElements0 ={0,0,4,1,0,2,2};
        ElementsObjectiveCard elem0 = new ElementsObjectiveCard(0,2,reqElements0);
        PlayingGrid plcards0 = new PlayingGrid();
        plcards0.setNumElements(numElements0);
        assertEquals(2,elem0.calculatePoints(plcards0));

    }

    @Test
    void calculatePointsC1() throws IllegalRequirementsException {
        //Case 1: see attached documentation
        int[] reqElements1 = {0,0,2,2,0,2,0};
        int[] numElements1 = {0,0,4,1,0,2,2};
        ElementsObjectiveCard elem1 = new ElementsObjectiveCard(0, 2, reqElements1);
        PlayingGrid plcards1 = new PlayingGrid();
        plcards1.setNumElements(numElements1);
        assertEquals(0, elem1.calculatePoints(plcards1));
    }

    @Test
    void calculatePointsC2() throws IllegalRequirementsException {
        //Case 2: see attached documentation
        int[] reqElements2 = {0,0,0,2,2,0,2};
        int[] numElements2 = {0,0,0,2,4,0,6};
        ElementsObjectiveCard elem2 = new ElementsObjectiveCard(0,2,reqElements2);
        PlayingGrid plcards2 = new PlayingGrid();
        plcards2.setNumElements(numElements2);
        assertEquals(2, elem2.calculatePoints(plcards2));
    }

    @Test
    void calculatePointsC3() throws IllegalRequirementsException {
        //Case 3: see attached documentation
        int[] reqElements3 = {0,0,0,2,2,0,2};
        int[] numElements3 = {0,0,0,6,4,0,2};
        ElementsObjectiveCard elem3 = new ElementsObjectiveCard(0,2,reqElements3);
        PlayingGrid plcards3 = new PlayingGrid();
        plcards3.setNumElements(numElements3);
        assertEquals(2, elem3.calculatePoints(plcards3));
    }

    @Test
    void calculatePointsC4() throws IllegalRequirementsException {
        //Case 4: see attached documentation
        int[] reqElements4 = {0,0,0,2,2,0,2};
        int[] numElements4 = {0,0,0,6,4,0,1};
        ElementsObjectiveCard elem4 = new ElementsObjectiveCard(0,2,reqElements4);
        PlayingGrid plcards4 = new PlayingGrid();
        plcards4.setNumElements(numElements4);
        assertEquals(0, elem4.calculatePoints(plcards4));
    }


}