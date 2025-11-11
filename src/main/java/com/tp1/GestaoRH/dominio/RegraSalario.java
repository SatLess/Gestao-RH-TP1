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
    
public class RegraSalario implements Serializable {
    private static final long serialVersionUID = 1L;

    private double valeTransporte;
    private double valeAlimentacao;
    private double imposto; // exemplo: 0.10 para 10%

    public RegraSalario(double valeTransporte, double valeAlimentacao, double imposto) {
        if (valeTransporte < 0 || valeAlimentacao < 0 || imposto < 0) {
            throw new RegraInvalidaException("Valores da regra não podem ser negativos");
        }
        this.valeTransporte = valeTransporte;
        this.valeAlimentacao = valeAlimentacao;
        this.imposto = imposto;
    }

    public double getValeTransporte() { return valeTransporte; }
    public void setValeTransporte(double valeTransporte) {
        if (valeTransporte < 0) throw new RegraInvalidaException("valeTransporte negativo");
        this.valeTransporte = valeTransporte;
    }

    public double getValeAlimentacao() { return valeAlimentacao; }
    public void setValeAlimentacao(double valeAlimentacao) {
        if (valeAlimentacao < 0) throw new RegraInvalidaException("valeAlimentacao negativo");
        this.valeAlimentacao = valeAlimentacao;
    }

    public double getImposto() { return imposto; }
    public void setImposto(double imposto) {
        if (imposto < 0) throw new RegraInvalidaException("imposto negativo");
        this.imposto = imposto;
    }

    /**
     * Método utilitário que aplica a regra ao salário base e retorna o valor final.
     */
    public double aplicarRegras(double salarioBase, String tipoContratacao) {
        if (salarioBase < 0) throw new RegraInvalidaException("salarioBase negativo");
        switch (tipoContratacao) {
            case "CLT":
                return salarioBase + valeAlimentacao + valeTransporte - (salarioBase * imposto);
            case "Estagio":
                // Estágio pode receber apenas parte do vale transporte, por exemplo
                return salarioBase + (valeTransporte / 2.0);
            case "PJ":
                // PJ normalmente não recebe benefícios
                return salarioBase;
            default:
                // Se surgir um tipo novo, devolve o salário base
                return salarioBase;
        }
    }
}
