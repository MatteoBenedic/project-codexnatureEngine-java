package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.Controller.Controller;
import it.polimi.ingsw.am12.Controller.Events.JoinMatchEvent;
import it.polimi.ingsw.am12.Model.Logic.*;
import it.polimi.ingsw.am12.View.VirtualViewSocket;
import it.polimi.ingsw.am12.View.VirtualView;
import it.polimi.ingsw.am12.View.VirtualViewRMI;

import java.io.IOException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidParameterException;
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.Registry;

import static java.lang.System.out;

/**
 * The server that contains all the ongoing matches
 */
public class Server extends UnicastRemoteObject implements ServerStub {

    private final Registry registry;
    private final Map<String, Controller> matches;
    private final Map<String, String> nicknamesToMatch;
    private final Map<String, VirtualView> linkClientViews;
    private ServerSocket serverSocket;
    private int portServerSocket;

    /**
     * Class constructor
     * @param portServerSocket the port of the Socket Connection
     * @param registry the RMI registry
     * @throws RemoteException if remote communication with the RMI registry failed
     */
    public Server(int portServerSocket, Registry registry) throws RemoteException{
        this.matches = new HashMap<>();
        this.nicknamesToMatch = new HashMap<>();
        this.linkClientViews = new HashMap<>();
        this.portServerSocket = portServerSocket;
        this.registry = registry;
    }

    /**
     * Get the VirtualView of a player
     * @param nickname A String that identifies the player
     * @return the VirtualView of that player
     */
    public VirtualView getVirtuaView(String nickname) {
        return linkClientViews.get(nickname);
    }

    /**
     * Create a match in the Server
     * @param matchName A String that identifies the match
     * @param numPlayers the number of the players of the match
     * @param nickname A String that identifies the player who wants to create the match
     * @param client An interface that identifies the client in case of an RMI connection
     * @param socketHandler the server side socket handler of the player with a socket connection
     *                      who wants to create the match
     * @throws NotBoundException if an attempt is made to look for a name that is not currently
     *                           bound in the RMI registry
     * @throws RemoteException if remote communication with the RMI registry failed
     * @throws DuplicateNicknameException if there's already a player with that nickname
     * @throws WrongNumberOfPlayersException if numPlayers < 2 or numPlayers > 4
     * @throws DuplicateMatchException if there's already a match with that name
     * @throws AlreadyBoundException if an attempt is made to bind an object to a name
     *                               that already has an associated binding in the RMI registry.
     * @throws IllegalStateException if the method has been invoked at an illegal or inappropriate time.
     * @throws InvalidParameterException if the nickname is null
     */
    public synchronized void createMatch(String matchName, int numPlayers, String nickname, ClientStub client, ServerSideSocketHandler socketHandler)
            throws NotBoundException, RemoteException, AlreadyBoundException, DuplicateNicknameException, WrongNumberOfPlayersException,
            DuplicateMatchException, IllegalStateException, InvalidPlacementException, WrongInformationException, NotYourTurnException,
            InvalidParameterException, InvalidSearchPositionException, EmptyDeckException {

        if(matches.containsKey(matchName)) {
            throw new DuplicateMatchException("There's already a match with this name!");
        }

        //moved here in order to guarantee ATOMICITY
        if(nicknamesToMatch.containsKey(nickname)) {
            throw new DuplicateNicknameException();
        }

        Controller c = new Controller(numPlayers);
        matches.put(matchName, c);

        VirtualView v;
        if(client != null) {
           v = new VirtualViewRMI(nickname, client);
            registry.bind(nickname+"VirtualView", v);
        } else {
            v = new VirtualViewSocket(nickname, socketHandler);
        }
        linkClientViews.put(nickname, v);
        matches.get(matchName).addView(v);
        nicknamesToMatch.put(nickname, matchName);
        JoinMatchEvent e = new JoinMatchEvent(nickname, v);
        v.performEvent(e);
    }

    /**
     * Get a list of the matches
     * @return a List of match names
     */
    public synchronized List<String> getMatches() {
        return matches.keySet().stream().toList();
    }

