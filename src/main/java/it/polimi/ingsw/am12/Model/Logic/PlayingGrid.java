package it.polimi.ingsw.am12.Model.Logic;

import it.polimi.ingsw.am12.Model.CardDesign.ObjectiveCards.*;
import it.polimi.ingsw.am12.Utils.Coordinate;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the playing area of a Player
 */
public class PlayingGrid {

    private GameCard[][] plcards;
    private int[] numElements;
    private GameCard lastPlacedCard;

    private static final int MAX_NUMBER_OF_ROW= 81;
    private static final int MAX_NUMBER_OF_COL= 81;
    private static final int MAX_NUMBER_OF_ELEMENTS = 7;
    private static final int FIRST_PATT_OBJ_INDEX = 86;

    /**
     * Class constructor: instantiates a new PlayingGrid.
     */
    public PlayingGrid() {
        this.plcards = new GameCard[MAX_NUMBER_OF_ROW][MAX_NUMBER_OF_COL];
        this.numElements = new int[]{0, 0, 0, 0, 0, 0, 0};
        this.lastPlacedCard = null;

    }

    /**
     * Place the first card in an empty position.
     * @param i: an int that represents the row
     * @param j: an int that represents the column
     * @param startcard is the initial card to place
     *
     * @throws InvalidPlacementException when there's been already a start card.
     **/

    public void placeStartCard(int i, int j, GameCard startcard) throws InvalidPlacementException{
        if(plcards[i][j] == null){
            plcards[i][j] = startcard;
            lastPlacedCard = startcard;
            lastPlacedCard.setCoordinates(i, j);
            int[] temp2 = startcard.getResources();
            for (int a = 0; a < MAX_NUMBER_OF_ELEMENTS; a++)
                numElements[a] += temp2[a];

        }
        else
            throw new InvalidPlacementException("There's already a start card!");
    }


    /**
     * Place a card on the grid.
     * @param i an int that represents the row
     * @param j an int that represents the column
     * @param card is the card to be placed
     *
     * @return the number of points to assign after placing the card
     * @throws InvalidPlacementException when it is not possible to place the card
     * in that position, with different messages that describe what went wrong
     *
     */

    public int place(int i, int j, GameCard card) throws InvalidPlacementException{
        boolean foundNearCard = false;
        int counterNotNear = 0;



        if (plcards[i][j] != null) {
            throw new InvalidPlacementException("There's already a card in this position!");
        } //position already occupied

        //controlla ij placeable e prima di aggiungere plccard[i][j]=null
        List<Coordinate> temp = new ArrayList<>();

        for(int k = 0; (k < 4) && !foundNearCard; k++) {
            try {
                temp = getPlaceablePosition(i-1 + (k / 2)*2, j-1 + (k % 2)*2);
                foundNearCard = true;
            } catch (InvalidSearchPositionException e) {
                counterNotNear++;
                if (counterNotNear == 4)
                    throw new InvalidPlacementException("There's no card near this one!");
            }
        }

        Coordinate coppiaDaCercare = new Coordinate(i, j);

        //check if list is not null and contains coordinates
        boolean check;
        if (temp.isEmpty()) {
            throw new InvalidPlacementException("You can't place here!");
        }else{
            check = false;
            int size = temp.size();
            for(int a = 0; ((a < size) && (!check)); a++){
                if(temp.get(a).getX() == i && temp.get(a).getY() == j)
                    check = true;
            }

            if(!check) {
                throw new InvalidPlacementException("You can't place here!");
            }
        }



        if (CheckCardPlaceability(card)) { //card can be placed

            plcards[i][j] = card; //new pos of the card
            card.setCoordinates(i,j); //setting coordinates


            GameCard coveredCard = plcards[i-1][j-1];
            if (checkBoundaries(i-1, j-1) && coveredCard != null)
                if (coveredCard.isThereCorner(3) && coveredCard.getResourceCorner(3) != null)
                    subtractElement(coveredCard.getResourceCorner(3));

            coveredCard = plcards[i-1][j+1];
            if (checkBoundaries(i-1, j+1) && coveredCard != null)
                if (coveredCard.isThereCorner(2) && coveredCard.getResourceCorner(2) != null)
                    subtractElement(coveredCard.getResourceCorner(2));

            coveredCard = plcards[i+1][j-1];
            if (checkBoundaries(i+1, j-1) && coveredCard != null)
                if (coveredCard.isThereCorner(1) && coveredCard.getResourceCorner(1) != null)
                    subtractElement(coveredCard.getResourceCorner(1));

            coveredCard = plcards[i+1][j+1];
            if (checkBoundaries(i+1, j+1) && coveredCard != null)
                if (coveredCard.isThereCorner(0) && coveredCard.getResourceCorner(0) != null)
                    subtractElement(coveredCard.getResourceCorner(0));


            int[] temp2 = card.getResources();
            for (int a = 0; a < MAX_NUMBER_OF_ELEMENTS; a++) {
                numElements[a] += temp2[a];
            }

            setLastPlacedCard(card);

            return ComputePoints();
        }

        throw new InvalidPlacementException("You can't place this card on this side, requirements not achieved");
    }


