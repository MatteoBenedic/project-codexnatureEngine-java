package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.MemoryUpdater;

public class NicknameEstablishedUpdate implements Update{
    String nickname;

    public NicknameEstablishedUpdate(String nickname){
        this.nickname = nickname;
    }

    @Override
    public String toString(String receiver) {
        return null;
    }

    @Override
    public String getTurn() {
        return null;
    }

    @Override
    public void executeUpdate(MemoryUpdater memoryUpdater) {

    }
}
