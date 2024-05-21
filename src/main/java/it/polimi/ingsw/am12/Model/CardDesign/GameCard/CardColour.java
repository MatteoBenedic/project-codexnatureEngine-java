package it.polimi.ingsw.am12.Model.CardDesign.GameCard;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration that contains all the possible card colours
 */
public enum CardColour {
    @SerializedName("green")
    GREEN("green"),

    @SerializedName("blue")
    BLUE("blue"),

    @SerializedName("red")
    RED("red"),
    @SerializedName("purple")
    PURPLE("purple");


    private String name;

    CardColour(String name) {
        this.name = name;
    }

}
