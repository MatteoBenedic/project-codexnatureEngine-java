package it.polimi.ingsw.am12.Client.UI.CLI;

import java.util.ArrayList;
import java.util.List;

/**
 * Buffer for the CLI that draws and prints the cards in the hand of the player
 */
public class CLIDrawBufferHand implements CLIDrawBuffer {
    public List<String> cardsinhand = new ArrayList<>();
    private static final int SPACE_BETWEEN_CARDS = 4;
    private static final int NUM_CARDS = 3;

    private static final int FIRSTCARD = 0;
    private static final int SECONDCARD = 1;
    private static final int THIRDCARD = 2;

    private List<CliCard> repCards;

    /**
     * Class constructor
     * @param repCards the list of all the cards in form of CliCards given by the CLI
     */
    public CLIDrawBufferHand(List<CliCard> repCards) {
        this.repCards = repCards;
    }

    /**
     * It inserts the new hand in the buffer
     * @param indexes a list of 3 integers that represents each a card of the new hand, if the index is a -1,
     *                it leaves a hole in the hand
     */
    public void insertCardsInBuffer(List<Integer> indexes){
        int len = indexes.size();
        List<List<String>> cards = new ArrayList<>();
        for(int i = 0; i < len; i++) {
            cards.add(extractCardfromIndex(indexes.get(i), true));
        }

        for(int i = len; i <= NUM_CARDS; i++)
            cards.add(extractCardfromIndex(-1, true));

        setBuffer(cards);
    }

    /**
     * Method to transform the integer value, the index, that travels in the network in the graphical and printable way, a
     * list of strings
     * @param index the integer value corresponding to a exact card
     * @param side a boolean that represents the side of the card wanted by the player
     * @return a list of strings, the representation of the card
     */
    @Override
    public List<String> extractCardfromIndex(int index, boolean side) {
        List<String> newCard = new ArrayList<>();
        int srcIndex;

        if(!side) {
            if (index < 80)
                srcIndex = (index % 40)/ 10 + DELTA_BACKS;
            else
                srcIndex = index + DELTA_STARTBACKS;
        }else
            srcIndex = index;

        for(CliCard c: repCards)
            if(c.getIndex() == srcIndex){
                newCard = c.getColouredRep();
                break;
            }

        return newCard;
    }

    /**
     * Method used to print the buffer in the CLI
     */
    @Override
    public void printBuffer() {
        System.out.println("This is your hand: ");

        for(String s : cardsinhand)
            System.out.println(s + ColourCLI.RESET.getColour());
    }

    /**
     * Method used to draw the cards
     * @param cards the list of the representantion of the cards
     */
    private void setBuffer(List<List<String>> cards){
        cardsinhand.clear();
        for(int i = 0; i < HEIGHT_CARD; i++){
            String newString = cards.get(FIRSTCARD).get(i) + SPACE.repeat(SPACE_BETWEEN_CARDS) + cards.get(SECONDCARD).get(i) +
                    SPACE.repeat(SPACE_BETWEEN_CARDS) + cards.get(THIRDCARD).get(i);

            cardsinhand.add(newString);
        }

    }
}
