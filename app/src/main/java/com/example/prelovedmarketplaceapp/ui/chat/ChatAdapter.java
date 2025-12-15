package com.example.prelovedmarketplaceapp.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prelovedmarketplaceapp.R;
import com.example.prelovedmarketplaceapp.model.Chat;
import com.example.prelovedmarketplaceapp.model.Message;
import com.example.prelovedmarketplaceapp.model.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    // 1. Click Listener Interface
    public interface OnChatClickListener {
        void onChatClick(Chat chat);
    }

    private final List<Chat> chatList;
    private final User currentUser;
    private final OnChatClickListener listener;

    public ChatAdapter(List<Chat> chatList, User currentUser, OnChatClickListener listener) {
        this.chatList = chatList;
        this.currentUser = currentUser;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_preview, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.bind(chat, currentUser, listener);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    // 2. ViewHolder Class
    static class ChatViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvPartnerName, tvLastMessage, tvTime;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_chat_avatar);
            tvPartnerName = itemView.findViewById(R.id.tv_chat_partner_name);
            tvLastMessage = itemView.findViewById(R.id.tv_last_message);
            tvTime = itemView.findViewById(R.id.tv_chat_time);
        }

        public void bind(Chat chat, User currentUser, OnChatClickListener listener) {

            // 2.1 Set Chat Partner Name
            String partnerName = chat.getChatPartner(currentUser.getName());
            String titlePreview = chat.getListingTitle() != null ? " (" + chat.getListingTitle() + ")" : "";
            tvPartnerName.setText(partnerName + titlePreview);

            // 2.2 Set Last Message
            Message lastMessage = chat.getLastMessage();
            if (lastMessage != null) {
                // Prepend "You: " if the message was sent by the current user
                String prefix = lastMessage.getSenderId().equals(currentUser.getName()) ? "You: " : "";
                tvLastMessage.setText(prefix + lastMessage.getText());
                tvTime.setText(formatTimeAgo(lastMessage.getTimestamp()));
            } else {
                tvLastMessage.setText("No messages yet.");
                tvTime.setText("");
            }

            // 2.3 Set Click Listener
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onChatClick(chat);
                }
            });

            // 2.4 Set Avatar (Placeholder for now)
            ivAvatar.setImageResource(android.R.drawable.ic_menu_compass);
        }

        // Helper function for displaying time (simplistic, can be improved)
        private String formatTimeAgo(long timestamp) {
            long now = System.currentTimeMillis();
            long diff = now - timestamp;

            if (diff < TimeUnit.MINUTES.toMillis(1)) {
                return "Just now";
            } else if (diff < TimeUnit.HOURS.toMillis(1)) {
                return TimeUnit.MILLISECONDS.toMinutes(diff) + "m ago";
            } else if (diff < TimeUnit.DAYS.toMillis(1)) {
                return TimeUnit.MILLISECONDS.toHours(diff) + "h ago";
            } else {
                return TimeUnit.MILLISECONDS.toDays(diff) + "d ago";
            }
        }
    }
}