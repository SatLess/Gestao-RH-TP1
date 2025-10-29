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
public class FolhaPagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    private Funcionario funcionario;
    private RegraSalario regra;
    private double salarioFinal;

    public FolhaPagamento(Funcionario funcionario, RegraSalario regra) {
        if (funcionario == null) throw new IllegalArgumentException("funcionario nulo");
        if (regra == null) throw new IllegalArgumentException("regra nula");
        this.funcionario = funcionario;
        this.regra = regra;
        calcularSalario();
    }

    public void calcularSalario() {
        double base = funcionario.getSalarioBase();
        this.salarioFinal = regra.aplicarRegras(base, funcionario.getTipoContratacao());
    }

    public double getSalarioFinal() { return salarioFinal; }

    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) {
        if (funcionario == null) throw new IllegalArgumentException("funcionario nulo");
        this.funcionario = funcionario;
        calcularSalario();
    }

    public RegraSalario getRegra() { return regra; }
    public void setRegra(RegraSalario regra) {
        if (regra == null) throw new IllegalArgumentException("regra nula");
        this.regra = regra;
        calcularSalario();
    }

    @Override
    public String toString() {
        return String.format("Folha[funcionario=%s, salarioFinal=%.2f]", funcionario.getNome(), salarioFinal);
    }
}
