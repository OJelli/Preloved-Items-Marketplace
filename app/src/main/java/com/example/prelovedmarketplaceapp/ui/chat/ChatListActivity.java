package com.example.prelovedmarketplaceapp.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prelovedmarketplaceapp.R;
import com.example.prelovedmarketplaceapp.model.Chat;
import com.example.prelovedmarketplaceapp.model.User;
import com.example.prelovedmarketplaceapp.repository.ChatRepository;
import com.example.prelovedmarketplaceapp.utils.UserSessionManager;

import java.util.List;

// ChatListActivity now implements the new ChatAdapter.OnChatClickListener interface
public class ChatListActivity extends AppCompatActivity implements ChatAdapter.OnChatClickListener {

    private User currentUser;

    // Helper to open Chat List from other screens (like HomeActivity)
    public static void start(Context context) {
        Intent intent = new Intent(context, ChatListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list); // Assuming you have activity_chat_list.xml

        // Get the current user
        currentUser = UserSessionManager.getInstance(this).getUser();
        if (currentUser == null) {
            Toast.makeText(this, "Please log in to view chats.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupToolbar();
        setupChatRecyclerView();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Messages");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }
    }

    private void setupChatRecyclerView() {
        RecyclerView rvConversations = findViewById(R.id.rv_conversations); // Correct ID for RecyclerView

        // Get chats relevant to the current user
        List<Chat> chatList = ChatRepository.getChatsForUser(currentUser.getName());

        // Use the correct adapter and pass the current user for displaying partner names
        ChatAdapter adapter = new ChatAdapter(chatList, currentUser, this);
        rvConversations.setLayoutManager(new LinearLayoutManager(this));
        rvConversations.setAdapter(adapter);
    }


    // This method launches the detailed chat screen (ChatActivity)
    @Override
    public void onChatClick(Chat chat) {
        // Start the individual chat activity, passing the chat ID
        ChatActivity.start(this, chat.getId());
    }
}