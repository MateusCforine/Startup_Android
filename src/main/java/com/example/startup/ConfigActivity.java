package com.example.startup;

import android.os.Bundle;
import android.widget.Toast;
import com.example.startup.databinding.ActivityConfigBinding;

public class ConfigActivity extends BaseMenuActivity {
    private ActivityConfigBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfigBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSalvarConfig.setOnClickListener(v -> Toast.makeText(this, "Configurações salvas.", Toast.LENGTH_SHORT).show());
        setupBottomMenu(binding.menuFeed, binding.menuSearch, binding.menuChat, binding.menuNotif, binding.menuProfile);
    }
}
