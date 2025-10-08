/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tp1.GestaoRH.dominio;

/**
 *
 * @author matheusmerechia
 */
public class Funcionario {
    private String cargo;
    private double salarioBase;
    private String tipoContratacao;
    private boolean ativo;

    
    public Funcionario(String cargo, double salarioBase, String tipoContratacao) {
        this.cargo = cargo;
        this.salarioBase = salarioBase;
        this.tipoContratacao = tipoContratacao;
        this.ativo = true;
    }

    
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }

    public String getTipoContratacao() { return tipoContratacao; }
    public void setTipoContratacao(String tipoContratacao) { this.tipoContratacao = tipoContratacao; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}

   
