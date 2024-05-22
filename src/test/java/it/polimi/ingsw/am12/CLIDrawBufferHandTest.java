package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.Client.UI.CLI.CLIDrawBufferHand;
import it.polimi.ingsw.am12.Client.UI.CLI.CliCard;
import it.polimi.ingsw.am12.Utils.JSONParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CLIDrawBufferHandTest {

    JSONParser jsonParser = new JSONParser();
    List<CliCard> cards = jsonParser.parseCLICards();
    static final int SPACE_WANTED = 3;

    @Test
    void createInitialHand(){
        for(CliCard c : cards)
            c.defineColouredRep();

        CLIDrawBufferHand hand = new CLIDrawBufferHand(cards);
        //hand with start card only
        List<Integer> startcard = new ArrayList<>();
        startcard.add(80);
        hand.insertCardsInBuffer(startcard);
        hand.printBuffer();

        for(int i = 0; i < SPACE_WANTED; i++)
            System.out.println(" ");

        //first hand
        List<Integer> firsthand = new ArrayList<>();
        firsthand.add(3);
        firsthand.add(52);
        firsthand.add(31);
        hand.insertCardsInBuffer(firsthand);
        hand.printBuffer();
    }

}