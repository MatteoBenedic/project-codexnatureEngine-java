package it.polimi.ingsw.am12.Model.Logic;

import it.polimi.ingsw.am12.Exceptions.InvalidPlacementException;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.GameCard;
import it.polimi.ingsw.am12.Exceptions.IllegalRequirementsException;
import it.polimi.ingsw.am12.Utils.JSONParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void setColour() {

        Player player = new Player("emanuele");

        // Imposta il colore del giocatore e verifica che sia stato impostato correttamente

        player.setColour(PlayerColour.BLUE);
        assertEquals(PlayerColour.BLUE, player.getColour());

        player.setColour(PlayerColour.RED);
        assertEquals(PlayerColour.RED, player.getColour());

        player.setColour(PlayerColour.YELLOW);
        assertEquals(PlayerColour.YELLOW, player.getColour());

        player.setColour(PlayerColour.GREEN);
        assertEquals(PlayerColour.GREEN, player.getColour());

        player.setColour(PlayerColour.BLACK);
        assertEquals(PlayerColour.BLACK, player.getColour());

    }

    @Test
    public void getCardsInHand(){

        JSONParser parser = new JSONParser();
        List<GameCard> golds = parser.parseGoldCards();
        List<GameCard> resses = parser.parseResourceCards();
        List<GameCard> starters = parser.parseStartCards();
        GameCard gc = golds.get(3);               // 43
        GameCard rc = resses.get(5);             // 5
        GameCard sc = starters.get(0);          // 80
        GameCard gc1 = golds.get(4);            // 44

        Player Player1 = new Player("emanuele");

        // Aggiungi la prima carta e verifica che sia presente
        Player1.addCardInHand(sc);
        Player1.addCardInHand(gc);
        Player1.addCardInHand(gc1);

        // Ottieni le carte nella mano del giocatore
        List<GameCard> cardsInHand = Player1.getCardsInHand();

        // Assicurati che la lista non sia vuota e contenga le carte corrette
        assertNotNull(cardsInHand);
        assertEquals(3, cardsInHand.size());
        assertTrue(cardsInHand.contains(sc));
        assertTrue(cardsInHand.contains(gc));
        assertTrue(cardsInHand.contains(gc1));


        Player1.getCardsInHand().clear();
        assertDoesNotThrow(Player1::getCardsInHand);
        assertEquals(0, Player1.getCardsInHand().size());

    }

    @Test
    void addCardInHand() {

        JSONParser parser = new JSONParser();
        List<GameCard> golds = parser.parseGoldCards();
        List<GameCard> resses = parser.parseResourceCards();
        List<GameCard> starters = parser.parseStartCards();
        GameCard gc = golds.get(3);               // 43
        GameCard rc = resses.get(5);             // 5
        GameCard sc = starters.get(0);          // 80
        GameCard gc1 = golds.get(4);            // 44

        Player Player1 = new Player("emanuele");

        // Aggiungi la prima carta e verifica che sia presente
        Player1.addCardInHand(sc);
        assertEquals(1, Player1.getCardsInHand().size());
        assertEquals(sc, Player1.getCardsInHand().get(0));

        Player1.addCardInHand(gc);
        assertEquals(2, Player1.getCardsInHand().size());
        assertEquals(gc, Player1.getCardsInHand().get(1));

        // Aggiungi la terza carta e verifica che sia presente
        Player1.addCardInHand(rc);
        assertEquals(3, Player1.getCardsInHand().size());
        assertEquals(rc, Player1.getCardsInHand().get(2));

        // Aggiungi una quarta carta e verifica che lanci un'eccezione quando la lista è piena
        assertThrows(IllegalStateException.class, () -> {
            Player1.addCardInHand(gc1);
        });

    }

    @Test
    public void testRemoveCardFromHand() {
        JSONParser parser = new JSONParser();
        List<GameCard> golds = parser.parseGoldCards();
        List<GameCard> resses = parser.parseResourceCards();
        List<GameCard> starters = parser.parseStartCards();
        GameCard gc = golds.get(3); // Esempio di carta gold
        GameCard rc = resses.get(5); // Esempio di carta resource
        GameCard sc = starters.get(0); // Esempio di carta start

        Player player1 = new Player("emanuele"); // Supponendo che il costruttore di Player richieda il nome del giocatore

        // Aggiungi la prima carta
        player1.addCardInHand(sc);

        // Aggiungi la seconda carta
        player1.addCardInHand(gc);

        // Aggiungi la terza carta
        player1.addCardInHand(rc);

        // Rimuovi la prima carta
        player1.removeCardFromHand(sc);
        assertEquals(2, player1.getCardsInHand().size());
        assertEquals(gc, player1.getCardsInHand().get(0));
        assertEquals(rc, player1.getCardsInHand().get(1));

        // Rimuovi la seconda carta
        player1.removeCardFromHand(gc);
        assertEquals(1, player1.getCardsInHand().size());
        assertEquals(rc, player1.getCardsInHand().get(0));

        // Rimuovi l'ultima carta
        player1.removeCardFromHand(rc);
        assertEquals(0, player1.getCardsInHand().size());

        // Tentativo di rimuovere una carta quando la lista è vuota
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            player1.removeCardFromHand(new GameCard());
        });
        assertEquals("The hand is empty", exception.getMessage());
    }

    @Test
    public void placePlayingGrid() throws IllegalRequirementsException, InvalidPlacementException {
        //place startcard creando la startcard e facendo grid[40][40] = st;
        JSONParser parser = new JSONParser();
        List<GameCard> golds = parser.parseGoldCards();
        List<GameCard> resources = parser.parseResourceCards();
        List<GameCard> starters = parser.parseStartCards();

        GameCard st = starters.getFirst();
        st.setValidSide(true);
        Player player = new Player("andrea");

        int[] numElements = {10, 15, 200, 30, 1, 50, 60}; // Esempio di array di numeri di elementi
        PlayingGrid playingGrid = player.getPlayingGrid();
        GameCard[][] grid = playingGrid.getPlcards();

        grid[40][40] = st;

        //check accumulo placeando carta numero 7 attorno a start card
        GameCard rs_7 = resources.get(7);
        rs_7.setValidSide(true);
        //qua dovrei fare la place e controllare che ci sia 1 pto
        player.placePlayingGrid(39,39,rs_7);
        int place = player.getPoints();
        assertEquals(1, place);

        //check non cambia niente placeando dopo carta 6 in un altro punto valido
        GameCard rs_6 = resources.get(6);
        rs_6.setValidSide(true);

        player.placePlayingGrid(39,41,rs_6);
        int place_1 = player.getPoints();
        assertEquals(1, place_1);
    }
}
