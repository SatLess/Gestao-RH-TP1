// dominio/Vaga.java
package com.tp1.GestaoRH.dominio;

import com.tp1.GestaoRH.Misc.Constantes;
import java.io.Serializable;
import java.time.LocalDate;

public class Vaga implements Serializable {
    private int id;
    private String cargo;
    private double salarioBase;
    private String departamento;
    private Constantes.STATUS status; // "Aberta" ou "Fechada"
    private LocalDate dataAbertura;

    public Vaga(int id, String cargo, double salarioBase, String departamento, Constantes.STATUS status, LocalDate dataAbertura) {
        this.id = id;
        this.cargo = cargo;
        this.salarioBase = salarioBase;
        this.departamento = departamento;
        this.status = status;
        this.dataAbertura = dataAbertura;
    }

    // Getters
    public int getId() { return id; }
    public String getCargo() { return cargo; }
    public double getSalarioBase() { return salarioBase; }
    public String getDepartamento() { return departamento; }
    public Constantes.STATUS getStatus() { return status; }
    public LocalDate getDataAbertura() { return dataAbertura; }

    // Setters
    public void setStatus(Constantes.STATUS status) { this.status = status; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }

    // Método auxiliar para exibir informações resumidas
    @Override
    public String toString() {
        return "Vaga [ID=" + id + ", Cargo=" + cargo + ", Departamento=" + departamento +
               ", Salário=" + salarioBase + ", Status=" + status + ", Aberta em=" + dataAbertura + "]";
    }
}
