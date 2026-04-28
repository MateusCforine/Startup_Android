package com.example.startup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.startup.databinding.ActivityVerificacaoBinding;

import java.util.Random;

public class VerificacaoActivity extends AppCompatActivity {
    private ActivityVerificacaoBinding binding;
    private String codigoGerado;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerificacaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email = getIntent().getStringExtra("email");
        if (email == null || email.isEmpty()) {
            email = "seu e-mail";
        }

        gerarNovoCodigo();

        binding.txtInfo.setText("Digite o código de 6 dígitos enviado para: " + email);
        binding.btnVerificar.setOnClickListener(v -> verificarCodigo());
        binding.txtReenviar.setOnClickListener(v -> gerarNovoCodigo());
    }

    private void gerarNovoCodigo() {
        codigoGerado = String.valueOf(100000 + new Random().nextInt(900000));
        Toast.makeText(this, "Código de verificação: " + codigoGerado, Toast.LENGTH_LONG).show();
    }

    private void verificarCodigo() {
        String codigoDigitado = binding.edtCodigo.getText().toString().trim();

        if (codigoDigitado.length() != 6) {
            binding.edtCodigo.setError("Digite os 6 números do código");
            return;
        }

        if (codigoDigitado.equals(codigoGerado)) {
            Toast.makeText(this, "Verificação concluída com sucesso!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, FeedActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Código incorreto. Tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }
}
