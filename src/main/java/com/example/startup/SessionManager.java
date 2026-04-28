package com.example.startup;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF = "startup_pref";
    private static final String KEY_EMAIL_ATUAL = "email_atual";

    private final SharedPreferences preferences;
    private final BancoHelper bancoHelper;

    public SessionManager(Context context) {
        preferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        bancoHelper = new BancoHelper(context);
    }

    public boolean saveUser(String email, String password, String phone) {
        boolean cadastrado = bancoHelper.cadastrarUsuario(email, password, phone);

        if (cadastrado) {
            preferences.edit().putString(KEY_EMAIL_ATUAL, email).apply();
        }

        return cadastrado;
    }

    public boolean login(String email, String password) {
        boolean valido = bancoHelper.validarLogin(email, password);

        if (valido) {
            preferences.edit().putString(KEY_EMAIL_ATUAL, email).apply();
        }

        return valido;
    }

    public boolean emailJaExiste(String email) {
        return bancoHelper.emailJaExiste(email);
    }

    public String getEmailAtual() {
        return preferences.getString(KEY_EMAIL_ATUAL, "demo@gusto.com");
    }

    public String getName() {
        String nome = bancoHelper.buscarNome(getEmailAtual());
        return nome == null || nome.isEmpty() ? "Sarahh" : nome;
    }

    public String getStatus() {
        String status = bancoHelper.buscarStatus(getEmailAtual());
        return status == null ? "" : status;
    }

    public String getFotoPerfil() {
        String foto = bancoHelper.buscarFotoPerfil(getEmailAtual());
        return foto == null ? "" : foto;
    }

    public String getTipoConta() {
        String tipo = bancoHelper.buscarTipoConta(getEmailAtual());
        return tipo == null || tipo.isEmpty() ? "Pública" : tipo;
    }

    public void updateProfile(String name, String status, String fotoPerfil, String tipoConta) {
        bancoHelper.atualizarPerfil(getEmailAtual(), name, status, fotoPerfil, tipoConta);
    }

    public void logout() {
        preferences.edit().remove(KEY_EMAIL_ATUAL).apply();
    }
}
