// dominio/Entrevista.java
package com.tp1.GestaoRH.dominio;

import com.tp1.GestaoRH.Candidatura.Candidato;
import java.time.LocalDate;

public class Entrevista {
    private Vaga vaga;
   private Candidato candidato;
    private LocalDate data;
    private Recrutador avaliador;
    private double nota;

    public Entrevista(Vaga vaga, Candidato candidato, LocalDate data, Recrutador avaliador, double nota) {
        this.vaga = vaga;
        this.candidato = candidato;
        this.data = data;
        this.avaliador = avaliador;
        this.nota = nota;
    }

    // Getters
    public Vaga getVaga() { return vaga; }
    public Candidato getCandidato() { return candidato; }
    public LocalDate getData() { return data; }
    public double getNota() { return nota; }

    // Setters (se precisar mudar depois)
    public void setData(LocalDate data) { this.data = data; }
    public void setNota(double nota) { this.nota = nota; }

    @Override
    public String toString() {
        return "Entrevista [Candidato=" + candidato.getNome() +
               ", Vaga=" + vaga.getCargo() +
               ", Data=" + data +
               ", Avaliador=" + avaliador.getNome() +
               ", Nota=" + nota + "]";
    }
}
