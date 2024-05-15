package it.polimi.ingsw.am12.CLI;

import it.polimi.ingsw.am12.ColourCLI;

import java.util.ArrayList;
import java.util.List;

public class CliCard {
    int index;
    List<String> representation = new ArrayList<>();
    ColourCLI colour;
    List<String> colouredRep;



    public void defineColouredRep(){
        colouredRep = new ArrayList<>();
        for(int i = 0; i < representation.size(); i++){
            String str = colour.getColour() + representation.get(i);

            colouredRep.add(str);
        }
    }

    public int getIndex() {
        return index;
    }

    public List<String> getColouredRep() {
        return colouredRep;
    }
}
