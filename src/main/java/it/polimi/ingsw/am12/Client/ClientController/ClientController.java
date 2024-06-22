package it.polimi.ingsw.am12.Client.ClientController;

import it.polimi.ingsw.am12.ClientStub;
import it.polimi.ingsw.am12.Message;
import it.polimi.ingsw.am12.Network.Messages.Updates.Update;
import it.polimi.ingsw.am12.Client.UI.UserInterface;
import it.polimi.ingsw.am12.Client.ViewModel.ViewModel;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;
import static java.lang.System.exit;

/**
 * The controller of the client. It manages the communication with the server or the communication layer of the client
 * and receives the updates to send to the ViewModel
 */
public class ClientController extends UnicastRemoteObject implements ClientStub {
    ViewModel viewModel;
    private volatile boolean connected;
    private Timer pingTimer = new Timer();
    private Timer pongTimeoutTimer;
    private static final int PING_INTERVAL = 2500;
    private static final int PONG_TIMEOUT = 10000;

    /**
     * Class constructor
     * @param ip the ip address of the server
     * @param port the port of the client connection
     * @throws RemoteException if remote communication with the RMI registry failed
     */
    public ClientController(String ip, int port) throws RemoteException{
        this.viewModel = new ViewModel();
        connected = true;
    }

    /**
     * Add a UserInterface as a listener of the ViewModel
     * @param ui the UserInterface
     */
    public void addViewModelListener(UserInterface ui) {
        viewModel.addListener(ui);
    }

    /**
     * Send a message to the server.
     * This method will be overridden by ClientControllerRMI and ClientControllerSocket
     * @param message the message to send
     */
    public void sendMessage(Message message) {
    }

    /**
     * Receive an update from the server and modify the ViewModel accordingly
     * @param update the received update
     */
    public void catchMessage(Update update){
        update.executeUpdate(viewModel);
    }

    /**
     * Handle an exception from the server and update the error message in the ViewModel
     * @param e the Exception to handle
     */
    public void catchException(Exception e) {
        viewModel.setErrorMessage(e.getMessage());
    };

    /**
     * Sends a ping to the server
     */
    protected void pingServer() {
    }

    /**
     * Starts the ping-pong mechanism: schedules a task to send ping at fixed intervals,
     * then calls a method to schedule another task that checks if a pong is received withing the pong timeout
     */
    public void startPingPong() {
        pingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (connected) {
                    pingServer();
                }
            }
        }, 0, PING_INTERVAL);

        schedulePongTimeout();
    }

    /**
     * Schedule a task that initializes a new timer to check if pong is received within the timeout
     */
    private void schedulePongTimeout() {
        if (pongTimeoutTimer != null)
            pongTimeoutTimer.cancel();

        pongTimeoutTimer = new Timer();
        pongTimeoutTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                connectionLostHandler();
                exit(1);
            }
        }, PONG_TIMEOUT);
    }

    /**
     * Reset the pong timeout timer
     */
    public void resetPongTimeoutTimer() {
        if (pongTimeoutTimer != null)
            pongTimeoutTimer.cancel();
        schedulePongTimeout();
    }

    /**
     * Handles the connection loss: signals the connection loss and closes the connection
     */
    public void connectionLostHandler() {
        if(connected) {
            System.out.println("connection to server lost...");
            closeConnection();
        }
    }

    /**
     * Closes the connection with the server
     */
    protected void closeConnection() {}

    /**
     * Getter method for the ping timer
     * @return a Timer object, which is the ping timer
     */
    public Timer getPingTimer() {
        return pingTimer;
    }

    /**
     * Getter method for the pong timer
     * @return a Timer object, which is the pong timeout timer
     */
    public Timer getPongTimeoutTimer() {
        return pongTimeoutTimer;
    }

    /**
     * RMI pong equivalent: this method represents an invocation that is done
     * to answer a ping invocation
     */
    @Override
    public void invokePongRMI() {}
  
    /**
     * If the connection is RMI, sets the VirtualView remote object by a lookup in RMI registry.
     * The method is implemented in the ClientControllerRMI.
     * @param nickname the nickname of the user who owns the VirtualView.
     */
    public void setVirtualView(String nickname) {
    }
}
