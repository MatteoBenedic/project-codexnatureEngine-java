package it.polimi.ingsw.am12.Client.UI.CLI.CommandsCLI;

/**
 * This enum represents the possible states of the Command Line Interface
 */
public enum CLIState {
    WAITING_NICKNAME,
    WAITING_COMMAND,
    CLOSING_PHASE,
    WAIT_FOR_UPDATE,
    MATCH_STOPPED;
}
