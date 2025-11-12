package com.tp1.GestaoRH.dominio;

import java.io.Serializable;
import java.time.LocalDate;

public class FolhaPagamento implements Serializable {
    private static final long serialVersionUID = 1L;
    private Funcionario funcionario;
    private double salarioBruto;
    private double deducoes;
    private double salarioLiquido;
    private LocalDate data;

    public FolhaPagamento(Funcionario f, RegraSalario regra) {
        this.funcionario = f;
        this.data = LocalDate.now();
        calcular(f, regra);
    }

    private void calcular(Funcionario f, RegraSalario regra) {
        double base = f.getSalarioBase();
        switch (f.getTipoContratacao()) {
            case "CLT":
                salarioBruto = base + regra.getValeAlimentacao() + regra.getValeTransporte();
                deducoes = base * regra.getImposto();
                salarioLiquido = salarioBruto - deducoes;
                break;
            case "Estágio": case "Estagio":
                salarioBruto = base + regra.getValeTransporte();
                deducoes = 0.0;
                salarioLiquido = salarioBruto;
                break;
            case "PJ":
                salarioBruto = base;
                deducoes = 0.0;
                salarioLiquido = salarioBruto;
                break;
            default:
                salarioBruto = base; deducoes = 0.0; salarioLiquido = salarioBruto; break;
        }
    }

    public Funcionario getFuncionario() { return funcionario; }
    public double getSalarioBruto() { return salarioBruto; }
    public double getDeducoes() { return deducoes; }
    public double getSalarioLiquido() { return salarioLiquido; }
    public LocalDate getData() { return data; }

    @Override
    public String toString() {
        return String.format("Contracheque\nNome: %s\nCargo: %s\nRegime: %s\nBruto: R$ %.2f\nDeduções: R$ %.2f\nLíquido: R$ %.2f\nData: %s",
                funcionario.getNome(), funcionario.getCargo(), funcionario.getTipoContratacao(),
                salarioBruto, deducoes, salarioLiquido, data.toString());
    }
}
