package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.CLI.CLIDrawBufferHand;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import java.util.List;

/**
 * The player has new cards in hand
 */
public class PropertyCardInHand implements PropertyChange{

    List<Integer> cards;

    /**
     * Class constructor
     * @param cards a list with the indexes of the cards in hand
     */
    public PropertyCardInHand(List<Integer> cards) {
        this.cards = cards;
    }

    /**
     * Update the CLI with the new card in hand
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        CLIDrawBufferHand hand = cli.getHand();
        hand.insertCardsInBuffer(cards);
        System.out.println("Your hand:");
        hand.printBuffer();
    }

    /**
     * Update the GUI with the new card in hand
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