    /**
     * Subtract an element from the counter of the elements, when a new card covers the corner
     * @param e the Element that has been covered
     */
    private void subtractElement(Element e){
        switch (e){
            case ANIMAL:
                numElements[0]--;
                break;
            case FUNGUS:
                numElements[1]--;
                break;
            case INKWELL:
                numElements[2]--;
                break;
            case INSECT:
                numElements[3]--;
                break;
            case MANUSCRIPT:
                numElements[4]--;
                break;
            case PLANT:
                numElements[5]--;
                break;
            case QUILL:
                numElements[6]--;
                break;
        }

    }


    /**
     * Get the positions where it is possible to place, around the card in the
     * given position.
     * @param i an int that represents the row
     * @param j an int that represents the column
     * @return a list of Coordinate: the placeable positions around the given card
     */
    public List<Coordinate> getPlaceablePosition(int i, int j) throws InvalidSearchPositionException{
        List <Coordinate> placeablePositions = new ArrayList<Coordinate>();

        if (checkBoundaries(i, j)) {
            GameCard positionChosen = plcards[i][j];
            if (positionChosen != null) {
                if (positionChosen.isThereCorner(0))
                    checkPositionCorner(i - 1, j - 1, placeablePositions);

                if (positionChosen.isThereCorner(1))
                    checkPositionCorner(i - 1, j + 1, placeablePositions);

                if (positionChosen.isThereCorner(2))
                    checkPositionCorner(i + 1, j - 1, placeablePositions);

                if (positionChosen.isThereCorner(3))
                    checkPositionCorner(i + 1, j + 1, placeablePositions);
            } else
                throw new InvalidSearchPositionException("There's no card in the position chosen!");
        }else
            throw new InvalidSearchPositionException("You're searching out of your playing area");

        return placeablePositions;
    }


    /**
     * Check if a position can be added to placeable Positions: if so, adds it to placeablePos
     * @param x the row of the position to check
     * @param y the column of the position to check
     * @param placeablePos a List of Coordinates that contains the updated list of placeable Positions
     */
    private void checkPositionCorner(int x, int y, List<Coordinate> placeablePos){
        boolean topLeft = true;
        boolean topRight = true;
        boolean bottomLeft = true;
        boolean bottomRight = true;

        if (checkBoundaries(x, y)){
            GameCard positionAvailable = plcards[x][y];
            if(positionAvailable == null) {
                topLeft = checkAroundPosition(0,x-1, y-1);
                topRight = checkAroundPosition(1, x-1, y+1);
                bottomLeft = checkAroundPosition(2, x+1, y-1);
                bottomRight = checkAroundPosition(3, x+1, y+1);

                if (topLeft && topRight && bottomLeft && bottomRight) {
                    Coordinate newPosition = new Coordinate(x, y);
                    placeablePos.add(newPosition);
                }
            }
        }
    }


    /**
     * Checks that there is not a hidden corner that would be covered by placing a card
     * in the candidate position.
     * @param cornerIndex an index that specifies with corner is under examination
     * @param x the row of the position with a possible hidden corner
     * @param y the row of the position with a possible hidden corner
     * @return true if there is no hidden corner that blocks the placement; false otherwise
     */
    private boolean checkAroundPosition(int cornerIndex, int x, int y){
        if (checkBoundaries(x, y)) {
            GameCard positionChecking = plcards[x][y];
            if (positionChecking != null)
                if (!positionChecking.isThereCorner(3 - cornerIndex))
                    return false;
        }
        return true;
    }

    /**
     * Check if a position is within the matrix
     * @param x the row of the position
     * @param y the column of the position
     * @return true if the position is within the matrix, otherwise false.
     */
    private boolean checkBoundaries(int x, int y){
        return (x > -1 && x < MAX_NUMBER_OF_ROW) && (y > -1 && y < MAX_NUMBER_OF_COL);
    }

    /**
     * Gets last placed card.
     * @return the last placed card
     */
    public GameCard getLastPlacedCard() {

        return lastPlacedCard;
    }

    /**
     * Get the matrix with the played cards.
     * @return the matrix with the played cards.
     */
    public GameCard[][] getPlcards() {
        return plcards;
    }


    /**
     * Gets the number of visible elements on the grid.
     * @param e is the type of element
     * @return the number of visible elements on the grid
     * (-1 if the given element is unknown)
     */
    public  int getNumElements(Element e) {

        int i = -1;

        if (e.equals(Element.ANIMAL)) {
            i = 0;
        } else if (e.equals(Element.FUNGUS)) {
            i = 1;
        } else if (e.equals(Element.INKWELL)) {
            i = 2;
        } else if (e.equals(Element.INSECT)) {
            i = 3;
        } else if (e.equals(Element.MANUSCRIPT)) {
            i = 4;
        } else if (e.equals(Element.PLANT)) {
            i = 5;
        } else if (e.equals(Element.QUILL)) {
            i = 6;
        }

        if (i == -1) {
            return -1; //error
        }

        return numElements[i];
    }


