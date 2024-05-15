package it.polimi.ingsw.am12.CommandsCLI;

/**
 * This enum lists the existing commands for the Command Line Interface,
 * binding them the required number of parameters
 */
public enum CommandInstruction {

    CREATE_MATCH("creatematch", 2),
    GET_LOBBIES("getlobbies", 0),
    JOIN_MATCH("joinmatch", 1),
    START_MATCH("startmatch", 0),
    PLACE_START_CARD("placestartcard", 1),
    SELECT_PLAYER_COLOUR("selectmycolour", 1),
    DISTRIBUTE_CARDS("distributecards", 0),
    SELECT_OBJECTIVE("selectobjective", 1),
    GET_PLACEABLE_POSITIONS("getpos", 2),
    PLACE_CARD("placecard", 4),
    DRAW_CARD("drawcard", 1),
    END_GAME("endgame", 0),
    //close connection
    QUIT("q", 0);

    private final String instruction;
    private final int numParams;

    /**
     * Class constructor
     * @param instruction the String command
     * @param numParams the number of parameters
     */
    CommandInstruction(String instruction, int numParams){
        this.instruction = instruction;
        this.numParams = numParams;
    }

    /**
     * Get the String command
     * @return the String command
     */
    public String getInstruction(){
        return instruction;
    }

    /**
     * Get the number of parameters
     * @return the number of parameters
     */
    public int getNumParams() {return numParams;}
}
