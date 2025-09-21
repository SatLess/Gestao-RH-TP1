// dominio/Contratacao.java
package dominio;

import java.time.LocalDate;

public class Contratacao {
    private String candidato;
    private Vaga vaga;
    private String regime; // CLT, PJ, Estágio, etc.
    private LocalDate dataContratacao;

    public Contratacao(String candidato, Vaga vaga, String regime, LocalDate dataContratacao) {
        this.candidato = candidato;
        this.vaga = vaga;
        this.regime = regime;
        this.dataContratacao = dataContratacao;
    }

    // Getters
    public String getCandidato() { return candidato; }
    public Vaga getVaga() { return vaga; }
    public String getRegime() { return regime; }
    public LocalDate getDataContratacao() { return dataContratacao; }

    // Setters
    public void setRegime(String regime) { this.regime = regime; }
    public void setDataContratacao(LocalDate dataContratacao) { this.dataContratacao = dataContratacao; }

    @Override
    public String toString() {
        return "Contratacao [Candidato=" + candidato +
               ", Vaga=" + vaga.getCargo() +
               ", Regime=" + regime +
               ", Data=" + dataContratacao + "]";
    }
}
