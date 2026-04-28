package com.example.startup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.startup.databinding.ItemMessageBinding;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private final List<Message> messages;

    public MessageAdapter(List<Message> messages) { this.messages = messages; }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageViewHolder(ItemMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) { holder.bind(messages.get(position)); }

    @Override
    public int getItemCount() { return messages.size(); }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        private final ItemMessageBinding binding;
        MessageViewHolder(ItemMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(Message message) {
            binding.txtMessage.setText(message.getText());
            binding.cardMessage.setCardBackgroundColor(binding.getRoot().getResources().getColor(
                    message.isMine() ? R.color.purple_500 : R.color.gray_100
            ));
            binding.txtMessage.setTextColor(binding.getRoot().getResources().getColor(
                    message.isMine() ? R.color.white : R.color.black
            ));
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.cardMessage.getLayoutParams();
            if (message.isMine()) {
                params.setMargins(100, 8, 0, 8);
            } else {
                params.setMargins(0, 8, 100, 8);
            }
            binding.cardMessage.setLayoutParams(params);
        }
    }
}
