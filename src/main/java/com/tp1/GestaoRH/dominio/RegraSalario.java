/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tp1.GestaoRH.dominio;

/**
 *
 * @author matheusmerechia
 */
    
public class RegraSalario {
    private double valeTransporte;
    private double valeAlimentacao;
    private double imposto;

    public RegraSalario(double valeTransporte, double valeAlimentacao, double imposto) {
        this.valeTransporte = valeTransporte;
        this.valeAlimentacao = valeAlimentacao;
        this.imposto = imposto;
    }

    // Getters e Setters
    public double getValeTransporte() { return valeTransporte; }
    public void setValeTransporte(double valeTransporte) { this.valeTransporte = valeTransporte; }

    public double getValeAlimentacao() { return valeAlimentacao; }
    public void setValeAlimentacao(double valeAlimentacao) { this.valeAlimentacao = valeAlimentacao; }

    public double getImposto() { return imposto; }
    public void setImposto(double imposto) { this.imposto = imposto; }
}
