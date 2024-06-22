package it.polimi.ingsw.am12.Client.ClientController;
import it.polimi.ingsw.am12.Message;
import it.polimi.ingsw.am12.Network.ClientSideSocketHandler;
import java.rmi.RemoteException;

/**
 * Socket implementation of ClientController. It manages the communication with the socket handler
 * receives the updates to send to the ViewModel and starts the ping-pong mechanism on the client side,
 * for the detection of connection problems
 */
public class ClientControllerSocket extends ClientController {

    ClientSideSocketHandler socketHandler;

    /**
     * Class constructor
     * @param ip the ip address of the server
     * @param port the port of the server socket
     */
    public ClientControllerSocket(String ip, int port) throws RemoteException {
        super(ip, port);
        socketHandler = new ClientSideSocketHandler(ip, port, this);
        socketHandler.run();
    }

    /**
     * Send a message to the server
     * @param message the message to send
     */
    @Override
    public void sendMessage(Message message) {
        socketHandler.send(message);
    }

    /**
     * Send a "ping" to the server through Socket
     */
    @Override
    protected void pingServer() {
        socketHandler.pingMessageToServer();
    }

    /**
     * Closes the socket connection
     */
    @Override
    protected void closeConnection(){
        socketHandler.closeConnection();
    }
}
