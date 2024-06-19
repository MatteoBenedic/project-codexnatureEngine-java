package it.polimi.ingsw.am12.Client.UI.CLI;

import it.polimi.ingsw.am12.Model.Logic.PlayerColour;

/**
 * Class that defines the data to print for every player in the intermediate classification
 */
public class CLIClassificationObject {
    private int points;
    private ColourCLI colour;
    private final String nickname;

    /**
     * Class Constructor
     * @param nickname the nickname of the player this class represents
     */
    CLIClassificationObject(String nickname){
        this.nickname = nickname;
    }

    /**
     * @return the name of the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return the current points of the player
     */
    public int getPoints() {
        return points;
    }

    /**
     * @return the colour chosen by the player
     */
    public ColourCLI getColour() {
        return colour;
    }

    /**
     * Sets the new number of points of the player
     * @param points the new number of points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Sets the colour of the player
     * @param col the colour of the player
     */
    public void setColour(String col) {
        try {
            switch(col){
                case "red":
                    colour = ColourCLI.RED;
                    break;
                case "green":
                    colour = ColourCLI.GREEN;
                    break;
                case "yellow":
                    colour = ColourCLI.YELLOW;
                    break;
                case "blue":
                    colour = ColourCLI.BLUE;
                    break;
                default:
                    break;
            }
        }catch (NullPointerException ignored){}
    }
}
