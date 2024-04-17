package it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards;
import it.polimi.ingsw.am12.Model.Logic.PlayingGrid;

/**
 * ElementsObjectiveCard represents an objective card that requires
 * to have a certain number of Elements in order
 * for it to be completed: extends a general ObjectiveCard
 */
public class ElementsObjectiveCard extends ObjectiveCard {
    private int[] reqElements;

    /**
     * constructor method for an ElementObjectiveCard object
     */
    public ElementsObjectiveCard(int objIndex, int points, int[] reqElements) throws IllegalRequirementsException {
        super(objIndex, points);
        if(reqElements.length != 7){
            throw new IllegalRequirementsException("Expected an array of 7 required elements");
        }
        this.reqElements = reqElements;
    }

    /**
     * override of the calculatePoints method: this method calculates the final points to be assigned to a player, given the number of completions of a ElementsObjectiveCard
     * @param placedCards a PlayingGrid instance
     * @return the final number of points for the completions of the ElementsObjectiveCard
     */
    @Override
    public int calculatePoints(PlayingGrid placedCards){
        int completions = checkElements(placedCards);
        int assignedPoints = getPoints() * completions;
        return assignedPoints;
    }


    /**
     * this method checks how many element objective completions are made in the playing grid
     * @param placedCards PlayingGrid instance
     * @return int value representing the number of completions
     */
    private int checkElements(PlayingGrid placedCards){
        int min = 0;
        for(int i=0; i<7; i++){

            if(placedCards.getNumElements()[i]<reqElements[i]){
                return 0;
            }

            if(reqElements[i]!=0){
                int temp =placedCards.getNumElements()[i] / reqElements[i];
                if(min==0){
                    min=temp;
                }
                else if(min > 0){
                    min = Math.min(temp,min);
                }
            }
        }
        return min;
    }

}
