package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.CLI.CliCard;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;
import it.polimi.ingsw.am12.Utils.JSONParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CLIDrawBufferTableTest {

    JSONParser jsonParser = new JSONParser();
    List<CliCard> cards = jsonParser.parseCLICards();

    @Test
    void FirstDrawTable(){
        for(CliCard c : cards)
            c.defineColouredRep();

        CLIDrawBufferTable table = new CLIDrawBufferTable(cards);
        table.insertCardInBuffer(3, 3);
        table.printBuffer();
        table.insertCardInDeck(4, CardColour.GREEN);
        table.printBuffer();
    }

}