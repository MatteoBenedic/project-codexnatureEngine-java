package it.polimi.ingsw.am12.Model.Logic;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards.ObjectiveCard;
import it.polimi.ingsw.am12.Utils.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.*;

class MatchTest {

    @Test
    public void Match() {
        //Test case: positive number of players
        Match match1 = new Match(10);
        assertEquals(10, match1.getNumPlayers());

        //Test case: negative number of players
        Match match2 = new Match(-1);
        assertEquals(0, match2.getNumPlayers());

        //Test case: 0 players
        Match match3 = new Match(0);
        assertEquals(0, match3.getNumPlayers());
    }

    @Test
    public void addPlayer() {

        //Test case: right number of players
        Match match1 = new Match(4);
        match1.addPlayer("player1");
        match1.addPlayer("player2");
        match1.addPlayer("player3");
        match1.addPlayer("player4");
        
        assertEquals(List.of("player1", "player2", "player3", "player4"), match1.getPlayerNames());

        //Test case: lower number of players
        Match match2 = new Match(2);
        match2.addPlayer("player1");
        
        assertEquals(List.of("player1"), match2.getPlayerNames());

        //Test case: greater number of players
        Match match3 = new Match(2);
        match3.addPlayer("player1");
        match3.addPlayer("player2");
        match3.addPlayer("player3");
        
        assertEquals(List.of("player1", "player2"), match3.getPlayerNames());

        //Test case: match with 0 players
        Match match4 = new Match(0);
        match4.addPlayer("player1");
        
        assertTrue(match4.getPlayerNames().isEmpty());
    }

    @Test
    public void randomizePlayerOrder() {

        //Test case: 3 players
        Match match3 = new Match(3);
        match3.addPlayer("player1");
        match3.addPlayer("player2");
        match3.addPlayer("player3");
        match3.randomizePlayerOrder();
        
        assertEquals(3, match3.getPlayerNames().size());
        assertTrue(match3.getPlayerNames().contains("player1"));
        assertTrue(match3.getPlayerNames().contains("player2"));
        assertTrue(match3.getPlayerNames().contains("player3"));
    }

    @Test
    void assignStartCards() {
        Match match = new Match(3);
        match.addPlayer("player1");
        match.addPlayer("player2");
        match.addPlayer("player3");

        match.createDecks();
        match.assignStartCards();
        
        for(String player : match.getPlayerNames()) {
            assertEquals(1, match.getCardsInHand(player).size());
            assertTrue(match.getCardsInHand(player).getFirst() >=80
                    && match.getCardsInHand(player).getFirst() <=85 );
        }
    }

    @Test

    void placeStartCards() throws InvalidPlacementException {
        Match match = new Match(3);
        match.addPlayer("player1");
        match.addPlayer("player2");
        match.addPlayer("player3");

        match.createDecks();
        match.assignStartCards();
        match.placeStartCard("player1", false);
        match.placeStartCard("player2", true);
        match.placeStartCard("player3", true);
        
        for(String player : match.getPlayerNames()) {
            assertTrue(match.getCardsInHand(player).isEmpty());
            assertTrue(match.getLastPlacedCard(player) >=80 && match.getLastPlacedCard(player) <=85);
        }
        assertEquals(false, match.getLastPlacedCardSide("player1"));
        assertEquals(true, match.getLastPlacedCardSide("player2"));
        assertEquals(true, match.getLastPlacedCardSide("player3"));

        match.assignStartCards();
        assertThrows(InvalidPlacementException.class, ()-> match.placeStartCard("player1", false));
        assertThrows(InvalidPlacementException.class, ()-> match.placeStartCard("player2", true));
        assertThrows(InvalidPlacementException.class, ()-> match.placeStartCard("player3", false));
    }

