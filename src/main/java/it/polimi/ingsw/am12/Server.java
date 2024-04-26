package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.Controller.Controller;
import it.polimi.ingsw.am12.Model.Logic.DuplicateNicknameException;
import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Model.Logic.WrongNumberOfPlayersException;
import it.polimi.ingsw.am12.View.VirtualView;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The server that contains all the ongoing matches
 */
public class Server extends UnicastRemoteObject implements Remote, Serializable {

    private final Registry registry;
    private final Map<String, Controller> matches;
    private final Map<String, String> nicknamesToMatch;
    private final Map<String, VirtualView> linkClientViews;
    private final ExecutorService executor;
    private final ServerSocket serverSocket;

    /**
     * Class constructor
     * @param port the port of the Socket Connection
     * @param registry the RMI registry
     * @throws IOException in an I/O error occurs while opening the Socket
     */
    public Server(int port, Registry registry) throws IOException {
        this.matches = new HashMap<>();
        this.nicknamesToMatch = new HashMap<>();
        this.linkClientViews = new HashMap<>();
        this.serverSocket = new ServerSocket(port);
        this.registry = registry;
        this.executor = Executors.newFixedThreadPool(128);
    }

    /**
     * Create a match in the Server
     * @param matchName A String that identifies the match
     * @param numPlayers the number of the players of the match
     * @param nickname A String that identifies the player who wants to create the match
     * @param connectionType The connection type (RMI or SOCKET) chosen by the player who wants to create the match
     * @return 0 if the match is created successfully
     * @throws NotBoundException if an attempt is made to look for a name that is not currently
     *                           bound in the RMI registry
     * @throws RemoteException if remote communication with the RMI registry failed
     * @throws DuplicateNicknameException if there's already a player with that nickname
     * @throws WrongNumberOfPlayersException if numPlayers < 2 or numPlayers > 4
     * @throws DuplicateMatchException if there's already a match with that name
     * @throws AlreadyBoundException if an attempt is made to bind an object to a name
     *                               that already has an associated binding in the RMI registry.
     */
    public synchronized int createMatch(String matchName, int numPlayers, String nickname, ConnectionType connectionType)
            throws NotBoundException, RemoteException, DuplicateNicknameException, WrongNumberOfPlayersException,
            DuplicateMatchException, AlreadyBoundException {

        if(matches.containsKey(matchName)) {
            throw new DuplicateMatchException("There's already a match with this name!");
        }

        //moved here in order to guarantee ATOMICITY
        if(nicknamesToMatch.containsKey(nickname)) {
            throw new DuplicateNicknameException();
        }

        Controller c = new Controller(numPlayers);
        matches.put(matchName, c);
        VirtualView v = new VirtualView(nickname, connectionType, registry);
        if(v.getConnectionType() == ConnectionType.RMI)
            registry.bind(nickname+"VirtualView", v);
        linkClientViews.put(nickname, v);
        matches.get(matchName).addView(v);
        nicknamesToMatch.put(nickname, matchName);
        return 0;
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
     * @param connectionType The connection type (RMI or SOCKET) chosen by the player who wants to join the match
     * @return 0 if the match is joined successfully
     * @throws NotBoundException if an attempt is made to look for a name that is not currently
     *                           bound in the RMI registry
     * @throws RemoteException if remote communication with the RMI registry failed
     * @throws DuplicateNicknameException if there's already a player with that nickname
     * @throws NoMatchException if there is not a match with that name
     * @throws AlreadyBoundException if an attempt is made to bind an object to a name
     *                               that already has an associated binding in the RMI registry.
     */
    public synchronized int joinMatch(String matchName, String nickname, ConnectionType connectionType)
            throws NotBoundException, RemoteException, DuplicateNicknameException, NoMatchException, AlreadyBoundException {
        if(!matches.containsKey(matchName)) {
            throw new NoMatchException("There isn't any match with this name!");
        }
        if(nicknamesToMatch.containsKey(nickname)) {
            throw new DuplicateNicknameException();
        }
        VirtualView v = new VirtualView(nickname, connectionType, registry);
        if(v.getConnectionType() == ConnectionType.RMI)
            registry.bind(nickname, v);
        linkClientViews.put(nickname, v);
        matches.get(matchName).addView(v);
        nicknamesToMatch.put(nickname, matchName);
        return 0;
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
        if(v.getConnectionType() == ConnectionType.RMI)
            registry.unbind(nickName+"VirtualView");

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
     * Starts the server that waits for clients connection, and every time one client links with the server, it creates
     * a thread where the server will communicate with this particular client.
     * @throws IOException if an I/O error occurs when waiting for a Socket connection.
     */
    public void start() throws IOException {
        while(true){
            Socket socket = serverSocket.accept();
            SocketConnection scConnection = new SocketConnection(socket, this);
            executor.submit(scConnection);
        }
    }
}
