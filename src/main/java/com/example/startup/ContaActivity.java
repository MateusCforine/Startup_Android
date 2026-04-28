package com.example.startup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.startup.databinding.ActivityContaBinding;

public class ContaActivity extends BaseMenuActivity {

    private static final int REQUEST_FOTO_PERFIL = 101;

    private ActivityContaBinding binding;
    private SessionManager sessionManager;
    private String fotoPerfilUri = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        ArrayAdapter<String> adapterTipoConta = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Pública", "Privada"}
        );
        adapterTipoConta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTipoConta.setAdapter(adapterTipoConta);

        carregarDadosUsuario();

        binding.btnAlterarFoto.setOnClickListener(v -> abrirGaleriaPerfil());
        binding.imgFotoPerfil.setOnClickListener(v -> abrirGaleriaPerfil());

        binding.btnSalvarPerfil.setOnClickListener(v -> salvar());

        binding.btnConfig.setOnClickListener(v ->
                startActivity(new Intent(this, ConfigActivity.class))
        );

        binding.btnSair.setOnClickListener(v -> {
            sessionManager.logout();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        setupBottomMenu(
                binding.menuFeed,
                binding.menuSearch,
                binding.menuChat,
                binding.menuNotif,
                binding.menuProfile
        );
    }

    private void carregarDadosUsuario() {
        binding.edtNome.setText(sessionManager.getName());
        binding.edtStatus.setText(sessionManager.getStatus());

        fotoPerfilUri = sessionManager.getFotoPerfil();
        if (!fotoPerfilUri.isEmpty()) {
            binding.imgFotoPerfil.setImageURI(Uri.parse(fotoPerfilUri));
        }

        String tipoConta = sessionManager.getTipoConta();
        if ("Privada".equalsIgnoreCase(tipoConta)) {
            binding.spinnerTipoConta.setSelection(1);
        } else {
            binding.spinnerTipoConta.setSelection(0);
        }
    }

    private void abrirGaleriaPerfil() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_FOTO_PERFIL);
    }

    private void salvar() {
        String nome = binding.edtNome.getText().toString().trim();
        String status = binding.edtStatus.getText().toString().trim();
        String tipoConta = binding.spinnerTipoConta.getSelectedItem().toString();

        if (nome.isEmpty()) {
            Toast.makeText(this, "Digite seu nome.", Toast.LENGTH_SHORT).show();
            return;
        }

        sessionManager.updateProfile(nome, status, fotoPerfilUri, tipoConta);

        Toast.makeText(this, "Perfil atualizado!", Toast.LENGTH_SHORT).show();
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FOTO_PERFIL && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            if (uri != null) {
                getContentResolver().takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                );

                fotoPerfilUri = uri.toString();
                binding.imgFotoPerfil.setImageURI(uri);
            }
        }
    }
}
