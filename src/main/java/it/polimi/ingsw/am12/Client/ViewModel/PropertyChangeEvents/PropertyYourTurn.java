package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;

/**
 * The turn changed and it's your turn
 */
public class PropertyYourTurn implements PropertyChange{

    State newState;

    /**
     * Class constructor
     * @param newState the new state of the game
     */
    public PropertyYourTurn(State newState) {
        this.newState = newState;
    }

    /**
     * Update the CLI with the new turn
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
        if(!newState.equals(State.DISTRIBUTION))
            System.out.println("It's your turn");
        switch (newState) {

            case STARTCARD -> System.out.println("Place your start card: type 'placestartcard front' or 'placestartcard back'");
            case COLOUR -> System.out.println("Select you colour: type 'selectmycolour red/yellow/green/blue' ");
            case OBJECTIVE -> System.out.println("Select you secret objective: type 'selectobjective first' or 'selectobjective second'");
            case PLACING -> System.out.println("Place a card:" +
                    " type 'getpos x y' to get the placeable positions;" +
                    " type 'placecard index front/back x y' to place");

        }
    }

    /**
     * Update the GUI with the new turn
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
