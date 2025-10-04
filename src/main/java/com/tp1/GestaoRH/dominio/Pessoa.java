/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tp1.GestaoRH.dominio;

/**
 *
 * @author spiri
 */
public class Pessoa {
    private String nome;
    private String email;
    private String endereço;
    private String cpf;
    
    public Pessoa(String nome, String email, String endereço, String cpf){
        this.nome = nome;
        this.email = email;
        this.endereço = endereço;
        this.cpf = cpf;
    }
    
        // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getEndereço() {
        return endereço;
    }
    public void setEndereço(String endereço)
    {
        this.endereço = endereço;
    }
    public String getCpf() {
        return cpf;
    }
}
