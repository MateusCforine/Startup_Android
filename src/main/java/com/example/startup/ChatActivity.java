package com.example.startup;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.startup.databinding.ActivityChatBinding;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseMenuActivity {
    private ActivityChatBinding binding;
    private final List<Message> messages = new ArrayList<>();
    private MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String contact = getIntent().getStringExtra("contact_name");
        binding.txtContactName.setText(contact == null ? "Noah" : contact);

        messages.add(new Message("Oi, td bemm?", false));
        messages.add(new Message("onde vc foi ontem???", false));
        messages.add(new Message("Oiii, eu fui no Outback", true));

        adapter = new MessageAdapter(messages);
        binding.recyclerMessages.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerMessages.setAdapter(adapter);

        binding.btnSend.setOnClickListener(v -> enviarMensagem());
        setupBottomMenu(binding.menuFeed, binding.menuSearch, binding.menuChat, binding.menuNotif, binding.menuProfile);
    }

    private void enviarMensagem() {
        String text = binding.edtMessage.getText().toString().trim();
        if (text.isEmpty()) return;
        messages.add(new Message(text, true));
        binding.edtMessage.setText("");
        adapter.notifyItemInserted(messages.size() - 1);
        binding.recyclerMessages.smoothScrollToPosition(messages.size() - 1);
    }
}
