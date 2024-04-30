package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.Utils.Coordinate;
import java.util.List;

public class PlaceablePositionsReturnedUpdate implements Update {

    List<Coordinate> availablePositions;
    String nickname;
    String turn;

    public PlaceablePositionsReturnedUpdate(String nickname, List<Coordinate> availablePositions, String turn){
        this.nickname=nickname;
        this.availablePositions = availablePositions;
        this.turn=turn;
    }

    public String getTurn() {
        return turn;
    }

    public List<Coordinate> getAvailablePositions() {
        return availablePositions;
    }

    public String getNickname() {
        return nickname;
    }

    public String toString(String receiver) {
        return receiver;
    }
}
