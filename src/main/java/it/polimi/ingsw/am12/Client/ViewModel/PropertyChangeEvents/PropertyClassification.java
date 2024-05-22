package it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents;

import it.polimi.ingsw.am12.Client.UI.CLI.CLI;
import it.polimi.ingsw.am12.Client.UI.Gui.GUI;
import java.util.LinkedHashMap;

/**
 * The game has ended and the classification is available
 */
public class PropertyClassification implements PropertyChange {

    int numWinners;
    LinkedHashMap<String, Integer> classification;

    /**
     * Class constructor
     * @param numWinners the number of winners
     * @param classification the classification of the match, with the final points of each player
     */
    public PropertyClassification(int numWinners, LinkedHashMap<String, Integer> classification) {
        this.numWinners = numWinners;
        this.classification = classification;
    }

    /**
     * Update the CLI with the classification
     * @param cli the CLI
     */
    @Override
    public void updateCLI(CLI cli) {
       System.out.println("Winners: ");
       String[] players = (String[]) classification.sequencedKeySet().toArray();
       for(int i=0; i<numWinners; i++) {
           System.out.println(players[i] + " with " + classification.get(players[i]) + " points");
       }
       System.out.println("Non winners: ");
        for(int i=numWinners; i< players.length; i++) {
            System.out.println(players[i] + " with " + classification.get(players[i]) + " points");
        }
    }

    /**
     * Update the GUI with the classification
     * @param gui the GUI
     */
    @Override
    public void updateGUI(GUI gui) {

    }
}
