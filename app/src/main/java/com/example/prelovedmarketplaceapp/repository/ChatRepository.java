package com.example.prelovedmarketplaceapp.repository;

import com.example.prelovedmarketplaceapp.model.Chat;
import com.example.prelovedmarketplaceapp.model.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ChatRepository {

    // Storage for all chats, keyed by chat ID
    private static final Map<String, Chat> CHATS_MAP = new HashMap<>();

    // A secondary map to quickly find a chat using the participants' IDs (key: P1ID_P2ID)
    private static final Map<String, String> PARTICIPANT_KEY_TO_CHAT_ID = new HashMap<>();

    // Static block to initialize dummy data when the class is loaded
    static {
        loadDummyData();
    }

    // --- Dummy Data Initialization ---
    private static void loadDummyData() {
        // NOTE: Ensure these user names match your dummy users ("Aiman" and "Siti")
        // or the users you log in with. We'll use "Aiman" and "Siti" here.

        // --- Conversation 1: User Aiman vs User Siti (Listing: Vintage Camera) ---
        String sellerAiman = "Aiman";
        String buyerSiti = "Siti";
        String listingTitle1 = "Vintage Kodak Camera";

        Chat chat1 = getOrCreateChat(sellerAiman, buyerSiti, listingTitle1);

        // Ensure no automated sample messages are added here if getOrCreateChat is called.
        // If the map is empty, getOrCreateChat adds them and returns the chat object.
        // We will manually overwrite/add messages for history.

        // Manually adding historical messages
        chat1.addMessage(new Message(
                UUID.randomUUID().toString(), chat1.getId(),
                buyerSiti, "Hi Aiman, is the camera lens clear?",
                System.currentTimeMillis() - 86400000 * 2, true)); // 2 days ago

        chat1.addMessage(new Message(
                UUID.randomUUID().toString(), chat1.getId(),
                sellerAiman, "Yes, completely clear, Siti. It's been well-kept.",
                System.currentTimeMillis() - 86400000 * 2 + 3600000, true)); // 1 hour later

        // --- Conversation 2: User Aiman vs User Fadhil (Listing: Gaming PC) ---
        String userFadhil = "Fadhil";
        String listingTitle2 = "High-End Gaming PC";

        // We use getOrCreateChat with the actual seller ("Aiman") and another user ("Fadhil")
        Chat chat2 = getOrCreateChat(sellerAiman, userFadhil, listingTitle2);

        // Manually adding historical messages
        chat2.addMessage(new Message(
                UUID.randomUUID().toString(), chat2.getId(),
                userFadhil, "Hey Aiman, I see you listed a PC. Can you drop the price to 3000?",
                System.currentTimeMillis() - 86400000, true)); // 1 day ago

        chat2.addMessage(new Message(
                UUID.randomUUID().toString(), chat2.getId(),
                sellerAiman, "Sorry, Fadhil, 3500 is the final price as it includes delivery.",
                System.currentTimeMillis() - 86400000 + 3600000, true)); // 1 day ago

    }
    // ---------------------------------------------

    // Helper method to create a standardized key regardless of participant order
    private static String generateParticipantKey(String id1, String id2) {
        // Ensures the key is consistent (alphabetical order)
        if (id1.compareTo(id2) < 0) {
            return id1 + "_" + id2;
        } else {
            return id2 + "_" + id1;
        }
    }

    // --- Core Chat Management Methods ---

    // 1. Retrieve a Chat by its ID
    public static Chat getChatById(String chatId) {
        return CHATS_MAP.get(chatId);
    }

    // 2. Get or Create a new chat (Keep the sample message creation for NEW chats)
    public static Chat getOrCreateChat(String id1, String id2, String listingTitle) {
        String key = generateParticipantKey(id1, id2);

        // 1. Check if chat already exists
        if (PARTICIPANT_KEY_TO_CHAT_ID.containsKey(key)) {
            String existingChatId = PARTICIPANT_KEY_TO_CHAT_ID.get(key);
            return CHATS_MAP.get(existingChatId);
        }

        // 2. If not, create a new chat
        String newChatId = UUID.randomUUID().toString();

        String sellerName = id1;
        String buyerName = id2;

        Chat newChat = new Chat(newChatId, UUID.randomUUID().toString(), listingTitle, sellerName, buyerName);

        // ADD SAMPLE MESSAGES TO THE NEW CHAT (This runs only for new chats created during runtime)

        // Message from the Buyer (id2) initiating the conversation
        Message welcomeMessage = new Message(
                UUID.randomUUID().toString(),
                newChatId,
                buyerName, // Sender is the buyer
                "Hi " + sellerName + ", I'm interested in your listing: " + listingTitle + ". Is this still available?",
                System.currentTimeMillis(),
                true
        );
        newChat.addMessage(welcomeMessage);

        // ---------------------------------------------

        // 3. Store the new chat
        CHATS_MAP.put(newChatId, newChat);
        PARTICIPANT_KEY_TO_CHAT_ID.put(key, newChatId);

        return newChat;
    }

    // 3. Get all chats relevant to a specific user
    public static List<Chat> getChatsForUser(String userId) {
        // Filter the chats to only include those where the userId is a participant
        return CHATS_MAP.values().stream()
                .filter(chat -> chat.getChatPartner(userId) != null && !chat.getChatPartner(userId).equals("Unknown User"))
                .collect(Collectors.toList());
    }

    // 4. Add a new message to an existing chat
    public static void addMessageToChat(String chatId, Message message) {
        Chat chat = CHATS_MAP.get(chatId);
        if (chat != null) {
            chat.addMessage(message);
        }
    }

    // 5. Retrieve the list of messages for a given Chat ID (the modern method)
    public static List<Message> getMessagesForChat(String chatId) {
        Chat chat = CHATS_MAP.get(chatId);
        if (chat != null) {
            return chat.getMessages();
        }
        return new ArrayList<>();
    }

    // 6. Fix for obsolete ConversationActivity (TEMPORARY ALIAS)
    public static List<Message> getMessagesForConversation(String conversationId) {
        return getMessagesForChat(conversationId);
    }
}