package it.polimi.ingsw.am12;

import java.util.List;

public interface CLIDrawBuffer {
    static final int HEIGHT_CARD = 6;
    static final int LENGTH_CARD = 17;
    static final int COVERED_HEIGHT = 2;
    static final int COVERED_LENGTH = 5;
    static final int COD_COLOUR = 5;
    static final int DELTA_BACKS = 86;
    static final int DELTA_STARTBACKS = 10;
    static final String SPACE = " ";

    List<String> extractCardfromIndex(int index, boolean side);

    void printBuffer();


}
