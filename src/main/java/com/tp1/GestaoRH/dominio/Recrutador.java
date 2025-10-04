// dominio/Recrutador.java
package com.tp1.GestaoRH.dominio;

public class Recrutador {
    private String nome;
    private String email;
    private String departamento;

    public Recrutador(String nome, String email, String departamento) {
        this.nome = nome;
        this.email = email;
        this.departamento = departamento;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
}
