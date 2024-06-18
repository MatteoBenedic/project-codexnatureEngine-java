package it.polimi.ingsw.am12.Client.UI.CLI;

import java.util.Collections;
import java.util.List;

/**
 * Buffer to save the printable version of the player's public and private objectives
 */
public class CLIObjectivesBuffer implements CLIDrawBuffer{
    private List<CliObjCard> objCards;
    private static final int NUM_PUB_OBJECTIVES = 2;
    private String[] pubDescriptions = new String[NUM_PUB_OBJECTIVES];
    private String secretDescription;

    /**
     * Class Constructor
     * @param objCards
     */
    public CLIObjectivesBuffer(List<CliObjCard> objCards) {
        this.objCards = objCards;
        secretDescription = null;
    }

    /**
     * Method used to set the public objectives
     * @param index the index of the objective to save in the buffer
     */
    public void setPublicObjectives(int index){
        if(pubDescriptions[0] == null){
            pubDescriptions[0] = extractCardfromIndex(index, true).getFirst();
        }else
            pubDescriptions[1] = extractCardfromIndex(index, true).getFirst();
    }

    /**
     * Method used to set the private objective
     * @param index the index of the objective to save in the buffer
     */
    public void setSecretObjective(int index){
        secretDescription = extractCardfromIndex(index, true).getFirst();
    }

    /**
     * Method used to extract the right description of the card starting by an integer value
     * @param index the integer value corresponding to a exact card
     * @param side a boolean that represents the side of the card wanted by the player
     * @return the objective description to save and print
     */
    @Override
    public List<String> extractCardfromIndex(int index, boolean side) {
        String desc = null;
        for(CliObjCard o: objCards)
            if(index == o.getIndex())
                desc = o.getDescription();

        return Collections.singletonList(desc);
    }

    /**
     * Method used to print the description of the objective cards requested from the user
     */
    @Override
    public void printBuffer() {
        if (pubDescriptions[0] != null && pubDescriptions[1] != null) {
            System.out.println("Public objectives:");
            System.out.println(" ");
            System.out.println(pubDescriptions[0]);
            System.out.println(" ");
            System.out.println(pubDescriptions[0]);
            System.out.println(" ");
            System.out.println(" ");
        }
        if (secretDescription != null) {
            System.out.println("Your secret objective:");
            System.out.println(" ");
            System.out.println(secretDescription);
        }
    }
}
