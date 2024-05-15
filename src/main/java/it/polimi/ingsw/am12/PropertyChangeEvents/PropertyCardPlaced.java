package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.CLIDrawBufferGrid;
import it.polimi.ingsw.am12.GUI;
import it.polimi.ingsw.am12.Utils.Coordinate;

/**
 * A new card has been placed on a playing grid
 */
public class PropertyCardPlaced implements PropertyChange {

    String nickname;
    int cardIndex;
    boolean side;
    Coordinate position;

    /**
     * Class constructor
     * @param nickname the player who placed a card
     * @param cardIndex the index of the placed card
     * @param side the side on which it was placed (TRUE = front, FALSE = back)
     * @param position the position on the playing grid
     */
    public PropertyCardPlaced(String nickname, int cardIndex, boolean side, Coordinate position) {
        this.nickname = nickname;
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
        System.out.println(nickname + "'s playing area updated: ");
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
