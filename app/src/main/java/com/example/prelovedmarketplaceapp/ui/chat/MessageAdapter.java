package com.example.prelovedmarketplaceapp.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prelovedmarketplaceapp.R;
import com.example.prelovedmarketplaceapp.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messages;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.bind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView tvMessageOther, tvMessageMine;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessageOther = itemView.findViewById(R.id.tvMessageOther);
            tvMessageMine = itemView.findViewById(R.id.tvMessageMine);
        }

        public void bind(Message message) {
            if (message.isMine()) {
                tvMessageMine.setVisibility(View.VISIBLE);
                tvMessageOther.setVisibility(View.GONE);
                tvMessageMine.setText(message.getText());
            } else {
                tvMessageOther.setVisibility(View.VISIBLE);
                tvMessageMine.setVisibility(View.GONE);
                tvMessageOther.setText(message.getText());
            }
        }
    }
}
