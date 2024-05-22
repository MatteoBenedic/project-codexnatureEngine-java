package it.polimi.ingsw.am12.Client.UI.CLI;

import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;

import java.util.ArrayList;
import java.util.List;

/**
 * Buffer for the CLI that draws and prints the cards of the draw table
 */
public class CLIDrawBufferTable implements CLIDrawBuffer {
    List<String> bufferTable = new ArrayList<>();
    private static final int SPACE_BETWEEN_CARDS = 4;
    private static final int SPACE_BETWEEN_LINES = 2;
    private static final int NUM_CARDS_INLINE = 3;
    private List<CliCard> repCards;

    /**
     * Class constructor
     * @param repcards the list of all the cards in form of CliCards given by the CLI
     */
    public CLIDrawBufferTable(List<CliCard> repcards) {
        this.repCards = repcards;

        String space = SPACE.repeat(NUM_CARDS_INLINE*(LENGTH_CARD + COD_COLOUR) + SPACE_BETWEEN_CARDS*(NUM_CARDS_INLINE-1));
        for(int i = 0; i < (HEIGHT_CARD*2 + SPACE_BETWEEN_LINES); i++)
            bufferTable.add(space);
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

        if(!side)
            srcIndex = (index % 40)/ 10 + DELTA_BACKS;
        else
            srcIndex = index;

        for(CliCard c: repCards)
            if(c.getIndex() == srcIndex){
                newCard = c.getColouredRep();
                break;
            }

        return newCard;
    }

    /**
     * Method to transform the enumeration value, the colour, that travels in the network, in the graphical and printable way, a
     * list of strings
     * @param colour the colour of the card to print on its back
     * @return a list of strings, the representation of the card
     */
    public List<String> extractCardfromColour(CardColour colour){
        List<String> newCard = new ArrayList<>();
        int srcIndex = -1;
        switch(colour){
            case RED:
                srcIndex = DELTA_BACKS;
                break;
            case GREEN:
                srcIndex = DELTA_BACKS + 1;
                break;
            case BLUE:
                srcIndex = DELTA_BACKS + 2;
                break;
            case PURPLE:
                srcIndex = DELTA_BACKS + 3;
                break;
            default:
                break;
        }

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
        System.out.println("This is the draw table: ");
        for(String s : bufferTable)
            System.out.println(s + ColourCLI.RESET.getColour());
    }

    /**
     * It replaces the cards drawn or moved with the new cards to represent on the public card place
     * @param index the index of the card to insert
     * @param position the index that represents the place where to insert the card
     */
    public void insertCardInBuffer(int index, int position){
        List<String> newCard = extractCardfromIndex(index, true);
        switch(position){
            case 0:
                 replaceFirstGold(newCard);
                break;
            case 1:
                replaceSecondGold(newCard);
                break;
            case 2:
                replaceFirstResource(newCard);
                break;
            case 3:
                replaceSecondResource(newCard);
                break;
            default:
                break;
        }
    }

    /**
     * It replaces the top of the deck chosen with the back of a coloured card
     * @param position the index which represents the deck where to replace the top
     * @param colour the colour of the card to insert on top of the chosen deck
     */
    public void insertCardInDeck(int position, CardColour colour){
        List<String> newCard = extractCardfromColour(colour);
        switch(position){
            case 4:
                replaceGoldDeck(newCard);
                break;
            case 5:
                replaceResourceDeck(newCard);
                break;
            default:
                break;
        }
    }

    /**
     * Method to replace the first public gold card
     * @param card the representation to insert
     */
    private void replaceFirstGold(List<String> card){
        for(int i = 0; i < HEIGHT_CARD; i++) {
            String newString = card.get(i) + bufferTable.get(i).substring(LENGTH_CARD + COD_COLOUR);

            bufferTable.set(i, newString);
        }
    }

    /**
     * Method to replace the second public gold card
     * @param card the representation to insert
     */
    private void replaceSecondGold(List<String> card){
        for(int i = 0; i < HEIGHT_CARD; i++) {
            String newString = bufferTable.get(i).substring(0, (LENGTH_CARD + COD_COLOUR + SPACE_BETWEEN_CARDS)) + card.get(i) +
                    bufferTable.get(i).substring((LENGTH_CARD+COD_COLOUR)*2 + SPACE_BETWEEN_CARDS);

            bufferTable.set(i, newString);
        }
    }

    /**
     * Method to replace the first public gold card
     * @param card the representation to insert
     */
    private void replaceFirstResource(List<String> card){
        for(int i = HEIGHT_CARD + SPACE_BETWEEN_LINES; i < bufferTable.size(); i++) {
            String newString = card.get(i - (HEIGHT_CARD + SPACE_BETWEEN_LINES)) + bufferTable.get(i).substring(LENGTH_CARD + COD_COLOUR);

            bufferTable.set(i, newString);
        }
    }

    /**
     * Method to replace the second public gold card
     * @param card the representation to insert
     */
    private void replaceSecondResource(List<String> card) {
        for (int i = HEIGHT_CARD + SPACE_BETWEEN_LINES; i < bufferTable.size(); i++) {
            String newString = bufferTable.get(i).substring(0, LENGTH_CARD + COD_COLOUR + SPACE_BETWEEN_CARDS) + card.get(i - (HEIGHT_CARD + SPACE_BETWEEN_LINES)) +
                    bufferTable.get(i).substring((LENGTH_CARD + COD_COLOUR) * 2 + SPACE_BETWEEN_CARDS);

            bufferTable.set(i, newString);
        }
    }

    /**
     * Method to replace the top of the gold deck
     * @param card the representation to insert
     */
    private void replaceGoldDeck(List<String> card){
        for(int i = 0; i < HEIGHT_CARD; i++) {
            String newString = bufferTable.get(i).substring(0,(LENGTH_CARD+ COD_COLOUR+SPACE_BETWEEN_CARDS)*2) + card.get(i);

            bufferTable.set(i, newString);
        }
    }

    /**
     * Method to replace the top of the resource deck
     * @param card the representation to insert
     */
    private void replaceResourceDeck(List<String> card){
        for(int i = HEIGHT_CARD + SPACE_BETWEEN_LINES; i < bufferTable.size(); i++) {
            String newString = bufferTable.get(i).substring(0,(LENGTH_CARD+ COD_COLOUR+SPACE_BETWEEN_CARDS)*2) + card.get(i - (HEIGHT_CARD + SPACE_BETWEEN_LINES));

            bufferTable.set(i, newString);
        }
    }
}
