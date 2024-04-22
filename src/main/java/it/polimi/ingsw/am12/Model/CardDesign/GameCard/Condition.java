package it.polimi.ingsw.am12.Model.CardDesign.GameCard;

import com.google.gson.annotations.SerializedName;
/**
 * This class is used to define the conditions that can multiply the points of the side of the card
 * where this condition is located. The points have to be multiplied the number of times this
 * condition is achieved.
 */
public enum Condition {
    @SerializedName("corner")
    CORNER(),
    @SerializedName("inkwell")
    INKWELL (Element.INKWELL),
    @SerializedName("manuscript")
    MANUSCRIPT (Element.MANUSCRIPT),
    @SerializedName("quill")
    QUILL (Element.QUILL);

    private Element name;

    Condition(){}
    Condition(Element name) {
        this.name = name;
    }

    public Element getName() {
        return name;
    }
}
