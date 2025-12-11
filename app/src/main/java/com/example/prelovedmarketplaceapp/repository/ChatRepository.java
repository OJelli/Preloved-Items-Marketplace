package com.example.prelovedmarketplaceapp.repository;

import com.example.prelovedmarketplaceapp.model.Conversation;
import com.example.prelovedmarketplaceapp.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatRepository {

    // Dummy conversations
    public static List<Conversation> getConversations() {
        List<Conversation> list = new ArrayList<>();

        list.add(new Conversation(
                "conv1",
                "Ahmed",
                "Sure, see you at the library!",
                System.currentTimeMillis() - 1000 * 60 * 5
        ));

        list.add(new Conversation(
                "conv2",
                "Nigel",
                "I can bring the extra chair.",
                System.currentTimeMillis() - 1000 * 60 * 60
        ));

        list.add(new Conversation(
                "conv3",
                "Juny",
                "Is the textbook still available?",
                System.currentTimeMillis() - 1000 * 60 * 60 * 3
        ));

        return list;
    }

    // Dummy messages for a conversation
    public static List<Message> getMessagesForConversation(String conversationId) {
        List<Message> messages = new ArrayList<>();

        if ("conv1".equals(conversationId)) {
            messages.add(new Message("m1", "conv1", "me",
                    "Hey, can we meet at 3pm?", System.currentTimeMillis() - 1000 * 60 * 20, true));
            messages.add(new Message("m2", "conv1", "Ahmed",
                    "Yeah, 3pm works!", System.currentTimeMillis() - 1000 * 60 * 15, false));
            messages.add(new Message("m3", "conv1", "me",
                    "Cool, library entrance?", System.currentTimeMillis() - 1000 * 60 * 10, true));
            messages.add(new Message("m4", "conv1", "Ahmed",
                    "Sure, see you at the library!", System.currentTimeMillis() - 1000 * 60 * 5, false));
        } else if ("conv2".equals(conversationId)) {
            messages.add(new Message("m5", "conv2", "Nigel",
                    "I have a spare table and chair.", System.currentTimeMillis() - 1000 * 60 * 60 * 4, false));
            messages.add(new Message("m6", "conv2", "me",
                    "Nice, how much?", System.currentTimeMillis() - 1000 * 60 * 60 * 3, true));
            messages.add(new Message("m7", "conv2", "Nigel",
                    "You can just take them lol.", System.currentTimeMillis() - 1000 * 60 * 60 * 2, false));
            messages.add(new Message("m8", "conv2", "me",
                    "Thanks bro, appreciate it", System.currentTimeMillis() - 1000 * 60 * 60, true));
            messages.add(new Message("m9", "conv2", "Nigel",
                    "I can bring the extra chair.", System.currentTimeMillis() - 1000 * 60 * 55, false));
        } else if ("conv3".equals(conversationId)) {
            messages.add(new Message("m10", "conv3", "Juny",
                    "Is the textbook still available?", System.currentTimeMillis() - 1000 * 60 * 60 * 5, false));
            messages.add(new Message("m11", "conv3", "me",
                    "Yes, it is.", System.currentTimeMillis() - 1000 * 60 * 60 * 4, true));
            messages.add(new Message("m12", "conv3", "Juny",
                    "Can we meet at the cafeteria?", System.currentTimeMillis() - 1000 * 60 * 60 * 3, false));
        }

        return messages;
    }
}
