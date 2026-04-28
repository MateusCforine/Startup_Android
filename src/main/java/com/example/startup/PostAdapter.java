package com.example.startup;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.startup.databinding.ItemPostBinding;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private final List<Post> posts;

    public PostAdapter(List<Post> posts) { this.posts = posts; }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(posts.get(position));
    }

    @Override
    public int getItemCount() { return posts.size(); }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        private final ItemPostBinding binding;

        PostViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Post post) {
            binding.txtAuthor.setText(post.getAuthor());
            binding.txtTag.setText(post.getTag());
            binding.txtTime.setText(post.getTime());
            binding.txtLikes.setText(post.getLikes() + " curtidas");
            binding.txtComments.setText(post.getComments() + " comentários");
        }
    }
}
