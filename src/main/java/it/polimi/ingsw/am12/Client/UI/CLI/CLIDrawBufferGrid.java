package it.polimi.ingsw.am12.Client.UI.CLI;

import it.polimi.ingsw.am12.Utils.Coordinate;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

/**
 * Buffer for the CLI that draws and prints the playing grid of a player
 */
public class CLIDrawBufferGrid implements CLIDrawBuffer {
    List<String> buffer;
    Coordinate topleftPosition = null;
    List<CliCard> repCards;

    /**
     * Class constructor
     * @param repCards the list of all the cards in form of CliCards given by the CLI
     */
    public CLIDrawBufferGrid(List<CliCard> repCards) {
        this.repCards = repCards;
        this.buffer = new ArrayList<>();
        for(int i = 0; i < HEIGHT_CARD; i++){
            String newString = SPACE.repeat(LENGTH_CARD);

            buffer.add(newString);
        }
    }

    /**
     * It inserts a new card in the buffer and enlarges the buffer to be compatible with the new representation
     * @param index the index of the card to insert
     * @param side the side of the card to place
     * @param position where to place the card
     */
    public void insertCardInBuffer(int index, boolean side, Coordinate position) {
        if (topleftPosition == null) {
            topleftPosition = position;
        } else {
            if (position.getX() < topleftPosition.getX())
                if (position.getY() < topleftPosition.getY()) {
                    topleftPosition = position;
                    shiftBufferDown();
                    shiftBufferRight();
                } else {
                    topleftPosition.setX(position.getX());
                    shiftBufferDown();
                }
            else {
                if (position.getY() < topleftPosition.getY()) {
                    topleftPosition.setY(position.getY());
                    shiftBufferRight();
                }
            }
            int newLength = (position.getX() - topleftPosition.getX()) * (HEIGHT_CARD - COVERED_HEIGHT) + HEIGHT_CARD - COVERED_HEIGHT;
            int len = 0;
            if (newLength >= buffer.size()) {
                for (String s : buffer)
                    if (len < s.length())
                        len = s.length();
                String space = SPACE.repeat(len);
                for (int i = buffer.size(); i < (newLength + HEIGHT_CARD); i++)
                    buffer.add(space);
            }
            for(int i = 0; i < buffer.size(); i++) {
                String s = buffer.get(i);
                if (s.length() < len) {
                    int a = s.length();
                    String toAppend = s + SPACE.repeat(len - a);
                    buffer.set(i, toAppend);
                }
            }
        }
        drawNewCard(index, side, position);
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
        for(String s : buffer)
            System.out.println(s+ ColourCLI.RESET.getColour());
    }

    /**
     * Shifts all the buffer to the right to place a new card on the left
     */
    private void shiftBufferRight(){
        List<String> newBuffer = new ArrayList<>();
        for(int i = 0; i < buffer.size(); i++) {
            String space = SPACE.repeat(LENGTH_CARD - COVERED_LENGTH);
            newBuffer.add(space + buffer.get(i));
        }

        buffer = newBuffer;
    }

    /**
     * Shifts all the buffer down to place a new card above all the others
     */
    private void shiftBufferDown(){
        int length = max(buffer.getFirst().length(), LENGTH_CARD);
        String space = SPACE.repeat(length);
        for(int i = 0; i < (HEIGHT_CARD - COVERED_HEIGHT); i++)
            buffer.addFirst(space);
    }

    /**
     * Draws the card in the buffer
     * @param index the index of the card to draw
     * @param side the side of the card to place
     * @param position where to place the card
     */
    private void drawNewCard(int index, boolean side, Coordinate position){
        List<String> newCard = extractCardfromIndex(index, side);
        int bufferY = (position.getY() - topleftPosition.getY())*(LENGTH_CARD - COVERED_LENGTH);
        int bufferX = (position.getX() - topleftPosition.getX())*(HEIGHT_CARD - COVERED_HEIGHT);
        int delta = 0;
        for(int i = bufferX; i < (bufferX + HEIGHT_CARD); i++){
            String oldString = buffer.get(i);
            int count = 0;
            for(int j = 0; j < bufferY + COVERED_LENGTH + COD_COLOUR; j++) {
                try {
                    char a = oldString.charAt(j);
                    if (a == '[')
                        count++;
                }catch(StringIndexOutOfBoundsException ignored){
                }
            }
            delta = count;
            String s2, s3;

            try{
                s2 =buffer.get(i).substring(bufferY + delta*COD_COLOUR + (LENGTH_CARD - COVERED_LENGTH), bufferY + delta*COD_COLOUR + (LENGTH_CARD - COVERED_LENGTH) + COD_COLOUR);
            }catch (StringIndexOutOfBoundsException e){
                s2 = "";
            }
            try{
                s3 = buffer.get(i).substring(bufferY + delta*COD_COLOUR + LENGTH_CARD + COD_COLOUR);
            }catch(StringIndexOutOfBoundsException e){
                s3 = "";
            }


            String newString = buffer.get(i).substring(0, bufferY + delta*COD_COLOUR) +
                    newCard.get(i - bufferX) +
                    s2 + s3;

            buffer.set(i, newString);
        }
    }

}
