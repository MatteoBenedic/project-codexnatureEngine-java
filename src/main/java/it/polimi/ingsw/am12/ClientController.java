package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.View.Updates.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The controller of the client. It manages the communication with the server or the communication layer of the client
 * and receives the updates to send to the ViewModel
 */
public class ClientController extends UnicastRemoteObject implements ClientStub {
    ViewModel viewModel;

    /**
     * Class constructor
     * @param ip the ip address of the server
     * @param port the port of the client connection
     * @throws RemoteException if remote communication with the RMI registry failed
     */
    public ClientController(String ip, int port) throws RemoteException{
        this.viewModel = new ViewModel();
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
    };

    /**
     * Handle an exception from the server and update the error message in the ViewModel
     * @param e the Exception to handle
     */
    public void catchException(Exception e) {
        viewModel.setErrorMessage(e.getMessage());
    };
}
