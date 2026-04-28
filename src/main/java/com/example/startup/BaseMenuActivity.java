package com.example.startup;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseMenuActivity extends AppCompatActivity {

    protected void setupBottomMenu(ImageButton btnFeed, ImageButton btnSearch, ImageButton btnChat, ImageButton btnNotif, ImageButton btnProfile) {
        if (btnFeed != null) btnFeed.setOnClickListener(v -> open(FeedActivity.class));
        if (btnSearch != null) btnSearch.setOnClickListener(v -> open(ProcurarActivity.class));
        if (btnChat != null) btnChat.setOnClickListener(v -> open(ConversasActivity.class));
        if (btnNotif != null) btnNotif.setOnClickListener(v -> open(NotificacoesActivity.class));
        if (btnProfile != null) btnProfile.setOnClickListener(v -> open(ContaActivity.class));
    }

    private void open(Class<?> cls) {
        if (!getClass().equals(cls)) startActivity(new Intent(this, cls));
    }
}
