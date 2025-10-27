/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tp1.GestaoRH.Candidatura;

import com.tp1.GestaoRH.Misc.Constantes;
import com.tp1.GestaoRH.dominio.Vaga;

/**
 *
 * @author spiri
 */
public class Candidatura {

    public Candidatura(Candidato candidato, Vaga vaga) {
        this.candidato = candidato;
        this.vaga = vaga;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    public Constantes.STATUS getStatus() {
        return status;
    }

    public void setStatus(Constantes.STATUS status) {
        this.status = status;
    }
    Candidato candidato;
    Vaga vaga;
    Constantes.STATUS status;
}
