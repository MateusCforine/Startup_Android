package com.example.startup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class BancoHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "gusto.db";
    private static final int VERSAO = 3;

    public BancoHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT, " +
                "email TEXT UNIQUE NOT NULL, " +
                "telefone TEXT, " +
                "senha TEXT NOT NULL, " +
                "status TEXT, " +
                "fotoPerfil TEXT, " +
                "tipoConta TEXT DEFAULT 'Pública')");

        ContentValues demo = new ContentValues();
        demo.put("nome", "Sarahh");
        demo.put("email", normalizarEmail("demo@gusto.com"));
        demo.put("telefone", "15999999999");
        demo.put("senha", gerarHashSenha("Gusto@123"));
        demo.put("status", "Apaixonada por comida e boas companhias!");
        demo.put("fotoPerfil", "");
        demo.put("tipoConta", "Pública");
        db.insert("usuarios", null, demo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }

    public boolean cadastrarUsuario(String email, String senha, String telefone) {
        SQLiteDatabase db = getWritableDatabase();
        String emailNormalizado = normalizarEmail(email);

        if (emailJaExiste(emailNormalizado)) {
            return false;
        }

        ContentValues values = new ContentValues();
        values.put("nome", "Novo usuário");
        values.put("email", emailNormalizado);
        values.put("telefone", telefone);
        values.put("senha", gerarHashSenha(senha));
        values.put("status", "");
        values.put("fotoPerfil", "");
        values.put("tipoConta", "Pública");

        long resultado = db.insert("usuarios", null, values);
        return resultado != -1;
    }

    public boolean validarLogin(String email, String senha) {
        SQLiteDatabase db = getReadableDatabase();
        String emailNormalizado = normalizarEmail(email);
        String senhaCriptografada = gerarHashSenha(senha);

        Cursor cursor = db.rawQuery(
                "SELECT id FROM usuarios WHERE email = ? AND senha = ?",
                new String[]{emailNormalizado, senhaCriptografada}
        );

        boolean existe = cursor.moveToFirst();
        cursor.close();
        return existe;
    }

    public boolean emailJaExiste(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id FROM usuarios WHERE email = ?",
                new String[]{normalizarEmail(email)}
        );

        boolean existe = cursor.moveToFirst();
        cursor.close();
        return existe;
    }

    private String normalizarEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }

    private String gerarHashSenha(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes());
            StringBuilder hexadecimal = new StringBuilder();

            for (byte b : hash) {
                hexadecimal.append(String.format("%02x", b));
            }

            return hexadecimal.toString();
        } catch (NoSuchAlgorithmException e) {
            return senha;
        }
    }

    public String buscarNome(String email) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT nome FROM usuarios WHERE email = ?",
                new String[]{normalizarEmail(email)}
        );

        String nome = "";
        if (cursor.moveToFirst()) {
            nome = cursor.getString(0);
        }

        cursor.close();
        return nome;
    }

    public String buscarStatus(String email) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT status FROM usuarios WHERE email = ?",
                new String[]{normalizarEmail(email)}
        );

        String status = "";
        if (cursor.moveToFirst()) {
            status = cursor.getString(0);
        }

        cursor.close();
        return status;
    }

    public String buscarFotoPerfil(String email) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT fotoPerfil FROM usuarios WHERE email = ?",
                new String[]{normalizarEmail(email)}
        );

        String foto = "";
        if (cursor.moveToFirst()) {
            foto = cursor.getString(0);
        }

        cursor.close();
        return foto;
    }

    public String buscarTipoConta(String email) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT tipoConta FROM usuarios WHERE email = ?",
                new String[]{normalizarEmail(email)}
        );

        String tipo = "Pública";
        if (cursor.moveToFirst()) {
            tipo = cursor.getString(0);
        }

        cursor.close();
        return tipo == null || tipo.isEmpty() ? "Pública" : tipo;
    }

    public void atualizarPerfil(String email, String nome, String status, String fotoPerfil, String tipoConta) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("status", status);
        values.put("fotoPerfil", fotoPerfil);
        values.put("tipoConta", tipoConta);

        db.update("usuarios", values, "email = ?", new String[]{normalizarEmail(email)});
    }
}
