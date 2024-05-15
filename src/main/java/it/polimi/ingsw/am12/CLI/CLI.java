package it.polimi.ingsw.am12.CLI;

import it.polimi.ingsw.am12.*;
import it.polimi.ingsw.am12.CommandsCLI.*;
import it.polimi.ingsw.am12.PropertyChangeEvents.PropertyChange;
import it.polimi.ingsw.am12.Utils.JSONParser;

import java.util.HashMap;
import java.util.Scanner;
import java.util.List;

/**
 * This class elaborates the commands from a CLI and creates the corresponding message to send
 * to the Client controller.
 */
public class CLI implements UserInterface {

    ClientController controller;
    private HashMap<CommandInstruction, UserAction> useractions = new HashMap<>();
    private CLIState currentState;
    private boolean isActive;
    private boolean isNicknameSet;
    private static final String MSG_INVALID_PARAMS = "Invalid parameters for command: ";
    private static final String MSG_MISSING_PARAMS = "Missing parameters for command: ";
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
            if(currentState == CLIState.WAITING_NICKNAME){
                System.out.println("Enter nickname: ");
                String nickname = cliScanner.nextLine();
                NicknameMessage nicknameMessage = new NicknameMessage(nickname);
                currentState = CLIState.WAIT_FOR_UPDATE;
                controller.sendMessage(nicknameMessage);
            }
            else if(currentState == CLIState.WAITING_COMMAND) {
                System.out.println("Enter command: ");
                //CommandInstruction = instruction + params

                String command = cliScanner.nextLine().trim();
                //String[] parts = command.split(" ", 2);
                String trimmedCommand = command.replaceAll("\\s+", " ");
                String[] parts = trimmedCommand.split(" ", 2);
                String instruction = parts[0];

                CommandInstruction validCommandInstruction = null;
                for(CommandInstruction allowedCommandInstruction : CommandInstruction.values()){
                    if(allowedCommandInstruction.getInstruction().equals(instruction)){
                        validCommandInstruction = allowedCommandInstruction;
                        break;
                    }
                }

                if(validCommandInstruction != null){
                    int expectedParams = validCommandInstruction.getNumParams();
                    System.out.println("Expected " + expectedParams + " parameters");
                    String[] parameters = {};
                    if(parts.length > 1){
                        String trimmedParams = parts[1].replaceAll("\\s+", " ");
                        trimmedParams = trimmedParams.trim();
                        parameters = trimmedParams.split(" ");
                    }
                    int actualParams = parameters.length;
                    System.out.println("Actual parameters: " + actualParams);

                    if (actualParams == expectedParams) {
                        String param = (actualParams > 0) ? parts[1] : null;
                        Message message = useractions.get(validCommandInstruction).createMessage(param);
                        if (message != null) {
                            controller.sendMessage(message);
                            if(validCommandInstruction == CommandInstruction.END_GAME){
                                currentState = CLIState.CLOSING_PHASE;
                            }
                        }
                        else {
                            System.out.println(MSG_INVALID_PARAMS + instruction);
                        }
                    } else {
                        if (actualParams < expectedParams) {
                            System.out.println(MSG_MISSING_PARAMS + instruction);
                        } else {
                            System.out.println("Too many parameters for command: " + instruction);
                        }
                    }
                } else {
                    System.out.println(MSG_COMMAND_NOT_RECOGNIZED);
                }

            } else if (currentState == CLIState.CLOSING_PHASE) {
                System.out.println("Type q to quit...");
                CloseMatchConnectionMessage closeConnectionMessage = new CloseMatchConnectionMessage();
                controller.sendMessage(closeConnectionMessage);
                isActive = false;
            } else if(currentState == CLIState.WAIT_FOR_UPDATE) {
                try {
                    Thread.sleep(1000);
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
     * Show the current playing grid of the player with all cards already placed
     * @param playingGrid the playing grid of the player
     */
    public void printPlayingGrid(List<ClientCard> playingGrid) {
        System.out.println("Your playing grid: ");
        for(ClientCard c : playingGrid) {
            String s = "Card " + c.getIndex() + " on ";
            if(c.getSide())
                s+="front";
            else
                s+="back";
            s+= " in position (" + c.getCoordinates().getX() + ", " + c.getCoordinates().getY() + ") ";
            System.out.println(s);
        }
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

    public void addPlayer(String nickname) {
        playingGrids.put(nickname, new CLIDrawBufferGrid(repCards));
    }


    public HashMap<String, CLIDrawBufferGrid> getPlayingGrids() {
        return playingGrids;
    }

    public CLIDrawBufferTable getDrawtable() {
        return drawtable;
    }

    public CLIDrawBufferHand getHand() {
        return hand;
    }
}