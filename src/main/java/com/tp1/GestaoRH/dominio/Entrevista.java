// dominio/Entrevista.java
package com.tp1.GestaoRH.dominio;

import java.time.LocalDate;

public class Entrevista {
    private Vaga vaga;
    private String candidato;
    private LocalDate data;
    private String avaliador;
    private double nota;

    public Entrevista(Vaga vaga, String candidato, LocalDate data, String avaliador, double nota) {
        this.vaga = vaga;
        this.candidato = candidato;
        this.data = data;
        this.avaliador = avaliador;
        this.nota = nota;
    }

    // Getters
    public Vaga getVaga() { return vaga; }
    public String getCandidato() { return candidato; }
    public LocalDate getData() { return data; }
    public String getAvaliador() { return avaliador; }
    public double getNota() { return nota; }

    // Setters (se precisar mudar depois)
    public void setData(LocalDate data) { this.data = data; }
    public void setNota(double nota) { this.nota = nota; }

    @Override
    public String toString() {
        return "Entrevista [Candidato=" + candidato +
               ", Vaga=" + vaga.getCargo() +
               ", Data=" + data +
               ", Avaliador=" + avaliador +
               ", Nota=" + nota + "]";
    }
}
