package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.CLI.CLIDrawBufferGrid;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import it.polimi.ingsw.am12.Utils.Coordinate;

/**
 * A new card has been placed on a playing grid
 */
public class PropertyCardPlaced implements PropertyChange {

    String nickname;
    boolean isYourPlayingGrid;
    int cardIndex;
    boolean side;
    Coordinate position;

    /**
     * Class constructor
     * @param nickname the player who placed a card
     * @param isYourPlayingGrid a boolean:
     *                          TRUE if the card was placed by the player to whom the update is displayed
     *                          FALSE if the card was placed by another player
     * @param cardIndex the index of the placed card
     * @param side the side on which it was placed (TRUE = front, FALSE = back)
     * @param position the position on the playing grid
     */
    public PropertyCardPlaced(String nickname, boolean isYourPlayingGrid, int cardIndex, boolean side, Coordinate position) {
        this.nickname = nickname;
        this.isYourPlayingGrid = isYourPlayingGrid;
        this.cardIndex = cardIndex;
        this.side = side;
        this.position = position;
    }

    /**
     * Update the CLI with the new card on the playing grid
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        CLIDrawBufferGrid grid = cli.getPlayingGrids().get(nickname);
        grid.insertCardInBuffer(cardIndex, side, position);
        if(isYourPlayingGrid)
            System.out.println("Your new playing grid:");
        else
            System.out.println(nickname + "'s new playing grid:");
        grid.printBuffer();
    }

    /**
     * Update the GUI with the new card on the playing grid
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
