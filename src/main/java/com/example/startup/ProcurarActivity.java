package com.example.startup;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.example.startup.databinding.ActivityProcurarBinding;
import java.util.ArrayList;
import java.util.List;

public class ProcurarActivity extends BaseMenuActivity {
    private ActivityProcurarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProcurarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<String> sugestoes = new ArrayList<>();

        sugestoes.add("Ana Clara");
        sugestoes.add("Lucas Ferreira");
        sugestoes.add("Receita de pizza artesanal");
        sugestoes.add("Receita de brownie");
        sugestoes.add("#pizza");
        sugestoes.add("#sushi");
        sugestoes.add("#gastronomia");
        sugestoes.add("Cafés em Sorocaba");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                sugestoes
        );

        binding.listSugestoes.setAdapter(adapter);

        binding.btnPesquisar.setOnClickListener(v ->
                Toast.makeText(
                        this,
                        "Pesquisar: " + binding.edtPesquisa.getText().toString(),
                        Toast.LENGTH_SHORT
                ).show()
        );

        setupBottomMenu(binding.menuFeed, binding.menuSearch, binding.menuChat, binding.menuNotif, binding.menuProfile);
    }
}
