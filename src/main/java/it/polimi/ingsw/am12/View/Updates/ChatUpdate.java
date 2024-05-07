package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.MemoryUpdater;

public class ChatUpdate implements Update{
    String sender;
    boolean publicMess;

    String chatMessage;

    public ChatUpdate(String sender, boolean publicMess, String chatMessage){
        this.sender = sender;
        this.chatMessage = chatMessage;
        this.publicMess = publicMess;
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

    public String getChatMessage() {
        return chatMessage;
    }

    public boolean isPublicMess() {
        return publicMess;
    }

    public String getSender() {
        return sender;
    }
}
