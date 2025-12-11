package com.example.prelovedmarketplaceapp.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prelovedmarketplaceapp.R;
import com.example.prelovedmarketplaceapp.model.Conversation;
import com.example.prelovedmarketplaceapp.model.Message;
import com.example.prelovedmarketplaceapp.repository.ChatRepository;

import java.util.ArrayList;
import java.util.List;

public class ConversationActivity extends AppCompatActivity {

    // Helper so other screens can open this easily
    public static void start(Context context, Conversation conversation) {
        Intent intent = new Intent(context, ConversationActivity.class);
        intent.putExtra("conversation_id", conversation.getId());
        intent.putExtra("other_user_name", conversation.getOtherUserName());
        context.startActivity(intent);
    }

    private TextView tvConversationTitle;
    private TextView tvAvatar;
    private RecyclerView rvMessages;
    private EditText etMessage;
    private Button btnSend;
    private ImageView btnBack;
    private ImageButton btnAttach;

    private MessageAdapter messageAdapter;
    private List<Message> messageList = new ArrayList<>();
    private String conversationId;
    private String otherUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        tvConversationTitle = findViewById(R.id.tvConversationTitle);
        tvAvatar = findViewById(R.id.tvAvatar);
        rvMessages = findViewById(R.id.rvMessages);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);
        btnBack = findViewById(R.id.btnBack);
        btnAttach = findViewById(R.id.btnAttach);

        conversationId = getIntent().getStringExtra("conversation_id");
        otherUserName = getIntent().getStringExtra("other_user_name");

        if (otherUserName != null) {
            tvConversationTitle.setText(otherUserName);

            String trimmed = otherUserName.trim();
            if (!trimmed.isEmpty()) {
                tvAvatar.setText(String.valueOf(trimmed.charAt(0)).toUpperCase());
            }
        }

        if (conversationId != null) {
            messageList = new ArrayList<>(ChatRepository.getMessagesForConversation(conversationId));
        } else {
            messageList = new ArrayList<>();
        }

        messageAdapter = new MessageAdapter(messageList);

        // Use layout manager that starts from bottom, like real chats
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        rvMessages.setLayoutManager(layoutManager);
        rvMessages.setAdapter(messageAdapter);

        // Scroll to bottom if messages exist
        if (!messageList.isEmpty()) {
            rvMessages.scrollToPosition(messageList.size() - 1);
        }

        btnSend.setOnClickListener(v -> sendMessage());
        btnBack.setOnClickListener(v -> finish());

        btnAttach.setOnClickListener(v ->
                Toast.makeText(this, "Attachment feature not implemented (UI only)", Toast.LENGTH_SHORT).show()
        );
    }

    private void sendMessage() {
        String text = etMessage.getText().toString().trim();
        if (TextUtils.isEmpty(text)) return;

        long now = System.currentTimeMillis();
        Message newMessage = new Message(
                "local_" + now,
                conversationId != null ? conversationId : "local_conv",
                "me",
                text,
                now,
                true
        );

        messageAdapter.addMessage(newMessage);
        rvMessages.scrollToPosition(messageAdapter.getItemCount() - 1);
        etMessage.setText("");
    }
}
