package it.polimi.ingsw.am12.Model.Logic;


/**
 * The enum Player color.
 */
public enum PlayerColour {
    /**
     * Blue player color.
     */
    BLUE("blue"),
    /**
     * Red player color.
     */
    RED("red"),
    /**
     * Yellow player color.
     */
    YELLOW("yellow"),
    /**
     * Green player color.
     */
    GREEN("green"),
    /**
     * Black player color.
     */
    BLACK("black");

    private String description;

    /**
     * Enum constructor
     * @param description the colour as a string
     */
    PlayerColour(String description) {
        this.description = description;
    }


    /**
     * Get colour description
     * @return the colour as a string.
     */
    public String getDescription() {
        return description;
    }
}
