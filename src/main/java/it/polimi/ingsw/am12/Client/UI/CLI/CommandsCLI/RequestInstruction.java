package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

/**
 * This enum lists the existing commands to request some information
 * to be show
 */
public enum RequestInstruction {
    GET_MY_HAND("showmycardsinhand"),
    GET_MY_PLAYING_GRID("showmygrid"),
    GET_MY_DRAW_TABLE("showdrawtable");

    private String instruction;

    /**
     * Class constructor
     * @param instruction the String command
     */
    RequestInstruction(String instruction){
        this.instruction = instruction;
    }

    /**
     * Get the String command
     * @return the String command
     */
    public String getInstruction(){
        return instruction;
    }
}
