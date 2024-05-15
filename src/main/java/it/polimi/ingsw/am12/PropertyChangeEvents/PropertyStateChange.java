package it.polimi.ingsw.am12.PropertyChangeEvents;

import it.polimi.ingsw.am12.CLI.CLI;
import it.polimi.ingsw.am12.GUI;
import it.polimi.ingsw.am12.Model.Logic.State;

/**
 * The state of the game changed
 */
public class PropertyStateChange implements PropertyChange{

    State newState;

    /**
     * Class constructor
     * @param newState the new state of the game
     */
    public PropertyStateChange(State newState) {
        this.newState = newState;
    }

    /**
     * Update the CLI with the new state
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        switch (newState) {
            case INITIALIZATION -> System.out.println("All the players have joined: type 'startmatch' in order to start the match");
            case STARTCARD -> System.out.println("Place your start card (type 'placestartcard front' or 'placestartcard back'");
            case COLOUR -> System.out.println("Select you colour (type 'selectmycolour red/yellow/green/blue' ");
            case DISTRIBUTION -> System.out.println("Type 'distributecards' to distribute the cards");
            case OBJECTIVE -> System.out.println("Select you secret objective: type 'selectobjective first' or 'selectobjective second'");
            case PLACING -> System.out.println("Place a card: type 'getpos x y' to get the placable positions; type 'placecard index front/back x y' to place");
            case DRAWING -> System.out.println("Draw a card: type 'drawcard 0/1/2/3/4/5'");
            case END -> System.out.println("Type 'endgame' to get the results");
            case CLOSED -> System.out.println("The game has ended: type q to quit");
        }
    }

    /**
     * Update the GUI with the new state
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
