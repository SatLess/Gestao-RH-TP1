package com.tp1.GestaoRH.dominio;

import java.io.Serializable;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String login;
    private String senha;
    private String cargo; // Administrador, GestorRH, FuncionarioGeral

    public Usuario(String login, String senha, String cargo) {
        this.login = login; this.senha = senha; this.cargo = cargo;
    }

    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public String getCargo() { return cargo; }
}
