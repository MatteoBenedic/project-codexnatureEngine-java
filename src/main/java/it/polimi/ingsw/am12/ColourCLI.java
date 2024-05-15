package it.polimi.ingsw.am12;

import com.google.gson.annotations.SerializedName;

public enum ColourCLI {
    @SerializedName("reset")
    RESET("\u001B[0m"),

    @SerializedName("red")
    RED("\u001B[31m"),

    @SerializedName("blue")
    BLUE("\u001B[34m"),

    @SerializedName("purple")
    PURPLE("\u001B[35m"),

    @SerializedName("green")
    GREEN("\u001B[32m"),

    @SerializedName("yellow")
    YELLOW("\u001B[33m");


    public String s;
    ColourCLI(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getColour(){return s;}
}
