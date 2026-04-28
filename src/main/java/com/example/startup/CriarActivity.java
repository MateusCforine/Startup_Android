package com.example.startup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.startup.databinding.ActivityCriarBinding;

public class CriarActivity extends BaseMenuActivity {

    private static final int REQUEST_IMAGEM_PUBLICACAO = 102;

    private ActivityCriarBinding binding;
    private Uri imagemSelecionadaUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCriarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAdicionarImagem.setOnClickListener(v -> abrirGaleriaPublicacao());
        binding.imgPublicacao.setOnClickListener(v -> abrirGaleriaPublicacao());
        binding.btnPublicar.setOnClickListener(v -> publicar());

        setupBottomMenu(
                binding.menuFeed,
                binding.menuSearch,
                binding.menuChat,
                binding.menuNotif,
                binding.menuProfile
        );
    }

    private void abrirGaleriaPublicacao() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_IMAGEM_PUBLICACAO);
    }

    private void publicar() {
        String descricao = binding.edtDescricao.getText().toString().trim();

        if (descricao.isEmpty() && imagemSelecionadaUri == null) {
            Toast.makeText(this, "Digite uma descrição ou adicione uma imagem.", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Publicação criada!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGEM_PUBLICACAO && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            if (uri != null) {
                getContentResolver().takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                );

                imagemSelecionadaUri = uri;
                binding.imgPublicacao.setImageURI(uri);
                binding.imgPublicacao.setVisibility(View.VISIBLE);
                binding.txtImagemSelecionada.setText("Imagem adicionada à publicação");
            }
        }
    }
}
