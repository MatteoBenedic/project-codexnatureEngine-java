package it.polimi.ingsw.am12.Client.UI.CLI;

import it.polimi.ingsw.am12.Client.ClientController.ClientController;
import it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI.*;
import it.polimi.ingsw.am12.Client.UI.UserInterface;
import it.polimi.ingsw.am12.Network.Messages.CloseMatchConnectionMessage;
import it.polimi.ingsw.am12.Message;
import it.polimi.ingsw.am12.Network.Messages.NicknameMessage;
import it.polimi.ingsw.am12.Client.ViewModel.PropertyChangeEvents.PropertyChange;
import it.polimi.ingsw.am12.Utils.JSONParser;

import java.util.HashMap;
import java.util.Scanner;
import java.util.List;


/**
 * This class elaborates the commands from a CLI and creates the corresponding message to send
 * to the Client controller. It also saves the graphic objects to print.
 */
public class CLI implements UserInterface {

    ClientController controller;
    private HashMap<CommandInstruction, UserAction> useractions = new HashMap<>();
    private HashMap<RequestInstruction, UserRequest> userrequests = new HashMap<>();
    private CLIState currentState;
    private boolean isActive;
    private boolean isNicknameSet;
    private static final String MSG_MISSING_PARAMS = "Missing parameters for command: ";
    private static final String MSG_TOO_MANY_PARAMS = "Too many parameters for command: ";
    private static final String MSG_COMMAND_NOT_RECOGNIZED = "Command not recognized!";
    private HashMap<String, CLIDrawBufferGrid> playingGrids;
    private CLIDrawBufferTable drawtable;
    private CLIDrawBufferHand hand;
    private final List<CliCard> repCards;

    /**
     * Class constructor
     * @param controller the Client controller
     * Starts a thread that listens to input from the user and sends the corresponding
     * message to the controller
     */
    public CLI(ClientController controller) {
        this.controller = controller;
        controller.addViewModelListener(this);
        this.currentState = CLIState.WAITING_NICKNAME;
        this.isActive = true;
        this.isNicknameSet = false;

        JSONParser jsonParser = new JSONParser();
        repCards = jsonParser.parseCLICards();
        for(CliCard c : repCards)
            c.defineColouredRep();
        playingGrids = new HashMap<>();
        drawtable = new CLIDrawBufferTable(repCards);
        hand = new CLIDrawBufferHand(repCards);

        new Thread(this::readUserInput).start();
        inizializeUserActions();

    }


