package it.polimi.ingsw.am12.Client.UI.CLI;

import java.util.List;

/**
 * Interfaces used by all the buffers in the CLI. These buffers are used for the graphical part of the CLI, in particular
 * to print informations in a more pleasant way and that is more similar to reality
 */
public interface CLIDrawBuffer {
    static final int HEIGHT_CARD = 6;
    static final int LENGTH_CARD = 17;
    static final int COVERED_HEIGHT = 2;
    static final int COVERED_LENGTH = 5;
    static final int COD_COLOUR = 5;
    static final int DELTA_BACKS = 86;
    static final int DELTA_STARTBACKS = 10;
    static final String SPACE = " ";

    /**
     * Method to transform the integer value, the index, that travels in the network in the graphical and printable way, a
     * list of strings
     * @param index the integer value corresponding to a exact card
     * @param side a boolean that represents the side of the card wanted by the player
     * @return a list of strings, the representation of the card
     */
    List<String> extractCardfromIndex(int index, boolean side);

    /**
     * Method used to print the buffer in the CLI
     */
    void printBuffer();


}
