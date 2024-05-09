package it.polimi.ingsw.am12.Model.CardDesign.GameCard;

import it.polimi.ingsw.am12.Utils.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents all the cards that are playable on the playing grid of the player
 * and contains the methods
 * which other parts of the model can use to extract info from the card
 */
public class GameCard{
    private int index;
    private CardColour colour;
    private Side front = null;
    private Side back = null;
    private Side validSide = null;
    private Coordinate coordinates = new Coordinate(-1, -1);
    private boolean[] flagObj = {false, false, false, false, false, false, false, false};


    //Getters

    /**
     * @return the index of the card
     */
    public int getIndex(){
        return index;
    }

    /**
     * @return the playing Side of this card
     */
    public Side getValidSide() {
        return validSide;
    }

    /**
     * It returns a value to define which side of the card is played
     * @return a boolean value: true if the valid side is the front one, false if it's the other one
     * @throws NullPointerException if the valid side is not initialized
     */
    public boolean getWhichSide() throws NullPointerException{
        if(validSide == null)
            throw new NullPointerException();
        else
            return validSide == front;
    }

    /**
     * @return the coordinates of the card
     */
    public Coordinate getCoordinates() throws NullPointerException {
        return coordinates;
    }

    /**
     * @return the card suit
     */
    public CardColour getColour() {
        return colour;
    }

    /**
     * @return the points that the card gives when placed on the current playing side
     */
    public int getPoints(){
        return validSide.getPoints();
    }

    /**
     * Checks if the chosen corner exists (if it is visible or not)
     * @param i the position of the searched corner:
     *          0 - represents the top left corner
     *          1 - represents the top right corner
     *          2 - represents the bottom left corner
     *          3 - represents the bottom right corner
     * @return true if there's an object corner on that position of the array, false if there's not
     */
    public Boolean isThereCorner(int i) throws IndexOutOfBoundsException{
        return validSide.getCorner(i) != null;
    }

    /**
     * Gets the element of the corner chosen of the validSide
     * @param i the position of the searched corner. It has to be a valid corner (not a hidden corner).
     * @return the Element of the searched corner if there's one, null otherwise
     */
    public Element getResourceCorner(int i){
        return (validSide.getCorner(i).getElement());
    }

    /**
     * Gets the elements needed on the playingGrid to place the card on the validSide, if those exist
     * @return an array of integers where every number represents how many element of a certain type is needed
     * (array with the same order of the one returned by getResources)
     */
    public int[] getRequirements(){
        int[] temp = new int[7];

        for(int i = 0; i < 7; i++){
            temp[i] = 0;
        }

        List<Element> req = new ArrayList<Element>();
        req = validSide.getRequirements();

        setListElements(req, temp);


        return temp;
    }

    /**
     * Extracts number of all type of resources of the valid side of this card,
     * from the visible corners and from the center if it exists
     * @return Array of integers, with each value representing the number of each type of element:
     *          position = 0 : n° of ANIMAL
     *          position = 1 : n° of FUNGUS
     *          position = 2 : n° of INKWELL
     *          position = 3 : n° of INSECT
     *          position = 4 : n° of MANUSCRIPT
     *          position = 5 : n° of PLANT
     *          position = 6 : n° of QUILL
     */
    public int[] getResources(){
        //ANIMAL = 0, FUNGUS = 1, INKWELL = 2, INSECT = 3, MANUSCRIPT = 4, PLANT = 5, QUILL = 6
        int[] temp = new int[7];
        int k;

        for (int i = 0; i < 7; i++)
            temp[i] = 0;

        List<Element> c = new ArrayList<Element>();

        for (int i = 0; i < 4; i++)
            if(isThereCorner(i) && getResourceCorner(i) != null)
                c.add(getResourceCorner(i));


        setListElements(c, temp);

        c = validSide.getCenter();
        setListElements(c, temp);


        return temp;
    }


    /**
     * Gets the type of multiplying condition of the points of the validSide of the card, if it exists one
     * @return the condition of the validSide if there's one, null otherwise
     */
    public Condition getCondition(){
        return validSide.getCondition();
    }

    /**
     * This method returns the boolean flag for the given pattern objective index
     * @param patternObjIndex the index that identifies the pattern objective
     * @return boolean value
     */
    public boolean getCompletedObjFlag(int patternObjIndex) {
        return flagObj[patternObjIndex];
    }

                        //Setters

    /**
     * Method used by the previous one in the concrete classes to set the initial playing side as the front one
     */
    public void setDefaultSide() {
        validSide = front;
    }

    /**
     * Sets which of the sides of the card has to be the playing one
     * @param side a boolean variable that if:
     *             - side = true, sets the front of the card as the playing side of the card
     *             - side = false, sets the back of the card as it
     * @throws NullPointerException if the sides aren't created still
     */
    public void setValidSide(Boolean side) throws NullPointerException {
        if (side)
            validSide = front;
        else
            validSide = back;
    }


    /**
     * Private method used by getResources to set in the array the number of elements given the elements of the card
     * in a list
     * @param c list of elements
     * @param t array to set
     */
    private void setListElements(List<Element> c, int[] t){
        int k;
        if(c != null){
            k = c.size();
            for(int i = 0; i < k; i++) {
                switch (c.get(i)) {
                    case ANIMAL:
                        t[0]++;
                        break;
                    case FUNGUS:
                        t[1]++;
                        break;
                    case INKWELL:
                        t[2]++;
                        break;
                    case INSECT:
                        t[3]++;
                        break;
                    case MANUSCRIPT:
                        t[4]++;
                        break;
                    case PLANT:
                        t[5]++;
                        break;
                    case QUILL:
                        t[6]++;
                        break;
                }
            }
        }
    }


    /**
     * sets the coordinates of the card
     * @param x the x coordinate to set
     * @param y the y coordinate to set
     */
    public void setCoordinates(int x, int y){
        coordinates.setX(x);
        coordinates.setY(y);
    }


    /**
     * This method marks this GameCard object as already used for a given pattern objective
     * @param patternObjIndex the index that identifies the pattern objective
     */
    public void setCompletedObjFlag(int patternObjIndex) {
        this.flagObj[patternObjIndex] = true;
    }
}
