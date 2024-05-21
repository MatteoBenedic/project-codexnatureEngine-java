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


    @Test
    void printNewBottomLeft(){
        for(CliCard c : cards)
            c.defineColouredRep();

        CLIDrawBufferGrid grid = new CLIDrawBufferGrid(cards);
        Coordinate position = new Coordinate(40, 40);
        grid.insertCardInBuffer(80, true, position);
        grid.printBuffer();

        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        Coordinate position1 = new Coordinate(41, 39);
        grid.insertCardInBuffer(1, true, position1);
        grid.printBuffer();
    }

    @Test
    void printNewBottomRight(){
        for(CliCard c : cards)
            c.defineColouredRep();

        CLIDrawBufferGrid grid = new CLIDrawBufferGrid(cards);
        Coordinate position = new Coordinate(40, 40);
        grid.insertCardInBuffer(80, true, position);
        grid.printBuffer();

        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        Coordinate position1 = new Coordinate(41, 41);
        grid.insertCardInBuffer(1, true, position1);
        grid.printBuffer();
    }

    @Test
    void printProgressiveGrid(){
        for(CliCard c : cards)
            c.defineColouredRep();

        CLIDrawBufferGrid grid = new CLIDrawBufferGrid(cards);
        Coordinate position = new Coordinate(40, 40);
        grid.insertCardInBuffer(80, true, position);
        grid.printBuffer();

        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        Coordinate p1 = new Coordinate(41, 41);
        grid.insertCardInBuffer(1, true, p1);
        grid.printBuffer();

        Coordinate p2 = new Coordinate(39, 39);
        grid.insertCardInBuffer(51, true, p2);
        grid.printBuffer();

        Coordinate p3 = new Coordinate(38, 40);
        grid.insertCardInBuffer(23, true, p3);
        grid.printBuffer();

        Coordinate p4 = new Coordinate(40, 38);
        grid.insertCardInBuffer(37, false, p4);
        grid.printBuffer();

        Coordinate p5 = new Coordinate(38, 38);
        grid.insertCardInBuffer(44, false, p5);
        grid.printBuffer();

        Coordinate p6 = new Coordinate(42, 40);
        grid.insertCardInBuffer(18, true, p6);
        grid.printBuffer();

        Coordinate p7 = new Coordinate(43, 41);
        grid.insertCardInBuffer(22, true, p7);
        grid.printBuffer();

        Coordinate p8 = new Coordinate(44, 40);
        grid.insertCardInBuffer(9, true, p8);
        grid.printBuffer();

        Coordinate p9 = new Coordinate(40, 42);
        grid.insertCardInBuffer(9, true, p9);
        grid.printBuffer();

        Coordinate p10 = new Coordinate(39, 41);
        grid.insertCardInBuffer(11, true, p10);
        grid.printBuffer();
    }
}