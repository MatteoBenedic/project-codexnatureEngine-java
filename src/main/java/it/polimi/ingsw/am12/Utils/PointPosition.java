package it.polimi.ingsw.am12.Utils;

import java.util.HashMap;

/**
 * This utility class is used to map the scores with the corresponding position
 * of the pion on the score board
 */
public class PointPosition {

    private HashMap<Integer, Coordinate> position;

    /**
     * Class constructor: initialize the map
     */
    public PointPosition(){
        position = new HashMap<>();

        position.put(24,new Coordinate(8,4));

        position.put(0,new Coordinate(8,88));
        position.put(1,new Coordinate(20,88));

        position.put(25,new Coordinate(20,2));
        position.put(29,new Coordinate(20,14));
        position.put(20,new Coordinate(20,27));

        position.put(2,new Coordinate(32,88));

        position.put(26,new Coordinate(31,4));

        position.put(6,new Coordinate(3,78));
        position.put(5,new Coordinate(15,78));
        position.put(4,new Coordinate(27,78));
        position.put(3,new Coordinate(38,78));

        position.put(7,new Coordinate(3,67));
        position.put(8,new Coordinate(15,67));
        position.put(9,new Coordinate(27,67));
        position.put(10,new Coordinate(38,67));

        position.put(14,new Coordinate(3,56));
        position.put(13,new Coordinate(15,56));
        position.put(12,new Coordinate(27,56));
        position.put(11,new Coordinate(38,56));

        position.put(15,new Coordinate(3,45));
        position.put(16,new Coordinate(15,45));
        position.put(17,new Coordinate(27,45));
        position.put(18,new Coordinate(38,45));

        position.put(21,new Coordinate(3,34));
        position.put(22,new Coordinate(3,23));
        position.put(23,new Coordinate(3,12));

        position.put(19,new Coordinate(38,34));
        position.put(28,new Coordinate(38,23));
        position.put(27,new Coordinate(38,12));

    }

    /**
     * Get the position of the pion on the score board on score board for a certain score
     * @param point the score
     * @return
     */
    public Coordinate getPosition(int point) {
        return position.get(point);
    }
}