    /**
     * Listens to input from the user, switches between CLI states and sends the corresponding
     * message to the ClientController, which sends it to the Server
     */
    private void readUserInput() {
        Scanner cliScanner = new Scanner(System.in);
        while(isActive) {
            if (currentState == CLIState.WAITING_NICKNAME) {
                System.out.println("Enter nickname: ");
                String nickname = cliScanner.nextLine();
                NicknameMessage nicknameMessage = new NicknameMessage(nickname);
                currentState = CLIState.WAIT_FOR_UPDATE;
                controller.sendMessage(nicknameMessage);
            } else if (currentState == CLIState.WAITING_COMMAND) {
                String command = cliScanner.nextLine().trim();
                String trimmedCommand = command.replaceAll("\\s+", " ");
                String[] parts = trimmedCommand.split(" ", 2);
                String instruction = parts[0];

                CommandInstruction validCommandInstruction = null;
                for (CommandInstruction allowedCommandInstruction : CommandInstruction.values()) {
                    if (allowedCommandInstruction.getInstruction().equals(instruction)) {
                        validCommandInstruction = allowedCommandInstruction;
                        break;
                    }
                }

                RequestInstruction validRequestInstruction = null;
                for (RequestInstruction allowedRequestInstruction : RequestInstruction.values()) {
                    if (allowedRequestInstruction.getInstruction().equals(instruction)) {
                        validRequestInstruction = allowedRequestInstruction;
                        break;
                    }
                }

                if (validCommandInstruction != null) {
                    int expectedParams = validCommandInstruction.getNumParams();

                    String[] parameters = {};
                    if (parts.length > 1) {
                        String trimmedParams = parts[1].replaceAll("\\s+", " ");
                        trimmedParams = trimmedParams.trim();
                        parameters = trimmedParams.split(" ");
                    }
                    int actualParams = parameters.length;

                    if(validCommandInstruction.equals(CommandInstruction.CHAT)) {
                        if (actualParams > expectedParams)
                            actualParams = expectedParams;
                    }

                    if (actualParams == expectedParams) {
                        String param = (actualParams > 0) ? parts[1] : null;
                        Message message = useractions.get(validCommandInstruction).createMessage(param);
                        if (message != null) {
                            controller.sendMessage(message);
                            if (validCommandInstruction == CommandInstruction.END_GAME) {
                                currentState = CLIState.CLOSING_PHASE;
                            }
                        }
                    } else {
                        System.out.println("Expected " + expectedParams + " parameters");
                        if (actualParams < expectedParams) {
                            System.out.println(MSG_MISSING_PARAMS + instruction);
                        } else {
                            System.out.println(MSG_TOO_MANY_PARAMS + instruction);
                        }
                    }
                } else if (validRequestInstruction != null) {
                    int expectedParams = 0;
                    int actualParams = parts.length - 1;

                    if (actualParams == expectedParams) {
                        userrequests.get(validRequestInstruction).showRequest(this);
                    } else {
                        System.out.println("Expected " + expectedParams + " parameters");
                        System.out.println(MSG_TOO_MANY_PARAMS + instruction);
                    }
                } else {
                    System.out.println(MSG_COMMAND_NOT_RECOGNIZED);
                }
            }
            else if (currentState == CLIState.CLOSING_PHASE) {
                CloseMatchConnectionMessage closeConnectionMessage = new CloseMatchConnectionMessage();
                controller.sendMessage(closeConnectionMessage);
                isActive = false;
            } else if(currentState == CLIState.WAIT_FOR_UPDATE) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Sets the nickname on all the classes that implement UserAction
     * @param nickname a string containing the chosen nickname
     */
    private void setNicknameOnUserActions(String nickname) {
        for(UserAction userAction : useractions.values()){
            userAction.setNickname(nickname);
        }
        for(UserRequest userRequest : userrequests.values()){
            userRequest.setNickname(nickname);
        }
    }


    /**
     * Fills the useractions map with the corresponding UserAction class
     */
    private void inizializeUserActions(){
        useractions.put(CommandInstruction.CREATE_MATCH, new UserCreateMatch());
        useractions.put(CommandInstruction.GET_LOBBIES, new UserGetLobbies());
        useractions.put(CommandInstruction.JOIN_MATCH, new UserJoinMatch());
        useractions.put(CommandInstruction.START_MATCH, new UserStartMatch());
        useractions.put(CommandInstruction.PLACE_START_CARD, new UserPlaceStartCard());
        useractions.put(CommandInstruction.SELECT_PLAYER_COLOUR, new UserSelectColour());
        useractions.put(CommandInstruction.DISTRIBUTE_CARDS, new UserDistributeCards());
        useractions.put(CommandInstruction.SELECT_OBJECTIVE, new UserSelectObjective());
        useractions.put(CommandInstruction.GET_PLACEABLE_POSITIONS, new UserGetPlaceablePositions());
        useractions.put(CommandInstruction.PLACE_CARD, new UserPlaceCard());
        useractions.put(CommandInstruction.DRAW_CARD, new UserDrawCard());
        useractions.put(CommandInstruction.END_GAME, new UserEndGame());
        useractions.put(CommandInstruction.CHAT, new UserChat());

        userrequests.put(RequestInstruction.GET_MY_HAND, new UserRequestHand());
        userrequests.put(RequestInstruction.GET_MY_PLAYING_GRID, new UserRequestPlayingGrid());
        userrequests.put(RequestInstruction.GET_MY_DRAW_TABLE, new UserRequestDrawTable());
    }

    /**
     * Notify the CLI that a property in the ViewModel changed
     * @param p an instance of PropertyChange that describes with property has changed
     */
    @Override
    public void propertyChange(PropertyChange p) {
        p.updateCLI(this);
    }

    /**
     * Allow user to enter commands once his nickname hab been set
     * @param nickname the user nickname
     */
    public void setNickname(String nickname) {
        setNicknameOnUserActions(nickname);
        this.isNicknameSet = true;
        currentState = CLIState.WAITING_COMMAND;
    }

    /**
     * Allow user to enter commands
     */
    public void enableCommand() {
        if(isNicknameSet) {
            currentState = CLIState.WAITING_COMMAND;
        }
        else {
            currentState = CLIState.WAITING_NICKNAME;
        }
    }

    /**
     * Adds a new playing grid denominated in the playinggrids map by the nickname of the owner
     * @param nickname the name of the new player
     */
    public void addPlayer(String nickname) {
        playingGrids.put(nickname, new CLIDrawBufferGrid(repCards));
    }

    /**
     * @return a map that contains all the playing areas
     */
    public HashMap<String, CLIDrawBufferGrid> getPlayingGrids() {
        return playingGrids;
    }

    /**
     * @return the buffer that prints the drawtable with the drawable cards
     */
    public CLIDrawBufferTable getDrawtable() {
        return drawtable;
    }

    /**
     * @return the buffer that prints the cards in the hand of this player
     */
    public CLIDrawBufferHand getHand() {
        return hand;
    }
}