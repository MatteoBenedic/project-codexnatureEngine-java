package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.CLI.CliCard;

import java.util.ArrayList;
import java.util.List;

public class CLIDrawBufferHand implements CLIDrawBuffer{
    public List<String> cardsinhand = new ArrayList<>();
    private static final int SPACE_BETWEEN_CARDS = 4;
    private static final int NUM_CARDS = 3;

    private static final int FIRSTCARD = 0;
    private static final int SECONDCARD = 1;
    private static final int THIRDCARD = 2;

    private List<CliCard> repCards;

    public CLIDrawBufferHand(List<CliCard> repCards) {
        this.repCards = repCards;
    }

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

    @Override
    public void printBuffer() {
        for(String s : cardsinhand)
            System.out.println(s);
    }

    private void setBuffer(List<List<String>> cards){
        cardsinhand.clear();
        for(int i = 0; i < HEIGHT_CARD; i++){
            String newString = cards.get(FIRSTCARD).get(i) + SPACE.repeat(SPACE_BETWEEN_CARDS) + cards.get(SECONDCARD).get(i) +
                    SPACE.repeat(SPACE_BETWEEN_CARDS) + cards.get(THIRDCARD).get(i);

            cardsinhand.add(newString);
        }

    }
}
