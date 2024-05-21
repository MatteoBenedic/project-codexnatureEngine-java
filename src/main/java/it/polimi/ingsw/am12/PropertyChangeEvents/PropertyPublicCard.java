package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.CLIDrawBufferTable;
import it.polimi.ingsw.am12.Gui.GUI;

/**
 * There is a new public card on the table
 */
public class PropertyPublicCard implements PropertyChange{

    int deckIndex;
    int cardIndex;

    /**
     * Class constructor
     * @param deckIndex an int that specifies which public card ha changed
     *                    0 --> first gold
     *                    1 --> second gold
     *                    2 --> first resource
     *                    3 --> second resource
     * @param cardIndex the index of the new public card
     */
    public PropertyPublicCard(int deckIndex, int cardIndex) {
        this.deckIndex = deckIndex;
        this.cardIndex = cardIndex;
    }

    /**
     * Update the CLI with the new public card
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        CLIDrawBufferTable drawtable = cli.getDrawtable();
        drawtable.insertCardInBuffer(cardIndex, deckIndex);
    }

    /**
     * Update the GUI with the new public card
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
