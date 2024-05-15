package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.CLI.CliCard;
import it.polimi.ingsw.am12.Utils.Coordinate;
import it.polimi.ingsw.am12.Utils.JSONParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CLIDrawBufferGridTest {

    JSONParser jsonParser = new JSONParser();
    List<CliCard> cards = jsonParser.parseCLICards();


    @Test
    void printNewTopLeft(){
        for(CliCard c : cards)
            c.defineColouredRep();

        CLIDrawBufferGrid grid = new CLIDrawBufferGrid(cards);
        Coordinate position = new Coordinate(40, 40);
        grid.insertCardInBuffer(80, true, position);
        grid.printBuffer();

        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        Coordinate position1 = new Coordinate(39, 39);
        grid.insertCardInBuffer(1, true, position1);
        grid.printBuffer();
    }

    @Test
    void printNewTopRight(){
        for(CliCard c : cards)
            c.defineColouredRep();

        CLIDrawBufferGrid grid = new CLIDrawBufferGrid(cards);
        Coordinate position = new Coordinate(40, 40);
        grid.insertCardInBuffer(80, true, position);
        grid.printBuffer();

        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        Coordinate position1 = new Coordinate(39, 41);
        grid.insertCardInBuffer(1, true, position1);
        grid.printBuffer();
    }
}