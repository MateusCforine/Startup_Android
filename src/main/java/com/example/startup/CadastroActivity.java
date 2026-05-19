package com.example.startup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import com.example.startup.databinding.ActivityCadastroBinding;

public class CadastroActivity extends BaseMenuActivity {
    private ActivityCadastroBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sessionManager = new SessionManager(this);

        binding.btnCadastrar.setOnClickListener(v -> cadastrar());
    }

    private void cadastrar() {
        String email = binding.edtEmail.getText().toString().trim().toLowerCase();
        String telefone = binding.edtTelefone.getText().toString().trim();
        String senha = binding.edtSenha.getText().toString().trim();
        String confirmar = binding.edtConfirmarSenha.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.setError("Digite um e-mail válido");
            return;
        }

        if (sessionManager.emailJaExiste(email)) {
            binding.edtEmail.setError("Este e-mail já está cadastrado");
            Toast.makeText(this, "Não é permitido repetir e-mail.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (telefone.length() < 8) {
            binding.edtTelefone.setError("Digite um telefone válido");
            return;
        }

        String erroSenha = validarSenha(senha);
        if (!erroSenha.isEmpty()) {
            binding.edtSenha.setError(erroSenha);
            Toast.makeText(this, erroSenha, Toast.LENGTH_LONG).show();
            return;
        }

        if (!senha.equals(confirmar)) {
            binding.edtConfirmarSenha.setError("As senhas não conferem");
            return;
        }

        boolean cadastrado = sessionManager.saveUser(email, senha, telefone);

        if (cadastrado) {
            Toast.makeText(this, "Conta criada com sucesso! Agora faça a verificação de 2 etapas.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, VerificacaoActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Este e-mail já está cadastrado.", Toast.LENGTH_SHORT).show();
        }
    }

    private String validarSenha(String senha) {
        if (senha.length() < 8) {
            return "A senha precisa ter no mínimo 8 caracteres.";
        }

        if (!senha.matches(".*[!@#$%&*()_+=|<>?{}\\[\\]~-].*")) {
            return "A senha precisa ter pelo menos 1 caractere especial.";
        }

        if (!senha.matches(".*[A-Za-z].*")) {
            return "A senha precisa ter pelo menos 1 letra.";
        }

        if (!senha.matches(".*[0-9].*")) {
            return "A senha precisa ter pelo menos 1 número.";
        }

        return "";
    }
}
