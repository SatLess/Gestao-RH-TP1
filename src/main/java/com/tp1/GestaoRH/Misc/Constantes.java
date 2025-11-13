/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tp1.GestaoRH.Misc;

public class Constantes {
    public enum STATUS{
        ABERTA,
        FECHADA,
        APROVADO,
        REJEITADO
    }

        public enum CANDIDATOSTATUS{
        PENDENTE,
        EM_ANALISE,
        REJEITADO,
        APROVADO,
    }
    
    public static final String PATHCANDIDATOS = "candidatos.txt";
    public static final String PATH_RECRUTADORES = "recrutadores.dat";
    public static final String PATH_ENTREVISTAS = "entrevistas.dat";
    public static final String PATH_CONTRATACOES = "contratacoes.dat";
}
