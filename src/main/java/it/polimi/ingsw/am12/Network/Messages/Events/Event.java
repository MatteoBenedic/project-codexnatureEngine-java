package it.polimi.ingsw.am12.Network.Messages.Events;

import it.polimi.ingsw.am12.ClientStub;
import it.polimi.ingsw.am12.Exceptions.*;
import it.polimi.ingsw.am12.Exceptions.IllegalStateException;
import it.polimi.ingsw.am12.Message;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.ServerStub;
import it.polimi.ingsw.am12.VVStub;
import java.rmi.RemoteException;

/**
 * Abstract class that defines an event that allows the client to execute a model function
 * requires method executeCommand
 */
public abstract class Event implements Message {

    /**
     * Invoke the corresponding function in remote server via RMI
     * @param client the client
     * @param server the remote Server
     * @param vv the remote VirtualView
     * @param nickname the nickname of the player;
     */
    public void sendRMI(ClientStub client, ServerStub server, VVStub vv, String nickname) throws RemoteException, DuplicateNicknameException, WrongInformationException, InvalidSearchPositionException, NotYourTurnException, WrongNumberOfPlayersException, EmptyDeckException, InvalidPlacementException, IllegalStateException, it.polimi.ingsw.am12.Exceptions.InvalidParameterException {
       vv.performEvent(this);
    }

    /**
     * Execute the command of a listened event
     * Each subclass of Event overrides this method
     * @param model the GameModel that interacts with this event
     */
    public void executeCommand(GameModel model) throws WrongNumberOfPlayersException, DuplicateNicknameException,
            IllegalStateException, InvalidPlacementException, WrongInformationException, NotYourTurnException,
            EmptyDeckException, InvalidSearchPositionException, it.polimi.ingsw.am12.Exceptions.InvalidParameterException {

    }
}
