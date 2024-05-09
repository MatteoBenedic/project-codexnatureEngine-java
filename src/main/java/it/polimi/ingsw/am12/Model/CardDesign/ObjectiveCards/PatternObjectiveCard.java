package it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards;

import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;
import it.polimi.ingsw.am12.Model.Logic.PlayingGrid;

/**
 * PatternObjectiveCard represents an objective card which requires
 * to search for a certain pattern in the PlayingGrid in order to be completed:
 * extends a general ObjectiveCard.
 */
public class PatternObjectiveCard extends ObjectiveCard{
    private CoordinateSubmatrix coordReqPattern[];
    private int deltaXSecond;
    private int deltaYSecond;
    private int deltaXThird;
    private int deltaYThird;


    /**
     * constructor method for a pattern objective card
     * @param coordReqPattern an array of 3 CoordinateSubmatrix that represent the pattern
     * @param objIndex the int index of the objective card
     * @param points the number of points that the cards gives each time the pattern is found
     * @throws IllegalRequirementsException if the length of coordReqPattern is not 3.
     */
    public PatternObjectiveCard(CoordinateSubmatrix[] coordReqPattern, int objIndex, int points) throws IllegalRequirementsException {
        super(objIndex, points);

        if (coordReqPattern.length != 3) {
            throw new IllegalRequirementsException("Expected an array of exactly three coordinates but found a different length");
        }
        this.coordReqPattern = coordReqPattern;

    }


    /**
     * method that calculates the delta attributes for this PatternObjectiveCard object
     */
    @Override
    public void calculateDeltas(){
        this.deltaXSecond = this.coordReqPattern[1].getX()-this.coordReqPattern[0].getX();
        this.deltaYSecond = this.coordReqPattern[1].getY()-this.coordReqPattern[0].getY();
        this.deltaXThird = this.coordReqPattern[2].getX()-this.coordReqPattern[0].getX();
        this.deltaYThird = this.coordReqPattern[2].getY()-this.coordReqPattern[0].getY();
    }

    /**
     * getter method for a coordinate object in the coordReqPattern array, given the array index
     * @param requiredIndex an int value representing the index in the array of the submatrix coordinate to return
     * @return a CoordinateSubmatrix object
     */
    public CoordinateSubmatrix getCoordReqPattern(int requiredIndex) {
        return coordReqPattern[requiredIndex];
    }

    /**
     * Override of the calculatePoints method: this method calculates the final points to be assigned to a player, given the number of completions of a PatternObjectiveCard
     * @param grid a PlayingGrid instance
     * @return the final number of points for the completions of the PatternObjectiveCard
     */
    @Override
    public int calculatePoints(PlayingGrid grid){
        int completions = checkPatterns(grid);
        int pointsAdded = getPoints() * completions;
        return pointsAdded;
    }


    /**
     * This method calculates how many pattern objective completions are made in the playing grid
     * @param grid a PlayingGrid instance
     * @return int value representing the number of completions
     */
    private int checkPatterns(PlayingGrid grid){
        //v.1.2
        int objCompletions = 0;
        for(int i=0; i<grid.getPlcards().length; i++){
            for(int j=0; j<grid.getPlcards()[i].length; j++){
                if(!grid.cellIsEmpty(i,j) && grid.getPlcards()[i][j].getColour()!= null){
                    if(grid.checkColourMatch(i,j,this,0)
                            && !(grid.cardWasAlreadyUsedForThisObjective(i,j,this))){
                        //From here: working starting from the master cell
                        if(i+deltaYSecond >=0 && i+deltaYSecond <= grid.getPlcards().length && j+deltaXSecond>=0 && j+deltaXSecond <= grid.getPlcards()[i].length &&
                                i+deltaYThird >=0 && i+deltaYThird <= grid.getPlcards().length && j+deltaXThird>=0 && j+deltaXThird <= grid.getPlcards()[i].length) {
                            //checking whether the colours match
                            if (grid.checkColourMatch(i + deltaYSecond, j + deltaXSecond, this, 1)
                                    && grid.checkColourMatch(i + deltaYThird, j + deltaXThird, this, 2)) {
                                //checking whether the cards were already used
                                if (!(grid.cardWasAlreadyUsedForThisObjective(i + deltaYSecond, j + deltaXSecond, this) &&
                                        !(grid.cardWasAlreadyUsedForThisObjective(i + deltaYThird, j + deltaXThird, this)))) {
                                    grid.markCardAsUsedForThisObjective(i, j, this);
                                    grid.markCardAsUsedForThisObjective(i + deltaYSecond, j + deltaXSecond, this);
                                    grid.markCardAsUsedForThisObjective(i + deltaYThird, j + deltaXThird, this);
                                    objCompletions = objCompletions + 1;
                                }
                            }
                        }
                    }
                }

            }
        }
        return objCompletions;
    }
}
