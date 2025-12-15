package com.example.prelovedmarketplaceapp.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager; // Added this import for clarity

import com.example.prelovedmarketplaceapp.R;
import com.example.prelovedmarketplaceapp.model.Chat;
import com.example.prelovedmarketplaceapp.model.Message;
import com.example.prelovedmarketplaceapp.model.User;
import com.example.prelovedmarketplaceapp.repository.ChatRepository;
import com.example.prelovedmarketplaceapp.utils.UserSessionManager;

import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

    public static final String EXTRA_CHAT_ID = "extra_chat_id";

    private RecyclerView rvMessages;
    private EditText etMessageInput;
    private MessageAdapter messageAdapter;
    private Chat currentChat;
    private User currentUser;

    // Helper to start this activity
    public static void start(Context context, String chatId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CHAT_ID, chatId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String chatId = getIntent().getStringExtra(EXTRA_CHAT_ID);
        currentUser = UserSessionManager.getInstance(this).getUser();

        if (chatId == null || currentUser == null) {
            Toast.makeText(this, "Error: Chat or user not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        currentChat = ChatRepository.getChatById(chatId);
        if (currentChat == null) {
            Toast.makeText(this, "Error: Chat not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupToolbar();
        setupMessageRecyclerView();
        setupInputListener();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String chatPartner = currentChat.getChatPartner(currentUser.getName());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(chatPartner);
            // Handle toolbar back navigation
            toolbar.setNavigationOnClickListener(v -> finish());
        }
    }

    private void setupMessageRecyclerView() {
        rvMessages = findViewById(R.id.rv_messages);

        messageAdapter = new MessageAdapter(currentChat.getMessages(), currentUser);
        rvMessages.setLayoutManager(new LinearLayoutManager(this)); // Ensure LayoutManager is set
        rvMessages.setAdapter(messageAdapter);

        if (messageAdapter.getItemCount() > 0) {
            rvMessages.scrollToPosition(messageAdapter.getItemCount() - 1);
        }
    }

    private void setupInputListener() {
        etMessageInput = findViewById(R.id.et_message_input);
        ImageButton btnSend = findViewById(R.id.btn_send);

        btnSend.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String text = etMessageInput.getText().toString().trim();

        if (TextUtils.isEmpty(text)) {
            Toast.makeText(this, "Message cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        Message newMessage = new Message(
                UUID.randomUUID().toString(),
                currentChat.getId(),
                currentUser.getName(), // Sender ID
                text,
                System.currentTimeMillis(),
                true // **FIXED**: Added missing boolean argument
        );

        // 1. Add to the repository
        ChatRepository.addMessageToChat(currentChat.getId(), newMessage);

        // 2. Update the RecyclerView
        messageAdapter.addMessage(newMessage);
        rvMessages.scrollToPosition(messageAdapter.getItemCount() - 1);

        // 3. Clear the input field
        etMessageInput.setText("");
    }
}