/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tp1.GestaoRH.Candidatura;

import com.tp1.GestaoRH.Misc.Constantes;
import com.tp1.GestaoRH.Misc.Constantes.CANDIDATOSTATUS;
import com.tp1.GestaoRH.dominio.Vaga;
import java.io.Serializable;
import java.lang.reflect.InaccessibleObjectException;
import java.security.InvalidParameterException;

import javax.swing.JPanel;

/**
 *
 * @author spiri
 */
public class Candidatura implements Serializable {
    
    Candidato candidato;
    Vaga vaga;
    Constantes.CANDIDATOSTATUS status;

    public Candidatura(Candidato candidato, Vaga vaga) {
        this.candidato = candidato;
        this.vaga = vaga;
        this.status = Constantes.CANDIDATOSTATUS.PENDENTE;
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

    public String getStatus() {
        switch (this.status) {
            case Constantes.CANDIDATOSTATUS.APROVADO:
                return new String("Aprovado");
            
            case Constantes.CANDIDATOSTATUS.EM_ANALISE:
                return "Em Analise";
                
            case Constantes.CANDIDATOSTATUS.PENDENTE:
                return "Pendente";
        
            default:
                return "Rejeitado";
        }
    }
    

    public void setStatus(Constantes.CANDIDATOSTATUS status) {
        this.status = status;
    }
 
    public void setStatus(String status) throws InvalidParameterException {
        if (status.toLowerCase().equals("em analise")){
            this.status  = CANDIDATOSTATUS.EM_ANALISE;
        }
        else if (status.toLowerCase().equals("rejeitado")) {
            this.status = CANDIDATOSTATUS.REJEITADO;
        }
        else if (status.toLowerCase().equals("aprovado"))  {
            this.status = CANDIDATOSTATUS.APROVADO;
        }
        else if (status.toLowerCase().equals("pendente")){this.status = CANDIDATOSTATUS.PENDENTE;}
        else {
            throw new InaccessibleObjectException("Erro: Status da candidatura inválida");
        }
    }
}
