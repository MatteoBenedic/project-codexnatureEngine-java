package it.polimi.ingsw.am12.VirtualView;


import it.polimi.ingsw.am12.Controller.EventListener;
import it.polimi.ingsw.am12.Exceptions.*;
import it.polimi.ingsw.am12.Exceptions.IllegalStateException;
import it.polimi.ingsw.am12.Network.Messages.Events.Event;
import it.polimi.ingsw.am12.VVStub;
import it.polimi.ingsw.am12.Network.Messages.Updates.Update;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is the server side component of the player view.
 * When the player requests an action, it creates an event to notify the Controller of the game.
 */
public abstract class VirtualView extends UnicastRemoteObject implements UpdateListener, VVStub {
    private EventListener listener;
    private final String nickname;
    private Timer waitingTime = new Timer();
    private static final int PING_TIMEOUT = 10000;


    /**
     * Class constructor
     *
     * @param nickname       a String that identifies the player who owns this instance of VirtualView
     * @throws RemoteException   if remote communication with the RMI registry failed
     * @throws NotBoundException if an attempt is made to look for a name that is not currently
     *                           bound in the RMI registry
     */
    public VirtualView(String nickname) throws RemoteException, NotBoundException {
        this.nickname = nickname;
    }


    /**
     * Subscribe a listener to this VirtualView
     *
     * @param listener the EventListener to add as a listener of this VirtualView
     *                 Only one listener can be added: if there's already a listener, this method overwrites it
     */
    public void addListener(EventListener listener) {
        this.listener = listener;
    }

    /**
     * Remove the listener of this VirtualView
     */
    public void removeListener() {
        listener = null;
    }

    /**
     * Perform an Event, that will be listened by the subscribed listener
     * @param e the Event to perform
     */
    public void performEvent(Event e) throws WrongNumberOfPlayersException, DuplicateNicknameException,
            InvalidPlacementException, WrongInformationException, NotYourTurnException,
            InvalidParameterException, EmptyDeckException, InvalidSearchPositionException, IllegalStateException {
        listener.actionPerformed(e);
    }

    /**
     * Listen to an Update and send it to the client
     *
     * @param u the listened Update
     */
    public abstract void sendUpdate(Update u);


    /**
     * Get the nickname of the player who owns this instance of VirtualView
     * @return the nickname of the player who owns this instance of VirtualView
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Starts a task to check if at least a ping is received from client within the ping timeout
     */
    public void startPingTimer() {
        waitingTime = new Timer();
        waitingTime.schedule(new TimerTask() {
            @Override
            public void run() {
                //If a ping is not received within the PING_TIMEOUT, the client is inactive
                System.err.println("Ping timeout: the client " + nickname + " " +  " is inactive. Closing connection...");
                shutdown();
            }
        }, PING_TIMEOUT);
    }

    /**
     * Resets the ping timer when a ping is received from client.
     * Starts a new ping timer.
     */
    public void resetPingTimer() {
        if (waitingTime != null) {
            waitingTime.cancel();
        }
        System.out.println("reset ping timer...");
        startPingTimer();
    }

    /**
     * Closes the connection with a client when the ping timeout is triggered
     */
    protected void shutdown(){}

    /**
     * RMI ping equivalent: this method represents an invocation that is done to periodically
     * check if the connection with the server is still active
     */
    public void invokePingRMI(){}

    /**
     * RMI method used to answer the RMI client once a ping invocation is received.
     * It is the RMI equivalent to a pong answer
     */
    protected void answerPongRMI() {}

}
