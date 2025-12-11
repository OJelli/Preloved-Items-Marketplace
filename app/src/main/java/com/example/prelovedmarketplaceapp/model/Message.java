package com.example.prelovedmarketplaceapp.model;

public class Message {

    private String id;
    private String conversationId;
    private String senderId;
    private String text;
    private long timestamp;
    private boolean isMine;

    public Message(String id, String conversationId, String senderId, String text, long timestamp, boolean isMine) {
        this.id = id;
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.text = text;
        this.timestamp = timestamp;
        this.isMine = isMine;
    }

    public String getId() {
        return id;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getText() {
        return text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isMine() {
        return isMine;
    }
}
