package it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards;

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
     * getter method for a coordinate object in the coordReqPattern array, given the array index
     * @param arrayIndex an int value representing the index in the array of the submatrix coordinate to return
     * @return a CoordinateSubmatrix object
     */
    public CoordinateSubmatrix getCoordReqPattern(int arrayIndex) {
        return coordReqPattern[arrayIndex];
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
     * Override of the calculatePoints method: this method calculates the final points to be assigned to a player, given the number of completions of a PatternObjectiveCard
     * @param placedCards a PlayingGrid instance
     * @return the final number of points for the completions of the PatternObjectiveCard
     */
    @Override
    public int calculatePoints(PlayingGrid placedCards){
        int completions = checkPatterns(placedCards);
        int assignedPoints = getPoints() * completions;
        return assignedPoints;
    }


    /**
     * This method calculates how many pattern objective completions are made in the playing grid
     * @param placedCards a PlayingGrid instance
     * @return int value representing the number of completions
     */
    private int checkPatterns(PlayingGrid placedCards){
        int completions = 0;

        for(int i=0; i<placedCards.getPlcards().length; i++){
            for(int j=0; j<placedCards.getPlcards()[i].length; j++){
                if(!placedCards.cellIsEmpty(i,j) && placedCards.getPlcards()[i][j].getColour()!=null){
                    if(placedCards.checkColourMatch(i,j,this,0)
                            && !(placedCards.cardWasAlreadyUsedForThisObjective(i,j,this))){
                        //at this point working starting from the master cell
                        if(i+deltaYSecond >=0 && i+deltaYSecond <= placedCards.getPlcards().length && j+deltaXSecond>=0 && j+deltaXSecond <= placedCards.getPlcards()[i].length &&
                                i+deltaYThird >=0 && i+deltaYThird <= placedCards.getPlcards().length && j+deltaXThird>=0 && j+deltaXThird <= placedCards.getPlcards()[i].length) {
                            //checking colour match
                            if (placedCards.checkColourMatch(i + deltaYSecond, j + deltaXSecond, this, 1)
                                    && placedCards.checkColourMatch(i + deltaYThird, j + deltaXThird, this, 2)) {
                                //checking whether already used
                                if (!(placedCards.cardWasAlreadyUsedForThisObjective(i + deltaYSecond, j + deltaXSecond, this) &&
                                        !(placedCards.cardWasAlreadyUsedForThisObjective(i + deltaYThird, j + deltaXThird, this)))) {

                                    placedCards.markCardAsUsedForThisObjective(i, j, this);
                                    placedCards.markCardAsUsedForThisObjective(i + deltaYSecond, j + deltaXSecond, this);
                                    placedCards.markCardAsUsedForThisObjective(i + deltaYThird, j + deltaXThird, this);
                                    completions = completions + 1;
                                }
                            }
                        }
                    }
                }

            }
        }

        return completions;
    }

}
