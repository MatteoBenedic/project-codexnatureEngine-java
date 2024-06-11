package it.polimi.ingsw.am12.Network.Messages.Events;

import it.polimi.ingsw.am12.Exceptions.DuplicateNicknameException;
import it.polimi.ingsw.am12.Exceptions.InvalidParameterException;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.am12.VirtualView.VirtualView;
import it.polimi.ingsw.am12.Exceptions.IllegalStateException;

/**
 * Event to add player to the match
 */
public class JoinMatchEvent extends Event{

    private String nickname;
    private VirtualView view;

    /**
     * Class constructor
     * @param nickname the nickname of the player
     * @param view the VirtualView associated to that player
     */
    public JoinMatchEvent(String nickname, VirtualView view) {
        this.nickname = nickname;
        this.view = view;
    }

    /**
     * Add the users in the lobby as players of the match
     * @param model the GameModel that interacts with this event
     * @throws IllegalStateException if the method has been invoked at an illegal or inappropriate time.
     * @throws InvalidParameterException if the nickname is null
     * @throws DuplicateNicknameException if the nickname is already in use
     * @throws WrongNumberOfPlayersException if there is already the maximum number of players in the lobby.
     */
    @Override
    public void executeCommand(GameModel model) throws WrongNumberOfPlayersException, DuplicateNicknameException,
            IllegalStateException, InvalidParameterException {
            model.addPlayerToLobby(nickname, view);
    }
}
