package com.example.startup;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.startup.databinding.ItemConversationBinding;
import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {
    private final List<Conversation> items;

    public ConversationAdapter(List<Conversation> items) { this.items = items; }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversationViewHolder(ItemConversationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class ConversationViewHolder extends RecyclerView.ViewHolder {
        private final ItemConversationBinding binding;
        ConversationViewHolder(ItemConversationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(Conversation item) {
            binding.txtName.setText(item.getName());
            binding.txtSummary.setText(item.getSummary());
            binding.txtTime.setText(item.getTime());
            binding.txtStatus.setText(item.isUnread() ? "Nova" : "Lida");
            binding.getRoot().setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                intent.putExtra("contact_name", item.getName());
                v.getContext().startActivity(intent);
            });
        }
    }
}
