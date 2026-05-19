package com.example.startup;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.example.startup.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseMenuActivity {

    private ActivityLoginBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        binding.btnLogin.setOnClickListener(v -> entrar());

        binding.txtCreateAccount.setOnClickListener(v ->
                startActivity(new Intent(this, CadastroActivity.class))
        );

        binding.txtForgotPassword.setOnClickListener(v -> abrirRecuperacaoSenha());
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

    private void abrirRecuperacaoSenha() {
        EditText edtEmailRecuperacao = new EditText(this);
        edtEmailRecuperacao.setHint("Digite seu e-mail");
        edtEmailRecuperacao.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        edtEmailRecuperacao.setPadding(40, 20, 40, 20);

        new AlertDialog.Builder(this)
                .setTitle("Recuperar senha")
                .setMessage("Informe seu e-mail para receber o link de recuperação.")
                .setView(edtEmailRecuperacao)
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Enviar", (dialog, which) -> {
                    String email = edtEmailRecuperacao.getText().toString().trim().toLowerCase();

                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(this, "Digite um e-mail válido.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(
                            this,
                            "E-mail de recuperação enviado para " + email,
                            Toast.LENGTH_LONG
                    ).show();
                })
                .show();
    }
}