    /**
     * Join a match
     * @param matchName The name of the match to join
     * @param nickname A String that identifies the player who wants to join the match
     * @param client An interface that identifies the client in case of an RMI connection
     * @param socketHandler the server side socket handler of the player with a socket connection
     *      *                      who wants to join the match
     * @throws NotBoundException if an attempt is made to look for a name that is not currently
     *                           bound in the RMI registry
     * @throws RemoteException if remote communication with the RMI registry failed
     * @throws DuplicateNicknameException if there's already a player with that nickname
     * @throws NoMatchException if there is not a match with that name
     * @throws AlreadyBoundException if an attempt is made to bind an object to a name
     *                               that already has an associated binding in the RMI registry.
     * @throws IllegalStateException if the method has been invoked at an illegal or inappropriate time.
     * @throws InvalidParameterException if the nickname is null
     * @throws WrongNumberOfPlayersException if there is already the maximum number of players in the lobby.
     */
    public synchronized void joinMatch(String matchName, String nickname, ClientStub client, ServerSideSocketHandler socketHandler)
            throws NotBoundException, RemoteException, AlreadyBoundException, DuplicateNicknameException, NoMatchException,  WrongNumberOfPlayersException,
            IllegalStateException, InvalidPlacementException, WrongInformationException, NotYourTurnException,
            InvalidParameterException, EmptyDeckException, InvalidSearchPositionException {
        if(!matches.containsKey(matchName)) {
            throw new NoMatchException("There isn't any match with this name!");
        }
        if(nicknamesToMatch.containsKey(nickname)) {
            throw new DuplicateNicknameException();
        }

        VirtualView v;
        if(client != null) {
            v = new VirtualViewRMI(nickname, client);
            registry.bind(nickname+"VirtualView", v);
        } else {
            v = new VirtualViewSocket(nickname, socketHandler);
        }

        linkClientViews.put(nickname, v);
        matches.get(matchName).addView(v);
        nicknamesToMatch.put(nickname, matchName);

        JoinMatchEvent e = new JoinMatchEvent(nickname, v);
        v.performEvent(e);
    }

    /**
     * Close a match for a player, and if the last players leaves, the pointer to that particular match will be
     * eliminated from the map containing that match, and all the links in the match itself are destroyed
     * @param nickName A String that identifies the player who wants to leave the match
     * @throws NoMatchException is the player is not part of a match
     * @throws NotBoundException if an attempt is made to look for a name that is not currently
     *                           bound in the RMI registry
     * @throws RemoteException if remote communication with the RMI registry failed
     */
    public synchronized void closeMatchForPlayer(String nickName) throws NoMatchException, NotBoundException, RemoteException {
        if(!(nicknamesToMatch.containsKey(nickName))){
            throw new NoMatchException("There isn't any match where you were playing to close!");
        }

        String match = nicknamesToMatch.get(nickName);
        VirtualView v = linkClientViews.get(nickName);
        try {
            registry.unbind(nickName + "VirtualView");
        }catch(NotBoundException ignored){
        }
        v.removeListener();

        GameModel gm = matches.get(match).getModel();
        int allPlayerExit = gm.removeListener(v);

        linkClientViews.remove(nickName, v);
        nicknamesToMatch.remove(nickName, match);

        if(allPlayerExit == 0) {
            matches.get(match).closeModel();
            matches.remove(match);
        }
    }


    /**
     *  Starts the server that waits for clients connection
     */
    public void start(){
        startServerSocket();
        out.println("Server is ready!");
        acceptClientsSocket();
    }

    /**
     * Starts the server socket.
     */
    private void startServerSocket() {
        try{
            serverSocket = new ServerSocket(portServerSocket);
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Every time one client links with the server, it creates
     * a thread where the server will communicate with this particular client.
     */
    private void acceptClientsSocket() {
        while(true){
            Socket clientSocket;
            try{
                clientSocket = serverSocket.accept();
                out.println(clientSocket.toString());
                new Thread(()->
                {
                    new ServerSideSocketHandler(clientSocket, this).run();
                }).start();
            } catch(IOException e){
                out.println("Client not connected");
            }
            out.println("Client accepted");
        }
    }
}