    @Test
    void setPlayerColors() {
        Match match = new Match(3);
        match.addPlayer("player1");
        match.addPlayer("player2");
        match.addPlayer("player3");

        match.setPlayerColour("player1", PlayerColour.RED);
        match.setPlayerColour("player2", PlayerColour.GREEN);
        match.setPlayerColour("player3", PlayerColour.YELLOW);
        
        assertEquals(PlayerColour.RED, match.getPlayerColour("player1"));
        assertEquals(PlayerColour.GREEN, match.getPlayerColour("player2"));
        assertEquals(PlayerColour.YELLOW, match.getPlayerColour("player3"));
    }

    @Test
    void drawObjective() throws EmptyDeckException {
        Match match = new Match(2);
        match.createDecks();
        
        for(int i=0; i<16; i++)
        {
            ObjectiveCard obj = match.drawObjective();
            assertTrue(obj.getObjIndex()>=86 && obj.getObjIndex()<102);
        }
        assertThrows(EmptyDeckException.class, match::drawObjective);
    }

    @Test
    void distributeCards() throws EmptyDeckException {
        //Test case: distribute cards once
        Match match1 = new Match(2);
        match1.addPlayer("player1");
        match1.addPlayer("player2");
        match1.createDecks();

        match1.distributeCards();
        
        assertNotNull(match1.getPublicObjectives()[0]);
        assertNotNull(match1.getPublicObjectives()[1]);
        
        assertTrue(match1.getObjectivesToChoose("player1")[0] >=86
                && match1.getObjectivesToChoose("player1")[0] <102);
        assertTrue(match1.getObjectivesToChoose("player1")[1] >=86
                && match1.getObjectivesToChoose("player1")[1] <102);
        assertTrue(match1.getObjectivesToChoose("player2")[0] >=86
                && match1.getObjectivesToChoose("player2")[0] <102);
        assertTrue(match1.getObjectivesToChoose("player2")[0] >=86
                && match1.getObjectivesToChoose("player2")[0] <102);
        
        assertEquals(3, match1.getCardsInHand("player1").size());
        assertEquals(3, match1.getCardsInHand("player2").size());
        
        //Test case: match with 1 player, distribute cards twice
        Match match2 = new Match(1);
        match2.addPlayer("player1");
        match2.createDecks();

        match2.distributeCards();
        assertThrows(IllegalStateException.class, match2::distributeCards);
        
        assertNotNull(match2.getPublicObjectives()[0]);
        assertNotNull(match2.getPublicObjectives()[1]);

        assertTrue(match2.getObjectivesToChoose("player1")[0] >=86
                && match2.getObjectivesToChoose("player1")[0] <102);
        assertTrue(match2.getObjectivesToChoose("player1")[1] >=86
                && match2.getObjectivesToChoose("player1")[1] <102);

        assertEquals(3, match2.getCardsInHand("player1").size());
    }

    @Test
    void setPlayerObjectives() throws EmptyDeckException {
        //Test case:
        // player1 selected the first objective
        // player2 selected the second objective
        Match match = new Match(2);
        match.addPlayer("player1");
        match.addPlayer("player2");
        match.createDecks();
        match.distributeCards();

        match.setPlayerObjective("player1", true);
        match.setPlayerObjective("player2", false);

        assertEquals(match.getSecretObjective("player1"), match.getObjectivesToChoose("player1")[0]);
        assertEquals(match.getSecretObjective("player2"), match.getObjectivesToChoose("player2")[1]);
    }

    @Test
    void getPlaceablePositions() throws InvalidPlacementException, InvalidSearchPositionException {
        //Test case: available positions around start card back
        // (4 visible corners)
        Match match = new Match(1);
        match.addPlayer("player");
        match.createDecks();
        match.assignStartCards();
        match.placeStartCard("player", true);

        List<Coordinate> expectedResult = Arrays.asList(new Coordinate(39,39), new Coordinate(39,41), new Coordinate(41,39), new Coordinate(41,41));
        List<Coordinate> actualResult = match.getPlaceablePositions(40, 40);
        assertEquals(4, actualResult.size());
        for(int i = 0; i<4; i++) {
            assertEquals(expectedResult.get(i).getX(), actualResult.get(i).getX());
            assertEquals(expectedResult.get(i).getY(), actualResult.get(i).getY());
        }

        //Test case: call getCardPlaceability on empty position
        assertThrows(InvalidSearchPositionException.class, () -> match.getPlaceablePositions(50, 75));
    }

