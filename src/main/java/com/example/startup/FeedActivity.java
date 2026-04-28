package com.example.startup;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.startup.databinding.ActivityFeedBinding;
import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends BaseMenuActivity {
    private ActivityFeedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerPosts.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerPosts.setAdapter(new PostAdapter(mockPosts()));

        binding.btnCreatePost.setOnClickListener(v -> startActivity(new Intent(this, CriarActivity.class)));
        setupBottomMenu(binding.menuFeed, binding.menuSearch, binding.menuChat, binding.menuNotif, binding.menuProfile);
    }

    private List<Post> mockPosts() {
        List<Post> list = new ArrayList<>();
        list.add(new Post("Outback", "#promo", "2 h", 53200, 2));
        list.add(new Post("Coco Bambu", "#seafood", "3 h", 27400, 2));
        list.add(new Post("Masterchef", "#receita", "5 h", 12000, 5));
        return list;
    }
}
