package it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards;

import it.polimi.ingsw.am12.Model.Logic.PlayingGrid;

/**
 * This class represents a general ObjectiveCard in the game
 */
public class ObjectiveCard {
    private int objIndex;
    private int points;

    /**
     * constructor for an ObjectiveCard
     */
    public ObjectiveCard(int objIndex, int points) {
        this.objIndex = objIndex;
        this.points=points;
    }

    /**
     * getter method for the objective index
     * @return the objective index
     */
    public int getObjIndex() {
        return objIndex;
    }


    /**
     * getter method for the number of points associated to the objective card
     * @return the number of points
     */
    public int getPoints() {
        return points;
    }

    /**
     * This method is implemented in PatternObjectiveCard and ElementObjectiveCard classes,
     * in order to take advantage of the override.
     * @return a default value of -1
     */
    public int calculatePoints(PlayingGrid placedCards){
        //this method will be overridden in the PatternObjectiveCard and ElementObjectiveCard classes
        return -1;
    }

    /**
     * This method is implemented in the PatternObjectiveCard class in order to take advantage of the override.
     */
    public void calculateDeltas(){
    }

}
