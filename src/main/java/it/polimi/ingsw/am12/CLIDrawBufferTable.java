package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.CLI.CliCard;
import it.polimi.ingsw.am12.Model.CardDesign.GameCard.CardColour;

import java.util.ArrayList;
import java.util.List;

public class CLIDrawBufferTable implements CLIDrawBuffer{
    List<String> bufferTable = new ArrayList<>();
    private static final int SPACE_BETWEEN_CARDS = 4;
    private static final int SPACE_BETWEEN_LINES = 2;
    private static final int NUM_CARDS_INLINE = 3;
    private List<CliCard> repCards;

    public CLIDrawBufferTable(List<CliCard> repcards) {
        this.repCards = repcards;

        String space = SPACE.repeat(NUM_CARDS_INLINE*LENGTH_CARD + SPACE_BETWEEN_CARDS*(NUM_CARDS_INLINE-1));
        for(int i = 0; i < (HEIGHT_CARD*2 + SPACE_BETWEEN_LINES); i++)
            bufferTable.add(space);
    }

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
    @Override
    public void printBuffer() {
        for(String s : bufferTable)
            System.out.println(s);
    }

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

    private void replaceFirstGold(List<String> card){
        for(int i = 0; i < HEIGHT_CARD; i++) {
            String newString = card.get(i) + bufferTable.get(i).substring(LENGTH_CARD);

            bufferTable.set(i, newString);
        }
    }


    private void replaceSecondGold(List<String> card){
        for(int i = 0; i < HEIGHT_CARD; i++) {
            String newString = bufferTable.get(i).substring(0, (LENGTH_CARD + SPACE_BETWEEN_CARDS)) + card.get(i) +
                    bufferTable.get(i).substring(LENGTH_CARD*2 + SPACE_BETWEEN_CARDS);

            bufferTable.set(i, newString);
        }
    }


    private void replaceFirstResource(List<String> card){
        for(int i = HEIGHT_CARD + SPACE_BETWEEN_LINES; i < bufferTable.size(); i++) {
            String newString = card.get(i - (HEIGHT_CARD + SPACE_BETWEEN_LINES)) + bufferTable.get(i).substring(LENGTH_CARD);

            bufferTable.set(i, newString);
        }
    }

    private void replaceSecondResource(List<String> card) {
        for (int i = HEIGHT_CARD + SPACE_BETWEEN_LINES; i < bufferTable.size(); i++) {
            String newString = bufferTable.get(i).substring(0, LENGTH_CARD + SPACE_BETWEEN_CARDS) + card.get(i - (HEIGHT_CARD + SPACE_BETWEEN_LINES)) +
                    bufferTable.get(i).substring(LENGTH_CARD * 2 + SPACE_BETWEEN_CARDS);

            bufferTable.set(i, newString);
        }
    }


    private void replaceGoldDeck(List<String> card){
        for(int i = 0; i < HEIGHT_CARD; i++) {
            String newString = bufferTable.get(i).substring(0,(LENGTH_CARD+SPACE_BETWEEN_CARDS)*2) + card.get(i);

            bufferTable.set(i, newString);
        }
    }


    private void replaceResourceDeck(List<String> card){
        for(int i = HEIGHT_CARD + SPACE_BETWEEN_LINES; i < bufferTable.size(); i++) {
            String newString = bufferTable.get(i).substring(0,(LENGTH_CARD+SPACE_BETWEEN_CARDS)*2) + card.get(i - (HEIGHT_CARD + SPACE_BETWEEN_LINES));

            bufferTable.set(i, newString);
        }
    }
}
