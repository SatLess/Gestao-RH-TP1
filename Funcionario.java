/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tp1.GestaoRH.dominio;
import java.io.Serializable;
/**
 *
 * @author matheusmerechia
 */
public class Funcionario extends Pessoa implements Serializable{
    private static final long serialVersionUID = 1L;

    private String cargo;
    private String tipoContratacao;
    private double salarioBase;
    private boolean ativo;

    public Funcionario(String nome, String email, String endereco, String cpf, String cargo, double salarioBase, String tipoContratacao) {
        super(nome, email, endereco, cpf);

        if (salarioBase < 0) throw new IllegalArgumentException("O salário base não pode ser negativo.");
        this.cargo = cargo;
        this.salarioBase = salarioBase;
        this.tipoContratacao = tipoContratacao;
        this.ativo = true;
    }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getTipoContratacao() { return tipoContratacao; }
    public void setTipoContratacao(String tipoContratacao) { this.tipoContratacao = tipoContratacao; }

    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) {
        if (salarioBase < 0) throw new IllegalArgumentException("Salário base não pode ser negativo.");
        this.salarioBase = salarioBase;
    }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    @Override
    public String toString() {
        return String.format("Funcionario[nome=%s, cargo=%s, salarioBase=%.2f, tipo=%s, ativo=%b]",
                getNome(), cargo, salarioBase, tipoContratacao, ativo);
    }
}


   
