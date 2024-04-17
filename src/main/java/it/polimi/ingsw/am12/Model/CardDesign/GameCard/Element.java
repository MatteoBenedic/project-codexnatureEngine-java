package it.polimi.ingsw.am12.Model.CardDesign.GameCard;

import com.google.gson.annotations.SerializedName;

/**
 * This enumeration defines all the types of symbols that can be present in a corner or as a list in the center
 */
public enum Element{
    @SerializedName("animal")
    ANIMAL ("animal"),
    @SerializedName("fungus")
    FUNGUS ("fungus"),
    @SerializedName("inkwell")
    INKWELL ("inkwell"),
    @SerializedName("insect")
    INSECT ("insect"),
    @SerializedName("manuscript")
    MANUSCRIPT ("manuscript"),
    @SerializedName("plant")
    PLANT ("plant"),
    @SerializedName("quill")
    QUILL ("quill");

    private String name;

    Element(String name) {
        this.name = name;
    }
}
