package it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards;
import it.polimi.ingsw.am12.Model.Logic.PlayingGrid;

/**
 * ElementsObjectiveCard represents an objective card that requires
 * to have a certain number of Elements in order
 * for it to be completed: extends a general ObjectiveCard
 */
public class ElementsObjectiveCard extends ObjectiveCard {
    private int[] reqElements;

    private static final int MAX_NUMBER_OF_ELEMENTS = 7;

    /**
     * constructor method for an ElementObjectiveCard object
     */
    public ElementsObjectiveCard(int objIndex, int points, int[] requiredElements) throws IllegalRequirementsException {
        super(objIndex,points);
        if(requiredElements.length!=MAX_NUMBER_OF_ELEMENTS) throw new IllegalRequirementsException("Must be an array of 7 required elements");
        this.reqElements = requiredElements;
    }

    /**
     * override of the calculatePoints method: this method calculates the final points to be assigned to a player, given the number of completions of a ElementsObjectiveCard
     * @param grid a PlayingGrid instance
     * @return the final number of points for the completions of the ElementsObjectiveCard
     */
    @Override
    public int calculatePoints(PlayingGrid grid){
        int completions = checkElements(grid);
        int addedPoints = getPoints() * completions;
        return addedPoints;
    }


    /**
     * this method checks how many element objective completions are made in the playing grid
     * @param grid PlayingGrid instance
     * @return int value representing the number of completions
     */
    private int checkElements(PlayingGrid grid){
        int minimum = 0;
        for(int i=0; i<MAX_NUMBER_OF_ELEMENTS; i++){

            if(grid.getNumElements()[i]<reqElements[i]){
                return  0;
            }

            if(reqElements[i]!=0){
                int temp =grid.getNumElements()[i] / reqElements[i];
                if(minimum==0){
                    minimum=temp;
                }
                else if(minimum > 0){
                    minimum = Math.min(temp,minimum);
                }
            }
        }
        return minimum;
    }

}
