package com.example.startup;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.startup.databinding.ActivityNotificacoesBinding;
import java.util.ArrayList;
import java.util.List;

public class NotificacoesActivity extends BaseMenuActivity {
    private ActivityNotificacoesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificacoesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerNotifications.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerNotifications.setAdapter(new NotificationAdapter(mockItems()));

        setupBottomMenu(binding.menuFeed, binding.menuSearch, binding.menuChat, binding.menuNotif, binding.menuProfile);
    }

    private List<NotificationItem> mockItems() {
        List<NotificationItem> list = new ArrayList<>();

        list.add(new NotificationItem("Mariana Costa curtiu sua publicação.", "2 min atrás"));
        list.add(new NotificationItem("João Paulo comentou na sua foto.", "15 min atrás"));
        list.add(new NotificationItem("Ana Clara mencionou você em um post.", "1 h atrás"));
        list.add(new NotificationItem("Beatriz Lima começou a seguir você.", "5 h atrás"));

        return list;
    }
}
