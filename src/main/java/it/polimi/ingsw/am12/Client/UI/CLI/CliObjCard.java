package it.polimi.ingsw.am12.Client.UI.CLI;


/**
 * Class that represents the printable interface of the objective cards in the CLI, defined by a description that helps
 * the player to understand how to do more points
 */
public class CliObjCard {
    int index;
    String description;

    /**
     * @return the index of the card
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return a string that defines the description of the card, that describes the objective itself
     */
    public String getDescription() {
        return description;
    }
}
