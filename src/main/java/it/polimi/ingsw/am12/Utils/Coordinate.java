package it.polimi.ingsw.am12.Utils;

/**
 * This class represents the position in the matrix, it's used to compress the 2 coordinates
 */
public class Coordinate {

    private int x;
    private int y;

    /**
     * Constructor
     * @param x number of the row in the matrix
     * @param y nomber of the column in the matrix
     */
    public Coordinate(int x, int y) {
        this.y = y;
        this.x = x;
    }

    /**
     * @return the number of the row of this position
     */
    public int getX(){
        return x;
    }

    /**
     * @return the number of the column of this position
     */
    public int getY() {
        return y;
    }

    /**
     * sets the number of the row of the position
     * @param x the number of the row
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * sets the number of the column of the position
     * @param y the number of the column
     */
    public void setY(int y){
        this.y = y;
    }
}
