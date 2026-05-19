package com.example.startup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseMenuActivity extends AppCompatActivity {

    private boolean wrappingContent = false;

    @Override
    public void setContentView(View view) {
        if (wrappingContent) {
            super.setContentView(view);
            return;
        }

        wrappingContent = true;

        FrameLayout root = new FrameLayout(this);
        root.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        root.addView(view, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        ImageButton btnAssistente = criarBotaoAssistente();
        root.addView(btnAssistente);

        super.setContentView(root);

        wrappingContent = false;
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        setContentView(view);
    }

    private ImageButton criarBotaoAssistente() {
        ImageButton botao = new ImageButton(this);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                dp(56),
                dp(56)
        );
        params.gravity = Gravity.BOTTOM | Gravity.END;
        params.setMargins(0, 0, dp(18), dp(86));
        botao.setLayoutParams(params);

        botao.setImageResource(R.drawable.ic_assistente);
        botao.setBackgroundResource(R.drawable.bg_assistente_button);
        botao.setPadding(dp(12), dp(12), dp(12), dp(12));
        botao.setScaleType(ImageButton.ScaleType.CENTER_INSIDE);
        // botao.setColorFilter(Color.BLACK);
        botao.setContentDescription("Assistente Gust");
        botao.setElevation(dp(8));

        botao.setOnClickListener(v ->
                startActivity(new Intent(this, ChatBotActivity.class))
        );

        return botao;
    }

    private int dp(int valor) {
        return (int) (valor * getResources().getDisplayMetrics().density);
    }

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
