/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tp1.GestaoRH.dominio;
import java.util.HashMap;
import java.io.File;

/**
 *
 * @author spiri
 */
public class Candidato extends Pessoa {

    float pretensaoSalarial;
    HashMap<Vaga, String[]> horarioDisponivel;
    String experienciaProfissional;
    File[] documentacao;
    
    public Candidato(float pretensaoSalarial, HashMap<Vaga, String[]> horarioDisponivel, String experienciaProfissional, File[] documentacao, String nome, String email, String endereço, String cpf) {
        super(nome, email, endereço, cpf);
        this.pretensaoSalarial = pretensaoSalarial;
        this.horarioDisponivel = horarioDisponivel;
        this.experienciaProfissional = experienciaProfissional;
        this.documentacao = documentacao;
    }
    
    
        public float getPretensaoSalarial() {
        return pretensaoSalarial;
    }

    public void setPretensaoSalarial(float pretensaoSalarial) {
        this.pretensaoSalarial = pretensaoSalarial;
    }

    public HashMap<Vaga, String[]> getHorarioDisponivel() {
        return horarioDisponivel;
    }

    public void setHorarioDisponivel(HashMap<Vaga, String[]> horarioDisponivel) {
        this.horarioDisponivel = horarioDisponivel;
    }

    public String getExperienciaProfissional() {
        return experienciaProfissional;
    }

    public void setExperienciaProfissional(String experienciaProfissional) {
        this.experienciaProfissional = experienciaProfissional;
    }

    public File[] getDocumentacao() {
        return documentacao;
    }

    public void setDocumentacao(File[] documentacao) {
        this.documentacao = documentacao;
    }

    
}
