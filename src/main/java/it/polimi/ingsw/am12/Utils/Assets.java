package it.polimi.ingsw.am12.Utils;

import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;

/**
 * Utility class to get the correct asset of a card
 */
public class Assets {

    /**
     * Get the filename of the asset of a card
     * @param cardIndex the index of the card
     * @param side TRUE = front, FALSE = back
     * @return the filename of the asset
     */
    public String getFileName(int cardIndex, boolean side) {
        String filename ="";
        if(side)
            filename+="front";
        else
            filename+="back";

        int c0 = cardIndex%10;
        cardIndex = cardIndex /10;
        int c1 = cardIndex%10;
        cardIndex = cardIndex /10;
        int c2 = cardIndex%10;

        filename += "_";
        filename += c2;
        filename += c1;
        filename += c0;

        filename+=".png";

        return filename;
    }

    /**
     * Get the filename of the asset of the back of a gold card
     * @param colour the colour of the card
     * @return the filename of the asset
     */
    public String getGoldBack(CardColour colour) {
        switch (colour) {
            case RED -> {
                return "back_040.png";
            }
            case GREEN -> {
                return "back_050.png";
            }
            case BLUE -> {
                return "back_060.png";
            }
            case PURPLE -> {
                return "back_070.png";
            }
        }
        return null;
    }

    /**
     * Get the filename of the asset of the back of a resource card
     * @param colour the colour of the card
     * @return the filename of the asset
     */
    public String getResourceBack(CardColour colour) {
        switch (colour) {
            case RED -> {
                return "back_000.png";
            }
            case GREEN -> {
                return "back_010.png";
            }
            case BLUE -> {
                return "back_020.png";
            }
            case PURPLE -> {
                return "back_030.png";
            }
        }
        return null;
    }

}
