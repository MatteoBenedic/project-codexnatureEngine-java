package it.polimi.ingsw.am12.Client.ClientController;

import it.polimi.ingsw.am12.Exceptions.IllegalStateException;
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
        try {
            message.sendRMI(this, server, vv, viewModel.getNickname());
        } catch (NoNicknameException | DuplicateMatchException | WrongNumberOfPlayersException |
                 WrongInformationException | DuplicateNicknameException | AlreadyBoundException | EmptyDeckException |
                 NotBoundException | InvalidSearchPositionException | NotYourTurnException | RemoteException |
                 NoMatchException | InvalidPlacementException | IllegalStateException | InvalidParameterException e) {
            catchException(e);
        }
    }
}
