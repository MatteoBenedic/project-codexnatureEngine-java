package it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards;

import it.polimi.ingsw.am12.Model.CardDesign.GameCard.GameCard;
import it.polimi.ingsw.am12.Model.Logic.InvalidPlacementException;
import it.polimi.ingsw.am12.Model.Logic.PlayingGrid;
import it.polimi.ingsw.am12.Utils.JSONParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveCardTest {


    @Test
    void getObjIndexC() throws IllegalRequirementsException {
        CoordinateSubmatrix[] obj86Coord = new CoordinateSubmatrix[3];
        obj86Coord[0]= new CoordinateSubmatrix(2,0, "red");
        obj86Coord[1]= new CoordinateSubmatrix(1,1, "red");
        obj86Coord[2]= new CoordinateSubmatrix(0,2, "red");
        PatternObjectiveCard objcard86 = new PatternObjectiveCard(obj86Coord, 86, 2);
        objcard86.calculateDeltas();

        assertEquals(86, objcard86.getObjIndex());
    }

    @Test
    void getObjIndexP() {
        JSONParser p = new JSONParser();
        List<ObjectiveCard> objcards = p.parseObjectiveCards();

        assertEquals(86, objcards.get(0).getObjIndex());
    }


    @Test
    void getPointsC() throws IllegalRequirementsException {

        CoordinateSubmatrix[] obj86Coord = new CoordinateSubmatrix[3];
        obj86Coord[0]= new CoordinateSubmatrix(2,0, "red");
        obj86Coord[1]= new CoordinateSubmatrix(1,1, "red");
        obj86Coord[2]= new CoordinateSubmatrix(0,2, "red");
        PatternObjectiveCard objcard86 = new PatternObjectiveCard(obj86Coord, 86, 2);
        objcard86.calculateDeltas();

        assertEquals(2, objcard86.getPoints());
    }

    @Test
    void getPointsP() {

        JSONParser p = new JSONParser();
        List<ObjectiveCard> objcards = p.parseObjectiveCards();

        assertEquals(2, objcards.get(0).getPoints());
    }

    @Test
    void calculatePointsC() throws IllegalRequirementsException, InvalidPlacementException {

        CoordinateSubmatrix[] obj91Coord = new CoordinateSubmatrix[3];
        obj91Coord[0]= new CoordinateSubmatrix(1,0, "green");
        obj91Coord[1]= new CoordinateSubmatrix(1,2, "green");
        obj91Coord[2]= new CoordinateSubmatrix(0,3,"purple");
        PatternObjectiveCard objcard91 = new PatternObjectiveCard(obj91Coord, 91, 3);
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


        //Case 3 PatternObjectiveCard: see attached documentation
        PlayingGrid plcards3= new PlayingGrid();
        plcards3.placeStartCard(1,4, goldcards.get(16));
        plcards3.placeStartCard(2,4, goldcards.get(17));
        plcards3.placeStartCard(3,4, goldcards.get(18));
        plcards3.placeStartCard(4,3, resourcecards.get(31));
        plcards3.placeStartCard(4,4, resourcecards.get(14));
        plcards3.placeStartCard(5,3, resourcecards.get(32));
        //calculatePoints is overidden
        assertEquals(6, objcard91.calculatePoints(plcards3));

    }

    @Test
    void calculatePointsP() throws InvalidPlacementException {

        JSONParser p = new JSONParser();
        List<ObjectiveCard> objcards = p.parseObjectiveCards();
        List<GameCard> resourcecards = p.parseResourceCards();
        List<GameCard> goldcards = p.parseResourceCards();
        for(GameCard resourceCard : resourcecards) {
            resourceCard.setValidSide(false);
        }
        for(GameCard goldCard : goldcards) {
            goldCard.setValidSide(false);
        }
        ObjectiveCard obj91 = objcards.get(5);
        obj91.calculateDeltas();

        //Case 3 (PatternObjectiveCard): see attached documentation
        PlayingGrid plcards3= new PlayingGrid();
        plcards3.placeStartCard(1,4, goldcards.get(16));
        plcards3.placeStartCard(2,4, goldcards.get(17));
        plcards3.placeStartCard(3,4, goldcards.get(18));
        plcards3.placeStartCard(4,3, resourcecards.get(31));
        plcards3.placeStartCard(4,4, resourcecards.get(14));
        plcards3.placeStartCard(5,3, resourcecards.get(32));
        //calculatePoints is overidden
        assertEquals(6, obj91.calculatePoints(plcards3));

    }



}