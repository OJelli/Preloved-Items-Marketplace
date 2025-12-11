package com.example.prelovedmarketplaceapp.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prelovedmarketplaceapp.R;
import com.example.prelovedmarketplaceapp.model.Conversation;
import com.example.prelovedmarketplaceapp.repository.ChatRepository;

import java.util.List;

public class ChatListActivity extends AppCompatActivity {

    private RecyclerView rvConversations;

    // 🔹 Helper so anyone can open this screen easily
    public static void start(Context context) {
        Intent intent = new Intent(context, ChatListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        rvConversations = findViewById(R.id.rvConversations);

        List<Conversation> conversations = ChatRepository.getConversations();

        ConversationAdapter adapter = new ConversationAdapter(conversations, conversation -> {
            // ✅ Use the helper method in ConversationActivity now
            ConversationActivity.start(this, conversation);
        });

        rvConversations.setLayoutManager(new LinearLayoutManager(this));
        rvConversations.setAdapter(adapter);
    }
}
