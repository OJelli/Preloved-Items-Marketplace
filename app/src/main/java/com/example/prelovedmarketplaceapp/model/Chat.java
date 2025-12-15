package com.example.prelovedmarketplaceapp.model;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private String id;
    private String listingId;
    private String listingTitle;
    private String participant1Id; // e.g., Seller
    private String participant2Id; // e.g., Buyer
    private List<Message> messages;

    // Constructor
    public Chat(String id, String listingId, String listingTitle, String participant1Id, String participant2Id) {
        this.id = id;
        this.listingId = listingId;
        this.listingTitle = listingTitle;
        this.participant1Id = participant1Id;
        this.participant2Id = participant2Id;
        this.messages = new ArrayList<>(); // Initialize the message list
    }

    // --- Getters ---
    public String getId() {
        return id;
    }

    public String getListingTitle() {
        return listingTitle;
    }

    public List<Message> getMessages() {
        return messages;
    }

    // Returns the name of the other person in the chat
    public String getChatPartner(String currentUserId) {
        if (participant1Id.equals(currentUserId)) {
            return participant2Id;
        } else if (participant2Id.equals(currentUserId)) {
            return participant1Id;
        }
        return "Unknown User";
    }

    // Returns the last message for chat list previews
    public Message getLastMessage() {
        if (!messages.isEmpty()) {
            return messages.get(messages.size() - 1);
        }
        return null;
    }

    // --- Message Management ---
    public void addMessage(Message message) {
        this.messages.add(message);
    }
}