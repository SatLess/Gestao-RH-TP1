// dominio/Entrevista.java
package com.tp1.GestaoRH.dominio;

import com.tp1.GestaoRH.Candidatura.Candidato;
import java.io.Serializable; // GARANTA QUE ESTÁ AQUI
import java.time.LocalDate;

// GARANTA QUE IMPLEMENTA 'Serializable'
public class Entrevista implements Serializable { 
    private Vaga vaga;
    private Candidato candidato;
    private LocalDate data;
    private double nota;

    // ===================================================================
    // MUDANÇA CRÍTICA AQUI
    // ===================================================================
    private String avaliadorNome; // Trocamos o Objeto Recrutador por uma String

    // Construtor atualizado para aceitar uma String
    public Entrevista(Vaga vaga, Candidato candidato, LocalDate data, String avaliadorNome, double nota) {
        this.vaga = vaga;
        this.candidato = candidato;
        this.data = data;
        this.avaliadorNome = avaliadorNome; // Armazena a String
        this.nota = nota;
    }

    // Getters
    public Vaga getVaga() { return vaga; }
    public Candidato getCandidato() { return candidato; }
    public LocalDate getData() { return data; }
    public double getNota() { return nota; }
    public String getAvaliadorNome() { return avaliadorNome; } // Novo getter

    // Setters
    public void setData(LocalDate data) { this.data = data; }
    public void setNota(double nota) { this.nota = nota; }

    @Override
    public String toString() {
        return "Entrevista [Candidato=" + candidato.getNome() +
                ", Vaga=" + vaga.getCargo() +
                ", Data=" + data +
                ", Avaliador=" + avaliadorNome + // Atualizado para usar a String
                ", Nota=" + nota + "]";
    }
}