package it.polimi.ingsw.am12.Client.ClientController;

import it.polimi.ingsw.am12.Message;
import it.polimi.ingsw.am12.Network.Messages.Events.Event;
import it.polimi.ingsw.am12.Exceptions.*;
import it.polimi.ingsw.am12.Network.Messages.*;
import it.polimi.ingsw.am12.Network.Messages.Updates.NicknameEstablishedUpdate;
import it.polimi.ingsw.am12.Network.Messages.Updates.Update;
import it.polimi.ingsw.am12.ServerStub;
import it.polimi.ingsw.am12.VVStub;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RMI implementation of ClientController. It manages the communication with the server
 * and receives the updates to send to the ViewModel
 */
public class ClientControllerRMI extends ClientController {

    Registry registry;
    ServerStub server;
    VVStub vv;

    /**
     * Class constructor
     * @param ip the ip address of the server
     * @param port the port of the RMI registry
     * @throws RemoteException if remote communication with the RMI registry failed
     */
    public ClientControllerRMI(String ip, int port) throws RemoteException {
        super(ip, port);
        InetAddress addr = null;
        try {
            addr = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        String host = addr.getHostName();
        try {
            registry = LocateRegistry.getRegistry(host, port);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        try {
            server = (ServerStub) registry.lookup("CodexServer");
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Receive an update from the server and modify the ViewModel accordingly
     * @param update the received update
     */
    @Override
    public void catchMessage(Update update){
        update.executeUpdate(viewModel);
        if(update instanceof NicknameEstablishedUpdate) {
            setVirtualView(((NicknameEstablishedUpdate) update).getNickname());
        }
    }

    /**
     * Sets the VirtualView remote object by a lookup in RMI registry
     * @param nickname the nickname of the user who owns the VirtualView.
     */
    public void setVirtualView(String nickname) {
        try {
            vv = (VVStub) registry.lookup(nickname+"VirtualView");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send a message to the server
     * @param message the message to send
     */
    @Override
    public void sendMessage(Message message) {
        if(message instanceof NicknameMessage) {
            try {
                server.setNickname(((NicknameMessage) message).getNickname(), this, null);
            } catch (RemoteException | DuplicateNicknameException | AlreadyBoundException | NotBoundException e) {
                catchException(e);
            }
        }
        if(message instanceof CreateMatchMessage) {
            try {
                server.createMatch(((CreateMatchMessage) message).getMatchName(), ((CreateMatchMessage) message).getNumPlayers(), viewModel.getNickname());
            } catch (RemoteException | DuplicateNicknameException | AlreadyBoundException | NotBoundException |
                     WrongInformationException | InvalidSearchPositionException | NotYourTurnException |
                     NoNicknameException | DuplicateMatchException | WrongNumberOfPlayersException |
                     EmptyDeckException | InvalidPlacementException e) {
                catchException(e);
            }
        }
        if(message instanceof JoinMatchMessage) {
            try {
                server.joinMatch(((JoinMatchMessage) message).getMatchName(), viewModel.getNickname());
            } catch (RemoteException | DuplicateNicknameException | AlreadyBoundException | NotBoundException |
                     WrongInformationException | InvalidSearchPositionException | NotYourTurnException |
                     NoNicknameException | WrongNumberOfPlayersException |
                     EmptyDeckException | InvalidPlacementException | NoMatchException e) {
                catchException(e);
            }
        }
        if(message instanceof LobbiesRequestMessage) {
            try {
                server.getIncompleteLobbies(viewModel.getNickname());
            } catch (NoNicknameException | RemoteException e) {
                catchException(e);
            }
        }
        if(message instanceof CloseMatchConnectionMessage) {
            try {
                server.closeMatchForPlayer(viewModel.getNickname());
            } catch (NoMatchException | RemoteException e) {
                catchException(e);
            }
        }
        if(message instanceof Event) {
            try {
                vv.performEvent((Event) message);
            } catch (WrongNumberOfPlayersException | DuplicateNicknameException | InvalidPlacementException |
                     WrongInformationException | NotYourTurnException | EmptyDeckException |
                     InvalidSearchPositionException | RemoteException e) {
                catchException(e);
            }
        }
    }
}
