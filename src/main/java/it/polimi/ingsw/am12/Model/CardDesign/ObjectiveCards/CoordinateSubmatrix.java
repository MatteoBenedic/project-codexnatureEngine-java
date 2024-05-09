package it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards;

import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;

/**
 * This class represents a coordinate in the submatrix to search for calculating the completions of a PatternObjectiveCard
 */
public class CoordinateSubmatrix {

    private static final int MAX_NUMBER_OF_ROWS = 81;
    private static final int MAX_NUMBER_OF_COL = 81;

    int x;
    int y;
    CardColour colour;

    /**
     * constructor method for a submatrix coordinate
     * @param x int x value in the submatrix
     * @param y int y value in the submatrix
     * @param colour the colour required by the pattern
     * @throws IllegalRequirementsException if an attempt to instatiate a new CoordinateSubmatrix object with invalid values is made
     */
    public CoordinateSubmatrix(int x, int y, CardColour colour) throws IllegalRequirementsException {

        if(x >= 0 && x < MAX_NUMBER_OF_COL && y>=0 && y < MAX_NUMBER_OF_ROWS) {
            this.x = x;
            this.y = y;
        }
        else{
            throw new IllegalRequirementsException("Cannot support coordinates outside the assigned PlayingGrid boundaries ("+MAX_NUMBER_OF_ROWS+"x"+MAX_NUMBER_OF_COL+")");
        }

        this.colour = colour;

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
    public CardColour getColour() {
        return colour;
    }

}