    @Test
    void drawCard() throws EmptyDeckException {
        Match match = new Match(1);
        match.addPlayer("player");
        match.createDecks();

        match.drawCard(0);
        match.drawCard(1);
        match.drawCard(2);
        assertThrows(IllegalStateException.class, () -> match.drawCard(5));

        assertEquals(3, match.getCardsInHand("player").size());
    }

    @Test void nextTurn() throws EmptyDeckException {
        Match match = new Match(3);
        match.addPlayer("player1");
        match.addPlayer("player2");
        match.addPlayer("player3");
        match.createDecks();

        for(int i=0; i<4; i++) {
            match.drawCard(4);
            match.nextTurn();
        }

        assertEquals(2, match.getCardsInHand("player1").size());
        assertEquals(1, match.getCardsInHand("player2").size());
        assertEquals(1, match.getCardsInHand("player3").size());
    }

    @Test
    void checkEndGameCondition() throws EmptyDeckException, InvalidPlacementException {
        //Test case: 1 player (player 1 triggers final stage)
        Match match = new Match(1);
        match.addPlayer("player1");
        match.createDecks();
        match.assignStartCards();
        match.placeStartCard("player1", true);

        for(int i = 0; i<38; i++) {
            match.drawCard(4);
            match.placeCard(0, false, 41 + i, 41 + i);
            assertTrue(match.nextTurn() > 2);
        }
        for(int i = 0; i<38; i++) {
            match.drawCard(5);
            match.placeCard(0, false, 39 - i, 39 - i);
            if(i < 37)
                assertTrue(match.nextTurn() > 2);
        }

        assertTrue(match.checkEndGameCondition());
        assertEquals(1, match.nextTurn());
        assertFalse(match.checkEndGameCondition());
        assertEquals(0, match.nextTurn());

        //Test case: 3 players (player 2 triggers final stage)
        Match match2 = new Match(3);
        match2.addPlayer("player1");
        match2.addPlayer("player2");
        match2.addPlayer("player3");
        match2.createDecks();
        match2.assignStartCards();
        match2.placeStartCard("player1", true);
        match2.placeStartCard("player2", true);
        match2.placeStartCard("player3", true);

        assertTrue(match2.nextTurn() > 2);
        for(int i = 0; i<38; i++) {
            match2.drawCard(4);
            match2.placeCard(0, false, 41 + i, 41 + i);
        }
        for(int i = 0; i<38; i++) {
            match2.drawCard(5);
            match2.placeCard(0, false, 39 - i, 39 - i);
        }

        assertTrue(match2.checkEndGameCondition());
        assertEquals(2, match2.nextTurn());
        assertFalse(match2.checkEndGameCondition());
        assertEquals(1, match2.nextTurn());
        assertEquals(1, match2.nextTurn());
        assertEquals(1, match2.nextTurn());
        assertEquals(0, match2.nextTurn());
    }

    @Test
    void isTurn() throws WrongInformationException {
        Match match = new Match(3);
        match.addPlayer("player1");
        match.addPlayer("player2");
        match.addPlayer("player3");

        match.createDecks();

        assertTrue(match.isTurn("player1"));
        assertFalse(match.isTurn("player2"));
        assertFalse(match.isTurn("player3"));

        match.nextTurn();
        assertFalse(match.isTurn("player1"));
        assertTrue(match.isTurn("player2"));
        assertFalse(match.isTurn("player3"));

        match.nextTurn();
        assertFalse(match.isTurn("player1"));
        assertFalse(match.isTurn("player2"));
        assertTrue(match.isTurn("player3"));

        match.nextTurn();
        assertTrue(match.isTurn("player1"));
        assertFalse(match.isTurn("player2"));
        assertFalse(match.isTurn("player3"));

        assertThrows(WrongInformationException.class, () -> match.isTurn("notExistingPlayer"));
    }

}
