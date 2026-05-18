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

        binding.btnCreatePost.setOnClickListener(v ->
                startActivity(new Intent(this, CriarActivity.class))
        );

        setupBottomMenu(
                binding.menuFeed,
                binding.menuSearch,
                binding.menuChat,
                binding.menuNotif,
                binding.menuProfile
        );
    }

    private List<Post> mockPosts() {
        List<Post> list = new ArrayList<>();

        list.add(new Post(
                "Cabral Pizzas",
                "Qualidade sem exploração 🍕",
                "2 h",
                128,
                18,
                R.drawable.cabral_pizzas
        ));

        list.add(new Post(
                "Pizzaria da Rita",
                "Sabor caseiro para compartilhar com os amigos",
                "4 h",
                96,
                11,
                R.drawable.pizzaria_da_rita
        ));

        list.add(new Post(
                "Gust",
                "Onde o sabor une pessoas",
                "6 h",
                74,
                7,
                R.drawable.gust_logo
        ));

        return list;
    }
}
