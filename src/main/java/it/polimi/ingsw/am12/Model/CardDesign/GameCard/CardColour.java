package it.polimi.ingsw.am12.Model.CardDesign.GameCard;

import com.google.gson.annotations.SerializedName;

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
