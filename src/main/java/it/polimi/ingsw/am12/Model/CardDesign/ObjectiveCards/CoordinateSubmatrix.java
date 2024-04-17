package it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards;

/**
 * This class represents a coordinate in the submatrix to search for calculating the completions of a PatternObjectiveCard
 */
public class CoordinateSubmatrix {
    int x;
    int y;
    String colour;

    /**
     * constructor method for a submatrix coordinate
     * @param x int x value in the submatrix
     * @param y int y value in the submatrix
     * @param colour the colour required by the pattern
     * @throws IllegalRequirementsException if an attempt to instatiate a new CoordinateSubmatrix object with invalid values is made
     */
    public CoordinateSubmatrix(int x, int y, String colour) throws IllegalRequirementsException {

        if(x >= 0 && x < 81 && y>=0 && y < 81) {
            this.x = x;
            this.y = y;
        }
        else{
            throw new IllegalRequirementsException("Cannot support coordinates outside the assigned PlayingGrid boundaries");
        }

        if(colour.equals("red") || colour.equals("green") || colour.equals("blue") || colour.equals("purple")) {
            this.colour = colour;
        }
        else{
            throw new IllegalRequirementsException("colour should be either red, green, blue or purple for submatrix coordinate");
        }
    }

    /**
     * getter method for the x submatrix coordinate
     * @return int value representing x
     */

    public int getX() {
        return x;
    }


    /**
     * getter method for the y submatrix coordinate
     * @return int value representing y
     */
    public int getY() {
        return y;
    }

    /**
     * getter method for the colour of the card in the submatrix
     * @return a string representing the colour
     */
    public String getColour() {

        return colour;
    }

}
