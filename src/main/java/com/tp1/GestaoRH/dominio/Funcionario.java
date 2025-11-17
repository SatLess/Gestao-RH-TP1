package com.tp1.GestaoRH.dominio;
import com.tp1.GestaoRH.Candidatura.*;
import java.io.Serializable;

public class Funcionario extends Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    private String cargo;
    private String tipoContratacao; // CLT, Estágio, PJ
    private double salarioBase;
    private boolean ativo;
    private String departamento;
    private String telefone;

    public Funcionario(String nome, String email, String endereco, String cpf, String departamento,String telefone,            
                       String cargo, double salarioBase, String tipoContratacao, boolean ativo) {
        super(nome, email, endereco, cpf);
        this.cargo = cargo;
        this.telefone = telefone;
        this.salarioBase = salarioBase;
        this.tipoContratacao = tipoContratacao;
        this.ativo = ativo;
        this.departamento = departamento;
    }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getTipoContratacao() { return tipoContratacao; }
    public void setTipoContratacao(String tipoContratacao) { this.tipoContratacao = tipoContratacao; }

    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    
    public String getDepartamento() { return departamento;}
    public void setDepartamento (String departamento) {this.departamento = departamento;}
    
    public String getTelefone() { return telefone;}
    public void setTelefone (String telefone) {this.telefone = telefone;}
}
