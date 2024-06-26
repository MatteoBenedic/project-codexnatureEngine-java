package it.polimi.ingsw.am12.Client.ClientController;

import it.polimi.ingsw.am12.Exceptions.IllegalStateException;
import it.polimi.ingsw.am12.Message;
import it.polimi.ingsw.am12.Exceptions.*;
import it.polimi.ingsw.am12.Network.Messages.Updates.NicknameEstablishedUpdate;
import it.polimi.ingsw.am12.Network.Messages.Updates.Update;
import it.polimi.ingsw.am12.ServerStub;
import it.polimi.ingsw.am12.VVStub;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import static java.lang.System.exit;

/**
 * RMI implementation of ClientController. It manages the communication with the server
 * and receives the updates to send to the ViewModel
 */
public class ClientControllerRMI extends ClientController {

    Registry registry;
    ServerStub server;
    VVStub vv;
    private String vvnick;

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
            System.err.println("Server not connected");
            exit(1);
        }

        String host = addr.getHostName();
        try {
            registry = LocateRegistry.getRegistry(host, port);
        } catch (RemoteException e) {
            System.err.println("Server not connected");
            exit(1);
        }

        try {
            server = (ServerStub) registry.lookup("CodexServer");
        } catch (NotBoundException | ConnectException | ConnectIOException e) {
           System.err.println("Server not connected");
           exit(1);
        }
    }

    /**
     * Sets the VirtualView remote object by a lookup in RMI registry
     * @param nickname the nickname of the user who owns the VirtualView.
     */
    public void setVirtualView(String nickname) {
        try {
            vvnick = nickname+"VirtualView";
            vv = (VVStub) registry.lookup(vvnick);
            //vv = (VVStub) registry.lookup(nickname+"VirtualView");
        } catch (RemoteException | NotBoundException e) {
            System.err.println("Server not connected");
            exit(1);
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
                 NotBoundException | InvalidSearchPositionException | NotYourTurnException |
                 NoMatchException | InvalidPlacementException | IllegalStateException | InvalidParameterException | RemoteException e) {
            catchException(e);
        }
    }

    /**
     * Sends a ping to the server (RMI invocation)
     */
    @Override
    protected void pingServer() {
        //System.out.println("Attempting to ping server...");
        try {
            vv.invokePingRMI();
        } catch (RemoteException e) {
            System.err.println("Error in sending ping to server");
        }
    }

    /**
     * Closes the connection with the server
     */
    @Override
    protected void closeConnection() {
        try {
            try {
                registry.unbind(vvnick);
            } catch (NotBoundException | RemoteException e) {
                UnicastRemoteObject.unexportObject(this, true);
            }
        } catch (RemoteException e) {
            System.err.println("Error in closing connection: " + e.getMessage());
        }
        Timer pingTimer = getPingTimer();
        Timer pongTimeoutTimer = getPongTimeoutTimer();
        pingTimer.cancel();
        if(pongTimeoutTimer != null)
            pongTimeoutTimer.cancel();
    }

    /**
     * RMI pong equivalent: this method represents an invocation that is done
     * to answer a ping invocation
     */
    public void invokePongRMI(){
        //System.out.println("Pong received");
        resetPongTimeoutTimer();
    }

}
