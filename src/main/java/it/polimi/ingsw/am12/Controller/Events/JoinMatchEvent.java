package it.polimi.ingsw.am12.Controller.Events;

import it.polimi.ingsw.am12.Model.Logic.DuplicateNicknameException;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Model.Logic.WrongNumberOfPlayersException;
import it.polimi.ingsw.am12.View.VirtualView;
import java.util.List;

/**
 * Event to add player to the match
 */
public class JoinMatchEvent implements Event{

    private String nickname;
    private VirtualView view;

    /**
     * Instantiates a new Join match event.
     *
     * @param nickname the nickname  of player
     * @param view the view
     */
    public JoinMatchEvent(String nickname, VirtualView view) {
        this.nickname = nickname;
        this.view = view;
    }

    /**
     * Add the users in the lobby as players of the match
     * @throws WrongNumberOfPlayersException    if the number of nicknames in the lobby differs from the
     *                                          number of players of the match. In this case no player is added.
     */
    @Override
    public void executeCommand(GameModel model) throws WrongNumberOfPlayersException, DuplicateNicknameException,
            IllegalStateException{
            model.addPlayerToLobby(nickname, view);
    }
}
