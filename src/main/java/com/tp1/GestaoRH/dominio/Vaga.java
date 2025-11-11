// dominio/Vaga.java
package com.tp1.GestaoRH.dominio;
import com.tp1.GestaoRH.Candidatura.*;
import com.tp1.GestaoRH.Misc.Constantes;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Vaga implements Serializable {

    private int id;
    private String cargo;
    private double salarioBase;
    private String departamento;
    private Constantes.STATUS status; // "Aberta" ou "Fechada"
    private LocalDate dataAbertura;

    // Associação com candidatos
    private List<Candidato> candidatosAssociados;

    public Vaga(int id, String cargo, double salarioBase, String departamento,
            Constantes.STATUS status, LocalDate dataAbertura) {
        this.id = id;
        this.cargo = cargo;
        this.salarioBase = salarioBase;
        this.departamento = departamento;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.candidatosAssociados = new ArrayList<>();
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getCargo() {
        return cargo;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public String getDepartamento() {
        return departamento;
    }

    public Constantes.STATUS getStatus() {
        return status;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public List<Candidato> getCandidatosAssociados() {
        return candidatosAssociados;
    }

    // Setters
    public void setStatus(Constantes.STATUS status) {
        this.status = status;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo; // 'this.cargo' é o campo da classe
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento; // 'this.departamento' é o campo da classe
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura; // 'this.dataAbertura' é o campo da classe
    }

    // Métodos de lógica
    public void adicionarCandidato(Candidato candidato) {
        candidatosAssociados.add(candidato);
    }

    public void removerCandidato(Candidato candidato) {
        candidatosAssociados.remove(candidato);
    }

    @Override
    public String toString() {
        return "Vaga [ID=" + id
                + ", Cargo=" + cargo
                + ", Departamento=" + departamento
                + ", Salário=" + salarioBase
                + ", Status=" + status
                + ", Aberta em=" + dataAbertura
                + ", Candidatos=" + candidatosAssociados.size() + "]";
    }
}
