package it.polimi.ingsw.am12.Controller;

import it.polimi.ingsw.am12.Network.Messages.Events.Event;
import it.polimi.ingsw.am12.Exceptions.*;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.VirtualView.VirtualView;
import java.security.InvalidParameterException;

/**
 * This class is the controller of the game: its function is to coordinate
 * the model and the views.
 * It's subscribed as a listener a list of VirtualViews (one for each player).
 */
public class Controller implements EventListener {

    private GameModel model;

    /**
     * Class constructor
     * @param numPlayers the number of players of the game associated to this instance of Controller
     *                   (requires 2 <= numPlayers <= 4)
     * @throws WrongNumberOfPlayersException if numPlayers < 2 or numPlayers > 4.
     */
    public Controller(int numPlayers) throws WrongNumberOfPlayersException {
        model = new GameModel(numPlayers);
    }

    /**
     * Subscribe this instance of Controller as a listener of a VirtualView
     * @param view the VirtualView to subscribe to.
     */
    public void addView(VirtualView view) {
        view.addListener(this);
    }

    /**
     * Listen to an Event and execute the corresponding command
     * @param e the listened Event
     */
    @Override
    public synchronized void actionPerformed(Event e) throws WrongNumberOfPlayersException, DuplicateNicknameException,
            IllegalStateException, InvalidPlacementException, WrongInformationException, NotYourTurnException,
            InvalidParameterException, EmptyDeckException, InvalidSearchPositionException {
        e.executeCommand(model);
    }

    /**
     * Get the model associated to this Controller
     * @return an instance of GameModel
     */
    public GameModel getModel(){
        return model;
    }

    /**
     * Invalidate the model associated to this Controller
     */
    public void closeModel(){
        model = null;
    }
}