package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.MemoryUpdater;

import java.util.List;
import java.util.Map;

public class LobbiesNonCompletedUpdate implements Update{
    Map<String, Integer> lobbies;

    public LobbiesNonCompletedUpdate(Map<String, Integer> lobbies){
        this.lobbies = lobbies;
    }

    public String toString(String receiver) {
        return null;
    }


    public String getTurn() {
        return null;
    }


    public void executeUpdate(MemoryUpdater memoryUpdater) {

    }
}
