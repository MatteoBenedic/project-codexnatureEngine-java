package it.polimi.ingsw.am12.Client.UI.CLI;

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
    List<String> back = new ArrayList<>();
    List<String> colouredRep;
    List<String> colouredBack;


    /**
     * Given the not coloured representation and the colour of the cards,
     * creates the coloured representation to use in the buffers
     */
    public void defineColouredRep(){
        colouredRep = new ArrayList<>();
        colouredBack = new ArrayList<>();
        for(int i = 0; i < representation.size(); i++){
            String str = colour.getColour() + representation.get(i);

            colouredRep.add(str);
        }

        for(int i = 0; i < back.size(); i++){
            String str = colour.getColour() + back.get(i);

            colouredBack.add(str);
        }
    }

    /**
     * @return the index of the card
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param side the side to represent
     * @return the coloured representation of the side choosen of the card, packed in a list of strings
     */
    public List<String> getColouredRep(boolean side) {
        if(side)
            return colouredRep;
        return colouredBack;
    }
}
