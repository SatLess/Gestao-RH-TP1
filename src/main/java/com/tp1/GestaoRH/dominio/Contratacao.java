package com.tp1.GestaoRH.dominio;

import com.tp1.GestaoRH.Candidatura.Candidatura;
import java.io.Serializable;
import java.time.LocalDate;

// 1. Deve ser Serializable para o Helper salvar
public class Contratacao implements Serializable {

    // 2. Armazena a CANDIDATURA inteira (o link "Quem-Para-Qual-Vaga")
    private Candidatura candidaturaAprovada;
    
    // 3. Os dados do Requisito 2
    private String regime; // "CLT", "Estágio", "PJ"
    private LocalDate dataContratacao;

    private String statusAutorizacao; // Ex: "PENDENTE_GESTOR"

    public Contratacao(Candidatura candidaturaAprovada, String regime, LocalDate dataContratacao) {
        if (!"Aprovado".equals(candidaturaAprovada.getStatus())) {
            throw new IllegalArgumentException("Só é possível iniciar contratação de candidaturas APROVADAS.");
        }
        this.candidaturaAprovada = candidaturaAprovada;
        this.regime = regime;
        this.dataContratacao = dataContratacao;
        this.statusAutorizacao = "PENDENTE_GESTOR"; // Estado inicial
    }

    // Getters
    public Candidatura getCandidaturaAprovada() {
        return candidaturaAprovada;
    }

    public String getRegime() {
        return regime;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public String getStatusAutorizacao() {
        return statusAutorizacao;
    }

    // Setter (para o Gestor)
    public void setStatusAutorizacao(String statusAutorizacao) {
        this.statusAutorizacao = statusAutorizacao;
    }

    @Override
    public String toString() {
        return "Contratação [Candidato=" + candidaturaAprovada.getCandidato().getNome() 
                + ", Vaga=" + candidaturaAprovada.getVaga().getCargo() 
                + ", Regime=" + regime + ", Status=" + statusAutorizacao + "]";
    }
}