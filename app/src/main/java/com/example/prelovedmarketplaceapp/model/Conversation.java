package com.example.prelovedmarketplaceapp.model;

public class Conversation {

    private String id;
    private String otherUserName;
    private String lastMessage;
    private long lastUpdatedTime;

    public Conversation(String id, String otherUserName, String lastMessage, long lastUpdatedTime) {
        this.id = id;
        this.otherUserName = otherUserName;
        this.lastMessage = lastMessage;
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getId() {
        return id;
    }

    public String getOtherUserName() {
        return otherUserName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public long getLastUpdatedTime() {
        return lastUpdatedTime;
    }
}
