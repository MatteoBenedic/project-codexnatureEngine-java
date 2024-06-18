package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

/**
 * This enum lists the existing commands to request some information
 * to be show
 */
public enum RequestInstruction {
    GET_MY_HAND("showmycardsinhand", 0),
    GET_MY_PLAYING_GRID("showmygrid", 0),
    GET_MY_DRAW_TABLE("showdrawtable", 0),
    GET_FLIPPED_CARD("flipcard", 1),
    GET_MY_OBJECTIVES("showobjectives", 0);

    private String instruction;
    private int numParams;

    /**
     * Class constructor
     * @param instruction the String command
     */
    RequestInstruction(String instruction, int numParams){
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
