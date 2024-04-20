package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.View.VirtualView;

import java.io.Serializable;
import java.util.List;

/**
 * Interface that defines and event:
 * requires method executeCommand
 */
public interface Event extends Serializable {

    /**
     * Execute the command of a listened event
     * Each implementation of this interface Overrides this method
     * @param model the GameModel that interacts with this event
     * @param views the list of VirtualViews that interact with this event
     */
    void executeCommand(GameModel model, List<VirtualView> views);
}
