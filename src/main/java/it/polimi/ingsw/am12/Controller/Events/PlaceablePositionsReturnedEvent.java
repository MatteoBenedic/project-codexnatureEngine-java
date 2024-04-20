package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Utils.Coordinate;
import it.polimi.ingsw.am12.View.VirtualView;
import java.util.List;

public class PlaceablePositionsReturnedEvent implements Event{

    List<Coordinate> availablePositions;
    String nickname;
    String turn;

    public PlaceablePositionsReturnedEvent(String nickname, List<Coordinate> availablePositions, String turn){
        this.nickname=nickname;
        this.availablePositions = availablePositions;
        this.turn=turn;
    }

    @Override
    public void executeCommand(GameModel model, List<VirtualView> views) {
        for(VirtualView view : views) {
            String message = "\n" + view.getNickname();
            if(view.getNickname().equals(nickname)) {
                message += "\nLa lista di coordinate dove puoi piazzare è: ";
                for(Coordinate coordinate : availablePositions){
                    message += "\nCoordinata x = " + coordinate.getX() + " Coordinata y = " + coordinate.getY();
                }
            }
            if(view.getNickname().equals(turn)) {
                message += "\nÈ il tuo turno";
            }
            else {
                message += "\nÈ il turno di " + turn;
            }
            view.setMessage(message);
        }
    }
}
