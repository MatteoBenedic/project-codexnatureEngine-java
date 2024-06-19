package it.polimi.ingsw.am12.Client.UI.CLI;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains the intermediate classification to print when every player turn ends
 */
public class CLIClassificationBuffer {
    List<CLIClassificationObject> players = new ArrayList<>();

    /**
     * Inserts the new player in the match in the list of players of the classification
     * @param nickname the name of the player
     */
    public void insertPlayer(String nickname){
        players.add(new CLIClassificationObject(nickname));
    }

    /**
     * Sets the colour of the specific player that has just chosen its colour
     * @param nickname the name of the player
     * @param colour the colour that the player chose
     */
    public void setColour(String nickname, String colour){
        for(CLIClassificationObject p : players)
            if(p.getNickname().equals(nickname))
                p.setColour(colour);
    }

    /**
     * Sets the new number of points updated after a card placing move
     * @param nickname the name of the player who achieved new points
     * @param points the new number of points
     */
    public void setPoints(String nickname, int points){
        for(CLIClassificationObject p : players)
            if(p.getNickname().equals(nickname))
                p.setPoints(points);
    }

    /**
     * Prints the classification
     */
    public void printBuffer(){
        System.out.println("Classification: ");
        for(CLIClassificationObject p : players)
            System.out.println(p.getColour().getColour() + p.getNickname() + " has " + p.getPoints() +
                    " points" + ColourCLI.RESET.getColour());
    }
}
