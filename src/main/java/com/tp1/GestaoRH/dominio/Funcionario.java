package com.tp1.GestaoRH.dominio;

public class Funcionario extends Pessoa {
    private static final long serialVersionUID = 1L;
    private String cargo;
    private String tipoContratacao; // CLT, Estágio, PJ
    private double salarioBase;
    private boolean ativo;

    public Funcionario(String nome, String email, String endereco, String cpf,
                       String cargo, double salarioBase, String tipoContratacao, boolean ativo) {
        super(nome, email, endereco, cpf);
        this.cargo = cargo;
        this.salarioBase = salarioBase;
        this.tipoContratacao = tipoContratacao;
        this.ativo = ativo;
    }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getTipoContratacao() { return tipoContratacao; }
    public void setTipoContratacao(String tipoContratacao) { this.tipoContratacao = tipoContratacao; }

    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
