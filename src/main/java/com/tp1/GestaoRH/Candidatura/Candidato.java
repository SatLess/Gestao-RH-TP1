/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tp1.GestaoRH.Candidatura;
import java.util.*;

import com.tp1.GestaoRH.dominio.*;
import com.tp1.GestaoRH.dominio.Pessoa;
import com.tp1.GestaoRH.dominio.Vaga;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author spiri
 */
public class Candidato extends Pessoa implements Serializable {

    private float pretensaoSalarial;
    private HashMap<Vaga, String[]> horarioDisponivel = new HashMap<Vaga,String[]>();
    private String experienciaProfissional;
    private ArrayList<File> documentacao = new ArrayList<File>();
    
    public Candidato(String pretensaoSalarial, Vaga vaga, String[] horarioDisponivel, String experienciaProfissional, File documentacao, String nome, String email, String endereço, String cpf) {
        super(nome, email, endereço, cpf);
        this.pretensaoSalarial = Float.valueOf(pretensaoSalarial);
        this.horarioDisponivel.put(vaga, horarioDisponivel);
        this.experienciaProfissional = experienciaProfissional;
        this.documentacao.add(documentacao);
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

    public String getExperienciaProfissional() {
        return experienciaProfissional;
    }

    public void setExperienciaProfissional(String experienciaProfissional) {
        this.experienciaProfissional = experienciaProfissional;
    }

    public ArrayList<File>  getDocumentacao() {
        return documentacao;
    }

    public void setDocumentacao(File documentacao) {
        this.documentacao.add(documentacao);
    }

    
}
