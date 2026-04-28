package com.example.startup;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.startup.databinding.ActivityConversasBinding;
import java.util.ArrayList;
import java.util.List;

public class ConversasActivity extends BaseMenuActivity {
    private ActivityConversasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConversasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerConversas.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerConversas.setAdapter(new ConversationAdapter(mockConversations()));

        setupBottomMenu(binding.menuFeed, binding.menuSearch, binding.menuChat, binding.menuNotif, binding.menuProfile);
    }

    private List<Conversation> mockConversations() {
        List<Conversation> list = new ArrayList<>();

        list.add(new Conversation("Lucas Ferreira", "Bora testar aquela pizzaria nova?", "10:30", true));
        list.add(new Conversation("Mariana Costa", "Adorei a receita que você postou!", "09:15", true));
        list.add(new Conversation("João Silva", "Valeu demais!", "Ontem", false));
        list.add(new Conversation("Grupo dos Chefs", "Vamos marcar um encontro?", "2 dias", false));

        return list;
    }
}
