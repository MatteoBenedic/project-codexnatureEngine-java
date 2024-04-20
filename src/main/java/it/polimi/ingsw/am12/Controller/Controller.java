package it.polimi.ingsw.am12.Controller;

import it.polimi.ingsw.am12.Controller.Events.Event;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Model.Logic.WrongNumberOfPlayersException;
import it.polimi.ingsw.am12.View.VirtualView;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the controller of the game:  its function is to coordinate
 * the model and the views.
 * It's subscribed as a listener of a GameModel and a list of VirtualViews (one for each player).
 */
public class Controller implements EventListener {

    private GameModel model;
    private List<VirtualView> views;

    /**
     * Class constructor
     * @param numPlayers the number of players of the game associated to this instance of Controller
     *                   (requires 2 <= numPlayers <= 4)
     * @throws WrongNumberOfPlayersException if numPlayers < 2 or numPlayers > 4.
     */
    public Controller(int numPlayers) throws WrongNumberOfPlayersException {
        this.views = new ArrayList<>();
        model = new GameModel(numPlayers);
        model.addListener(this);
    }

    /**
     * Subscribe this instance of Controller as a listener of a VirtualView
     * @param view the VirtualView to subscribe to.
     */
    public void addView(VirtualView view) {
        view.addListener(this);
        views.add(view);
    }

    /**
     * Listen to an Event and execute the corresponding command
     * @param e the listened Event
     */
    @Override
    public void actionPerformed(Event e) {
        e.executeCommand(model, views);
    }
}