package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.CLIDrawBufferTable;
import it.polimi.ingsw.am12.Gui.GUI;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;

/**
 * There is a new colour of the gold deck or the resource deck
 */
public class PropertyDeckColour implements PropertyChange{

    CardColour colour;
    int deckIndex;
    boolean print;

    /**
     * Class constructor
     * @param colour the new deck colour
     * @param deckIndex an int:
     *                   4 if the new colour refers to the gold deck;
     *                   5 if the new colour refers to the resource deck;
     * @param print a boolean: TRUE is the draw table has to be printed, otherwise FALSE
     */
    public PropertyDeckColour(CardColour colour, int deckIndex, boolean print) {
        this.colour = colour;
        this.deckIndex = deckIndex;
        this.print = print;
    }

    /**
     * Update the CLI with the new deck colour
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        CLIDrawBufferTable drawtable = cli.getDrawtable();
        drawtable.insertCardInDeck(deckIndex, colour);
        if(print) {
            System.out.println("Draw table:");
            drawtable.printBuffer();
        }
    }

    /**
     * Update the GUI with the new deck colour
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
