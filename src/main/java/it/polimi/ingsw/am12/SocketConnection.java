package it.polimi.ingsw.am12;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Socket connection between client and server
 */
public class SocketConnection implements Runnable{
    private Socket socketClient;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String idNickname;
    private Server server;

    /**
     * Class constructor
     * @param sc the client Socket
     * @param sv the server
     */
    public SocketConnection(Socket sc, Server sv) {
        this.socketClient = sc;
        this.server = sv;
    }

    /**
     * Remove the player and close the Socket connection
     * @throws NoMatchException if the player is not part of a match
     * @throws NotBoundException if an attempt is made to look for a name that is not currently
     *                           bound in the RMI registry
     * @throws RemoteException if remote communication with the RMI registry failed
     */
    public void close() throws NoMatchException, NotBoundException, RemoteException {
        server.closeMatchForPlayer(idNickname);
        closeConnection();
    }

    /**
     * Close the Socket connection
     */
    private void closeConnection(){
        try {
            in.close();
            out.close();
            socketClient.close();
        } catch (IOException e) {
            System.out.println("Error in closing connection");
        }
    }

    //TODO: javadoc
    @Override
    public void run() {
        try{
            out = new ObjectOutputStream(socketClient.getOutputStream()); //ciò che arriva al server
            in = new ObjectInputStream(socketClient.getInputStream()); //ciò che arriva al client



            //ping e pong
            //richiesta di join o create match
            //ping e pong
            //ingresso eventi - uscite update



            close();
        }catch(IOException e){
            ;
        } catch (NoMatchException e) {
            //
        } catch (NotBoundException e) {
            //;
        }
    }
}
