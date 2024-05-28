package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.Client.ClientController.ClientController;
import it.polimi.ingsw.am12.Client.ClientController.ClientControllerRMI;
import it.polimi.ingsw.am12.Client.UI.CLI.CLIDrawBufferTable;
import it.polimi.ingsw.am12.Client.UI.CLI.CliCard;
import it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents.PropertyDeckColour;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;
import it.polimi.ingsw.am12.Utils.JSONParser;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.List;

class CLIDrawBufferTableTest {

    JSONParser jsonParser = new JSONParser();
    List<CliCard> cards = jsonParser.parseCLICards();

    @Test
    void FirstCardsDrawTable(){
        for(CliCard c : cards)
            c.defineColouredRep();

        CLIDrawBufferTable table = new CLIDrawBufferTable(cards);
        table.insertCardInBuffer(3, 3);
        table.printBuffer();
        table.insertCardInDeck(4, CardColour.GREEN);
        table.printBuffer();
    }

    @Test
    void completeDrawTable(){
        for(CliCard c : cards)
            c.defineColouredRep();

        CLIDrawBufferTable table = new CLIDrawBufferTable(cards);
        table.insertCardInBuffer(40, 0);
        table.printBuffer();
        table.insertCardInBuffer(50, 1);
        table.printBuffer();
        table.insertCardInBuffer(0, 2);
        table.printBuffer();
        table.insertCardInBuffer(10, 3);
        table.printBuffer();
        table.insertCardInDeck(4, CardColour.RED);
        table.printBuffer();
        table.insertCardInDeck(5, CardColour.GREEN);
        table.printBuffer();
    }

    @Test
    void replaceDrawTable(){
        for(CliCard c : cards)
            c.defineColouredRep();

        CLIDrawBufferTable table = new CLIDrawBufferTable(cards);
        table.insertCardInBuffer(40, 0);
        table.insertCardInBuffer(50, 1);
        table.insertCardInBuffer(0, 2);
        table.insertCardInBuffer(10, 3);
        table.insertCardInDeck(4, CardColour.RED);
        table.insertCardInDeck(5, CardColour.GREEN);
        table.printBuffer();

        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        table.insertCardInBuffer(41, 1);
        table.insertCardInDeck(4, CardColour.PURPLE);
        table.printBuffer();

        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        table.insertCardInBuffer(11, 2);
        table.insertCardInDeck(5, CardColour.BLUE);
        table.printBuffer();
    }

    @Test
    void replaceToEmptyDeck(){
        for(CliCard c : cards)
            c.defineColouredRep();

        CLIDrawBufferTable table = new CLIDrawBufferTable(cards);
        table.insertCardInBuffer(40, 0);
        table.insertCardInBuffer(50, 1);
        table.insertCardInBuffer(0, 2);
        table.insertCardInBuffer(10, 3);
        table.insertCardInDeck(4, CardColour.RED);
        table.insertCardInDeck(5, CardColour.GREEN);
        table.printBuffer();

        table.insertCardInDeck(5, null);
        table.printBuffer();

        table.insertCardInDeck(5, null);
        table.printBuffer();

        table.insertCardInDeck(4, null);
        table.printBuffer();
    }
}