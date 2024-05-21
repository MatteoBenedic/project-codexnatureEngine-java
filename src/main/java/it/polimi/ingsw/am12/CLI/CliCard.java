package it.polimi.ingsw.am12.CLI;

import it.polimi.ingsw.am12.ColourCLI;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents the printable interface of the playing cards in the CLI, used to have a coloured command line
 * representation
 */
public class CliCard {
    int index;
    List<String> representation = new ArrayList<>();
    ColourCLI colour;
    List<String> colouredRep;


    /**
     * Given the not coloured representation and the colour of the cards,
     * creates the coloured representation to use in the buffers
     */
    public void defineColouredRep(){
        colouredRep = new ArrayList<>();
        for(int i = 0; i < representation.size(); i++){
            String str = colour.getColour() + representation.get(i);

            colouredRep.add(str);
        }
    }

    /**
     * @return the index of the card
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return the coloured representation of the card, packed in a list of strings
     */
    public List<String> getColouredRep() {
        return colouredRep;
    }
}
