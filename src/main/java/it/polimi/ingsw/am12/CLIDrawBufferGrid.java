package it.polimi.ingsw.am12;

import it.polimi.ingsw.am12.CLI.CliCard;
import it.polimi.ingsw.am12.Utils.Coordinate;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public class CLIDrawBufferGrid implements CLIDrawBuffer{
    List<String> buffer;
    Coordinate topleftPosition = null;
    List<CliCard> repCards;


    public CLIDrawBufferGrid(List<CliCard> repCards) {
        this.repCards = repCards;
        this.buffer = new ArrayList<>();
        for(int i = 0; i < HEIGHT_CARD; i++){
            String newString = SPACE.repeat(LENGTH_CARD);

            buffer.add(newString);
        }
    }

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
                int newLength = (position.getX() - topleftPosition.getX()) * (HEIGHT_CARD - COVERED_HEIGHT) + COVERED_HEIGHT;
                if (newLength >= buffer.size()) {
                    String space = SPACE.repeat(buffer.getLast().length());
                    for (int i = buffer.size(); i < newLength; i++)
                        buffer.add(space);
                }
                if (position.getY() < position.getY()) {
                    topleftPosition.setY(position.getY());
                    shiftBufferRight();
                }
            }
        }
        drawNewCard(index, side, position);
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
        for(String s : buffer)
            System.out.println(s);
    }

    private void shiftBufferRight(){
        List<String> newBuffer = new ArrayList<>();
        for(int i = 0; i < buffer.size(); i++) {
            String space = SPACE.repeat(LENGTH_CARD - COVERED_LENGTH);
            newBuffer.add(space + buffer.get(i));
        }

        buffer = newBuffer;
    }

    private void shiftBufferDown(){
        int length = max(buffer.getFirst().length(), LENGTH_CARD);
        String space = SPACE.repeat(length);
        for(int i = 0; i < (HEIGHT_CARD - COVERED_HEIGHT); i++)
            buffer.addFirst(space);
    }

    private void drawNewCard(int index, boolean side, Coordinate position){
        List<String> newCard = extractCardfromIndex(index, side);
        int bufferY = (position.getY() - topleftPosition.getY())*(LENGTH_CARD - COVERED_LENGTH);
        int bufferX = (position.getX() - topleftPosition.getX())*(HEIGHT_CARD - COVERED_HEIGHT);
        int delta = 0;
        for(int i = bufferX; i < (bufferX + HEIGHT_CARD); i++){
            String oldString = buffer.get(i);
            int countTr = 0;
            int countSl = 0;
            int countTv = 0;
            for(int j = 0; j < bufferY + COVERED_LENGTH + COD_COLOUR; j++) {
                char a = oldString.charAt(j);
                if (a == '|')
                    countTv++;
                else if (a == '/')
                    countSl++;
                else if (a == '_')
                    countTr++;
            }
            delta = max(max(countTv/2, countSl), countTr/13);
            String s2, s3;

            try{
                s2 =buffer.get(i).substring(bufferY + (LENGTH_CARD - COVERED_LENGTH), bufferY + (LENGTH_CARD - COVERED_LENGTH) + COD_COLOUR);
            }catch (StringIndexOutOfBoundsException e){
                s2 = "";
            }
            try{
                s3 = buffer.get(i).substring(bufferY + LENGTH_CARD + COD_COLOUR);
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
