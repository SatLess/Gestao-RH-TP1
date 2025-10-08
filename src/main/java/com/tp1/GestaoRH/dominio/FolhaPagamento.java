/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tp1.GestaoRH.dominio;

/**
 *
 * @author matheusmerechia
 */
public class FolhaPagamento {
    
    private Funcionario funcionario;
    private RegraSalario regra;
    private double salarioFinal;
    public void calcularSalario() {
        double base = funcionario.getSalarioBase();
        switch (funcionario.getTipoContratacao()) {
            case "CLT":
                salarioFinal = base + regra.getValeAlimentacao() + regra.getValeTransporte() - (base * regra.getImposto());
                break;
            case "Estágio":
                salarioFinal = base + (regra.getValeTransporte() / 2);
                break;
            case "PJ":
                salarioFinal = base;
                break;
        }
    }

    public FolhaPagamento(Funcionario funcionario, RegraSalario regra) {
        this.funcionario = funcionario;
        this.regra = regra;
        calcularSalario();
    }


    public double getSalarioFinal() { return salarioFinal; }

    // Getters e Setters
    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }

    public RegraSalario getRegra() { return regra; }
    public void setRegra(RegraSalario regra) { this.regra = regra; }
}