    /**
     * Check if a card fulfills the requirements for placing
     * @param card the card to be placed
     * @return true if the card fulfills the requirements, false if there are
     * not enough elements on the grid.
     */
    public boolean CheckCardPlaceability(GameCard card) {

            int[] temp1 = card.getRequirements();

            for (int i = 0; i < MAX_NUMBER_OF_ELEMENTS; i++) {
                if (numElements[i] < temp1[i])
                    return false;
            }
            // means that all of numElements are enough for requirements
            return true;
    }


    /**
     * Compute the points to assign to the player after a card is placed
     * @return the number of points gained
     */
    public int ComputePoints(){
        int points = 0;

        points = lastPlacedCard.getPoints();

        try {
            Condition condition = lastPlacedCard.getCondition();


            if (condition == Condition.CORNER) {
                int i = lastPlacedCard.getCoordinates().getX();
                int j = lastPlacedCard.getCoordinates().getY();

                int cont = 0;
                if (checkBoundaries(i-1, j+1) && (plcards[i - 1][j + 1] != null)) cont++;
                if (checkBoundaries(i-1, j-1) && (plcards[i - 1][j - 1] != null)) cont++;
                if (checkBoundaries(i+1, j+1) && (plcards[i + 1][j + 1] != null)) cont++;
                if (checkBoundaries(i+1, j+1) && (plcards[i + 1][j - 1] != null)) cont++;

                points = points * cont;
            }
            else {
                int occ = getNumElements(condition.getName());
                if (occ > 0) points = points * occ;
            }
            return points;
        }
        catch(NullPointerException e) {return points;}
    }

    /**
     * Set the last placed card
     * @param card the last placed card
     */
    public void setLastPlacedCard(GameCard card){
        this.lastPlacedCard = card;
    }


    /**
     * Checks if a cell in the playing grid contains a GameCard or not
     * @param row the row index in the playing grid
     * @param column the column index in the playing grid
     * @return true if the cell is empty; false if there is a card
     */
    public boolean cellIsEmpty(int row, int column){
        if(plcards[row][column] == null){
            return true;
        }
        else return false;
    }

    /**
     * Checks if the colour of a cell of the playing grid and a cell in the required submatrix to find match
     * @param row the row index in the playing grid
     * @param column the column index in the playing grid
     * @param patternObjCard a given pattern objective card to find in the playing grid
     * @param coordReqPatternIndex index of the cell in the submatrix that we're checking
     * @return true if the colours of the given cell and the cell in the required pattern match; otherwise false.
     */
    public boolean checkColourMatch(int row, int column, PatternObjectiveCard patternObjCard, int coordReqPatternIndex){
        try{
            if(plcards[row][column].getColour().equals(patternObjCard.getCoordReqPattern(coordReqPatternIndex).getColour())){
                return true;
            }
            else{
                return false;
            }
        } catch(NullPointerException e){
                return false;
        }
    }

    /**
     * Checks if a gamecard in the playing grid has already been used for a given pattern objective
     * @param row row index of the gamecard to check
     * @param column column index of the gamecard to check
     * @param patternObjCard the pattern objective card to check
     * @return true if the card was already used for the given objective; otherwise false.
     */
    public boolean cardWasAlreadyUsedForThisObjective(int row, int column, PatternObjectiveCard patternObjCard) {
        int index = patternObjCard.getObjIndex() - FIRST_PATT_OBJ_INDEX;
        if (plcards[row][column].getCompletedObjFlag(index)) {
            return true;
        } else return false;
    }

    /**
     * Marks a gamecard in the playing grid as used for a given pattern objective
     * @param row the row index of the gamecard to check
     * @param column the column index of the gamecard to check
     * @param patternObjCard the pattern objective card completed
     */
    public void markCardAsUsedForThisObjective(int row, int column, PatternObjectiveCard patternObjCard){
        int index = patternObjCard.getObjIndex() - FIRST_PATT_OBJ_INDEX;
        plcards[row][column].setCompletedObjFlag(index);
    }


    /**
     * Getter method for the array that contains the number of visible elements of each type.
     @return Array of integers, with each value representing the number of each type of element:
      *          position = 0 : n° of ANIMAL
      *          position = 1 : n° of FUNGUS
      *          position = 2 : n° of INKWELL
      *          position = 3 : n° of INSECT
      *          position = 4 : n° of MANUSCRIPT
      *          position = 5 : n° of PLANT
      *          position = 6 : n° of QUILL
     */
    public int[] getNumElements(){
        return numElements;
    }

    /**
     * Getter method for the array that contains the number of visible elements of each type.
     * @param numElements an array of integers, with each value representing the number of each type of element.
     *                    (array with the same order of the one returned by getNumElements)
     * @throws IllegalRequirementsException if the length of numElements is not 7
     */
    public void setNumElements(int[] numElements) throws IllegalRequirementsException {
        if(numElements.length==MAX_NUMBER_OF_ELEMENTS) {
            this.numElements = numElements;
        }
        else throw new IllegalRequirementsException("numElements array should be "+MAX_NUMBER_OF_ELEMENTS+" elements long");
    }
}










