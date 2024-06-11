package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.Controller.Controller;
import it.polimi.ingsw.am12.Exceptions.IllegalStateException;
import it.polimi.ingsw.am12.Model.Logic.State;
import it.polimi.ingsw.am12.Network.Messages.Events.JoinMatchEvent;
import it.polimi.ingsw.am12.Exceptions.NoNicknameException;
import it.polimi.ingsw.am12.Exceptions.NoMatchException;
import it.polimi.ingsw.am12.Exceptions.DuplicateNicknameException;
import it.polimi.ingsw.am12.Exceptions.DuplicateMatchException;
import it.polimi.ingsw.am12.Exceptions.*;

import it.polimi.ingsw.am12.Model.Logic.GameModel;
import it.polimi.ingsw.am12.Network.ServerSideSocketHandler;
import it.polimi.ingsw.am12.Network.Messages.Updates.LobbiesNonCompletedUpdate;
import it.polimi.ingsw.am12.Network.Messages.Updates.NicknameEstablishedUpdate;
import it.polimi.ingsw.am12.VirtualView.VirtualViewSocket;
import it.polimi.ingsw.am12.VirtualView.VirtualView;
import it.polimi.ingsw.am12.VirtualView.VirtualViewRMI;

import java.io.IOException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
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
    private final List<String> nicknames;
    private final Map<String, Integer> lobbiesToComplete;
    private ServerSocket serverSocket;
    private int portServerSocket;

    /**
     * Class constructor
     *
     * @param portServerSocket the port of the Socket Connection
     * @param registry         the RMI registry
     * @throws RemoteException if remote communication with the RMI registry failed
     */
    public Server(int portServerSocket, Registry registry) throws RemoteException {
        this.matches = new HashMap<>();
        this.nicknamesToMatch = new HashMap<>();
        this.linkClientViews = new HashMap<>();
        nicknames = new ArrayList<>();
        lobbiesToComplete = new HashMap<>();
        this.portServerSocket = portServerSocket;
        this.registry = registry;
    }

    /**
     * Get the VirtualView of a player
     *
     * @param nickname A String that identifies the player
     * @return the VirtualView of that player
     */
    public VirtualView getVirtuaView(String nickname) {
        return linkClientViews.get(nickname);
    }

    /**
     * Sets the nickname of the client that calls this remote method or by the socket handler connected to it
     *
     * @param nickname      the nickname with whom wants to be identified
     * @param client        An interface that identifies the client in case of an RMI connection
     * @param socketHandler the server side socket handler of the player with a socket connection
     *                      who wants to create the match
     * @throws RemoteException            if remote communication with the RMI registry failed
     * @throws NotBoundException          if an attempt is made to look for a name that is not currently
     *                                    bound in the RMI registry
     * @throws AlreadyBoundException      if an attempt is made to bind an object to a name
     *                                    that already has an associated binding in the RMI registry.
     * @throws DuplicateNicknameException if there's already a player with that nickname
     */
    public synchronized void setNickname(String nickname, ClientStub client, ServerSideSocketHandler socketHandler) throws RemoteException,
            NotBoundException, AlreadyBoundException, DuplicateNicknameException {
        if (nicknames.contains(nickname)) {
            throw new DuplicateNicknameException("Nickname already in use");
        }
        nicknames.add(nickname);

        VirtualView v;
        if (client != null) {
            v = new VirtualViewRMI(nickname, client);
            registry.bind(nickname + "VirtualView", v);
        } else {
            v = new VirtualViewSocket(nickname, socketHandler);
        }

        linkClientViews.put(nickname, v);

        NicknameEstablishedUpdate u = new NicknameEstablishedUpdate(nickname);
        new Thread(() ->
        {
            v.sendUpdate(u);
        }).start();

    }

    /**
     * Gives to the client the incomplete lobbies that he could join
     *
     * @param nickname the nickname of the user
     * @throws NoNicknameException if there is no such user with that nickname
     */
    public synchronized void getIncompleteLobbies(String nickname) throws NoNicknameException, RemoteException {
        if (!nicknames.contains(nickname))
            throw new NoNicknameException("This nickname hasn't been set");

        VirtualView v = linkClientViews.get(nickname);
        LobbiesNonCompletedUpdate u = new LobbiesNonCompletedUpdate(lobbiesToComplete);
        new Thread(() ->
        {
            v.sendUpdate(u);
        }).start();
    }

    /**
     * Create a match in the Server
     *
     * @param matchName  A String that identifies the match
     * @param numPlayers the number of the players of the match
     * @param nickname   A String that identifies the player who wants to create the match
     * @throws RemoteException               if remote communication with the RMI registry failed
     * @throws DuplicateNicknameException    if there's already a player with that nickname
     * @throws WrongNumberOfPlayersException if numPlayers < 2 or numPlayers > 4
     * @throws DuplicateMatchException       if there's already a match with that name
     * @throws IllegalStateException         if the method has been invoked at an illegal or inappropriate time.
     * @throws InvalidParameterException     if the nickname is null
     * @throws NoNicknameException           if there is no such user with that nickname
     */
    public synchronized void createMatch(String matchName, int numPlayers, String nickname)
            throws RemoteException, DuplicateNicknameException, WrongNumberOfPlayersException,
            DuplicateMatchException, IllegalStateException, InvalidPlacementException, WrongInformationException, NotYourTurnException,
            InvalidParameterException, InvalidSearchPositionException, NoNicknameException, EmptyDeckException {

        if (matches.containsKey(matchName)) {
            throw new DuplicateMatchException("There's already a match with this name!");
        }
        if (!nicknames.contains(nickname)) {
            throw new NoNicknameException("This nickname hasn't been set");
        }
        if (nicknamesToMatch.containsKey(nickname)) {
            throw new DuplicateNicknameException("There's already someone playing in a match with this nickname");
        }

        Controller c = new Controller(numPlayers);

        matches.put(matchName, c);

        lobbiesToComplete.put(matchName, numPlayers - 1);

        VirtualView v = linkClientViews.get(nickname);
        matches.get(matchName).addView(v);

        JoinMatchEvent e = new JoinMatchEvent(nickname, v);
        v.performEvent(e);

        nicknamesToMatch.put(nickname, matchName);
    }

    /**
     * Join a match
     *
     * @param matchName The name of the match to join
     * @param nickname  A String that identifies the player who wants to join the match
     * @throws RemoteException               if remote communication with the RMI registry failed
     * @throws DuplicateNicknameException    if there's already a player with that nickname
     * @throws NoMatchException              if there is not a match with that name
     * @throws IllegalStateException         if the method has been invoked at an illegal or inappropriate time.
     * @throws InvalidParameterException     if the nickname is null
     * @throws WrongNumberOfPlayersException if there is already the maximum number of players in the lobby.
     * @throws NoNicknameException           if there is no such user with that nickname
     */
    public synchronized void joinMatch(String matchName, String nickname)
            throws RemoteException, DuplicateNicknameException, NoMatchException, WrongNumberOfPlayersException,
            IllegalStateException, InvalidPlacementException, WrongInformationException, NotYourTurnException,
            InvalidParameterException, EmptyDeckException, InvalidSearchPositionException, NoNicknameException {
        if (!matches.containsKey(matchName)) {
            throw new NoMatchException("There isn't any match with this name!");
        }
        if (!nicknames.contains(nickname)) {
            throw new NoNicknameException("This nickname hasn't been set");
        }
        if (nicknamesToMatch.containsKey(nickname)) {
            throw new DuplicateNicknameException("There's already someone playing in a match with this nickname");
        }


        VirtualView v = linkClientViews.get(nickname);
        try {
            matches.get(matchName).addView(v);
            JoinMatchEvent e = new JoinMatchEvent(nickname, v);
            v.performEvent(e);
        } catch (DuplicateNicknameException e) {
            matches.get(matchName).getModel().removeListener(v);
            throw new DuplicateNicknameException(e.getMessage());
        } catch (WrongNumberOfPlayersException e) {
            matches.get(matchName).getModel().removeListener(v);
            throw new WrongNumberOfPlayersException(e.getMessage());
        }
        nicknamesToMatch.put(nickname, matchName);

        if (lobbiesToComplete.get(matchName) < 2)
            lobbiesToComplete.remove(matchName);
        else {
            int value = lobbiesToComplete.get(matchName);
            value--;
            lobbiesToComplete.remove(matchName);
            lobbiesToComplete.put(matchName, value);
        }
    }

    /**
     * Close a match for a player, and if the last players leaves, the pointer to that particular match will be
     * eliminated from the map containing that match, and all the links in the match itself are destroyed
     *
     * @param nickName A String that identifies the player who wants to leave the match
     * @throws NoMatchException is the player is not part of a match
     * @throws RemoteException  if remote communication with the RMI registry failed
     */
    public synchronized void closeMatchForPlayer(String nickName) throws NoMatchException, RemoteException {
        System.out.println("Closing match for player " + nickName + "...");
        try {
            if (!(nicknamesToMatch.containsKey(nickName))) {
                throw new NoMatchException("There isn't any match where you were playing to close!");
            }

            String match = nicknamesToMatch.get(nickName);
            VirtualView v = linkClientViews.get(nickName);
            try {
                registry.unbind(nickName + "VirtualView");
            } catch (NotBoundException ignored) {
            }
            v.removeListener();

            GameModel gm = matches.get(match).getModel();
            int allPlayerExit = gm.removeListener(v);

            linkClientViews.remove(nickName, v);
            nicknamesToMatch.remove(nickName, match);

            if (allPlayerExit == 0) {
                matches.get(match).closeModel();
                matches.remove(match);
            }
        } catch (NullPointerException ignored) {
        }

    }


    /**
     * Starts the server that waits for clients connection
     */
    public void start() {
        startServerSocket();
        out.println("Server is ready!");
        acceptClientsSocket();
    }

    /**
     * Starts the server socket.
     */
    private void startServerSocket() {
        try {
            serverSocket = new ServerSocket(portServerSocket);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Every time one client links with the server, it creates
     * a thread where the server will communicate with this particular client.
     */
    private void acceptClientsSocket() {
        while (true) {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
                out.println(clientSocket.toString());
                new Thread(() ->
                {
                    new ServerSideSocketHandler(clientSocket, this).run();
                }).start();
            } catch (IOException e) {
                out.println("Client not connected");
            }
            out.println("Client accepted");
        }
    }

    /**
     * Returns the list of nicknames of the players in a match
     * @param matchName a String identifying the match name
     * @return a List of nicknames
     */
    public List<String> getNicknamesInAMatch(String matchName) {
        List<String> nicknamesInAMatch = new ArrayList<>();
        for (Map.Entry<String, String> entry : nicknamesToMatch.entrySet()) {
            if (entry.getValue().equals(matchName)) {
                nicknamesInAMatch.add(entry.getKey());
            }
        }
        return nicknamesInAMatch;
    }

    /**
     * Closes the match for all players in a game, notifying them of the termination
     * @param matchName a String that identifies the match to close
     * @throws RemoteException if remote communication with the RMI registry failed
     */
    public synchronized void closeMatchForAllPlayers(String matchName, String disconnectedPlayer) throws RemoteException {
        System.err.println("Player " + disconnectedPlayer + " has disconnected. Closing match " + matchName + " for everyone");
        try {
            closeMatchForPlayer(disconnectedPlayer);
        } catch (NoMatchException e) {
            System.err.println("No match to close for player " + disconnectedPlayer + ": " + e.getMessage());
        }
        printNicknamesToMatch();
        //Notify the remaining connected players before closing the match
        notifyMatchTermination(matchName);

        List<String> playersInMatch = getNicknamesInAMatch(matchName);
        for (String player : playersInMatch) {
            try {
                closeMatchForPlayer(player);
            } catch (NoMatchException e) {
                System.err.println("No match to close for player " + player + ": " + e.getMessage());
            }
        }
        printNicknamesToMatch();
    }

    /**
     * Notifies all players in a match that the match has been terminated
     *
     * @param matchName a String with the name of the match that has been terminated
     * @throws RemoteException if remote communication with the RMI registry failed
     */
    public synchronized void notifyMatchTermination(String matchName) throws RemoteException {
        if (matches.containsKey(matchName)) {
            Controller gameController = matches.get(matchName);
            gameController.getModel().forceEndGame();
        }
    }

    /**
     * Handles the disconnection of a player: closes the match in which the player was
     * @param nickname a String with the nickname of the disconnected player
     * @throws RemoteException if remote communication with the RMI registry failed
     */
    public synchronized void playerDisconnectionHandler(String nickname) throws RemoteException {
        //System.out.println("Player " + nickname + " has asked to close the match or has lost connection");
        String matchName = nicknamesToMatch.get(nickname);
        if (matchName != null) {
            System.out.println("Player " + nickname + " has just disconnected or lost connection from match " + matchName);
            closeMatchForAllPlayers(matchName, nickname);
        } else {
            System.err.println("No match found for player " + nickname);
        }
    }

    /**
     * Displays, for each player connected to the server, the corresponding match name
     */
    public void printNicknamesToMatch() {
        System.out.println("Server{" + "nicknamesToMatch=" + nicknamesToMatch + "}");
    }

    /**
     * Returns the state of the game in which a given player is
     * @param nickname a String with the nickname of the player
     * @return the game State if the player is in a match; null otherwise;
     */
    public State getGameStateFromNickname(String nickname) {
        String matchName = nicknamesToMatch.get(nickname);
        if (matches.containsKey(matchName)) {
            Controller gameController = matches.get(matchName);
            return gameController.getModel().getState();
        }
        else return null;
    }

}