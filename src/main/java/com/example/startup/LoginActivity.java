package com.example.startup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.startup.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sessionManager = new SessionManager(this);

        binding.btnLogin.setOnClickListener(v -> entrar());
        binding.txtCreateAccount.setOnClickListener(v -> startActivity(new Intent(this, CadastroActivity.class)));
        binding.txtForgotPassword.setOnClickListener(v -> Toast.makeText(this, "Recuperação por e-mail/celular pode ser implementada aqui.", Toast.LENGTH_SHORT).show());
    }

    private void entrar() {
        String email = binding.edtEmail.getText().toString().trim().toLowerCase();
        String senha = binding.edtSenha.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.setError("Digite um e-mail válido");
            return;
        }

        if (senha.isEmpty()) {
            binding.edtSenha.setError("Digite sua senha");
            return;
        }

        if (sessionManager.login(email, senha)) {
            Toast.makeText(this, "Senha correta. Confirme a verificação de 2 etapas.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, VerificacaoActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "E-mail ou senha inválidos.", Toast.LENGTH_SHORT).show();
        }
    }
}
